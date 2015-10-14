package IR_Report2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
/**
 * 
 * 스테밍을 통해 한글 리스트를 추출 하고 그 중 2 개의 단어를 선택하여
 * 의존 관계를 알아보는 테스트 입니다.
 * @author HunJin
 *
 */
public class MainSearch {

	// 읽어들인 파일을 줄 단위로 임시 저장하기 위한 변수
	private String text1 = null;  
	// 읽어들인 줄을 단어 단위로 임시 저장하기 위한 변수
	private String[] text2 = null;
	// 단어를 스테밍 하여 나온 결과값을 임시 저장하기 위한 변수
	private String text3 = null;  
	// 중복 제거를 위함
	private HashSet<String> hashSet;  
	// 중복 제거 전 저장을 위한 변수
	private ArrayList<String> arrayList;  
	// 중복 제거 후 저장을 위한 변수
	ArrayList<String> deduplication;  

	private String inputTextA = null;  // A값 입력 변수
	private String inputTextB = null;  // B값 입력 변수

	// 이중 어레이 리스트로 선언하여 동적 이차원 배열 효과를 가짐
	private ArrayList<ArrayList<String>> inputWordList 
						= new ArrayList<ArrayList<String>>();
	
	// 읽어들일 파일의 최대 개수
	private static final int WEB_COUNT = 33;

	// 스테밍을 위한 값
	private static final String[] END_WORD_VERB = 
			{"까", "고", "요", "도", "어", "며", "라", "듯","지","다",
					"기","면","데","나","야","래","러","진","함","임",
					"게","서","니","오", "여", "군"};
	private static final String[] END_WORD_NOUN = 
			{"에", "의", "며", "어", "은", "는", "가",
					"를","랑","와", "이", "을", "과" ,"로","들"};

	/**
	 * 파일을 입력 받고 랜덤으로 파일의 단어들을 출력 후 입력을 받아 검색 합니다.
	 * @param args
	 */
	public static void main(String[] args) {

		MainSearch mainSearch = new MainSearch();

		for(int i=0;i<WEB_COUNT;i++) {
			// 0~32까지의 저장된 웹 페이지(파일 명)을 읽습니다.
			mainSearch.readFile("news"+i+".txt");
		}
		
		// 파일의 단어들을 보여줍니다.
		mainSearch.print();

		// 입력값을 받습니다.
		mainSearch.Scan();

		// 검색을 시작합니다.
		mainSearch.findText();

	}

	/**
	 * 파일을 읽고, 한줄 단위로 자른 후 문자 단위로 다시 자릅니다.
	 * @param fileName
	 */
	private void readFile(String fileName) {
		arrayList = new ArrayList<>();
		try {
			BufferedReader in = 
					new BufferedReader(new FileReader(fileName));
			while((text1 = in.readLine())!=null) {
				text2 = spilitText(text1);  // 문자로 자르기 위한 메서드 입니다.
				addList(text2);// 리스트에 자른 텍스트2를 저장하기 위한 메서드 입니다.				
			}

			makeDeduplication(arrayList);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 공백을 기준으로 문자를 자릅니다.
	 * @param text
	 * @return
	 */
	private String[] spilitText(String text) {
		return text.split(" ");
	}

	private void addList(String[] text) {

		for(int i=0;i<text.length;i++) {
			// 한글을 제외한 나머지 단어는 삭제시킵니다.
			text3 = korExtenction(text[i]);  
			if(text3.length()!=0) {
				// 몇 가지 스테밍을 통해 단어를 추출합니다.
				text3 = endWordExtenction(text3);  
			}
			arrayList.add(text3);  // 남은 단어를 list에 저장합니다.
		}
	}

	/**
	 * 단어에서 한글을 제외한 나머지 단어를 삭제시킵니다.
	 * @param str
	 * @return
	 */
	private String korExtenction(String str) {
		return str.replaceAll("[^\uAC00-\uD7AF\u1100-"
				+ "\u11FF\u3130-\u318F]","");
	}

	/**
	 * 간단한 스테밍 알고리즘을 통해 명사를 추출합니다.
	 * 워낙 간단하기 때문에 완벽하지 않습니다.
	 * @param str
	 * @return
	 */
	private String endWordExtenction(String str) {

		String match = str.substring(str.length()-1,str.length());
		for(int i=0;i<END_WORD_NOUN.length;i++) { 
			// 자른 단어가 명사 스테밍에 걸릴 경우 한문자를 삭제시킵니다.
			if(match.equals(END_WORD_NOUN[i])) {  
				str = returnText(str);
			}
		}
		for(int i=0;i<END_WORD_VERB.length;i++) { 
			// 자른 단어가 동사 스테밍일 경우 그 단어를 삭제 시킵니다.
			if(match.equals(END_WORD_VERB[i])) {  
				str = "";
			}
		}
		if(str.length()==1) {
			str = "";
		}
		return str;
	}

	/**
	 * 마지막 한자리를 보고 스테밍을 판단합니다.
	 * @param str
	 * @return
	 */
	private String returnText(String str) {
		return str = str.substring(0,str.length()-1);
	}

	/**
	 * 중복을 제거 합니다.
	 * @param arr
	 */
	private void makeDeduplication(ArrayList<String> arr) {
		hashSet = new HashSet<>(arr);
		makeDeduplicationArrayList(hashSet);
	}
	
	/**
	 * 이중 어레이리스트에 중복을 제외한 단어 리스트를 저장합니다.
	 * @param hashSet
	 */
	private void makeDeduplicationArrayList(HashSet<String> hashSet) {
		deduplication = new ArrayList<>(hashSet);
		inputWordList.add(deduplication);
	}
	
	/**
	 * 랜덤으로 단어 리스트의 목록을 보여주고 입력 값을 도와줍니다.
	 */
	private void print() {
		int random = (int)(Math.random()*WEB_COUNT);
		for(int i=0;i<inputWordList.get(random).size();i++) {
			System.out.print(
					inputWordList.get(random).get(i) + "   ");
		}
		System.out.println();
	}
	
	/**
	 * 단어를 입력하는 메서드 입니다.
	 */
	private void Scan() {
		System.out.print("첫번 째 단어 입력 : ");
		Scanner sc = new Scanner(System.in);
		inputTextA = sc.nextLine();
		System.out.print("두번 째 단어 입력 : ");
		inputTextB = sc.nextLine();
	}

	/**
	 * 단어를 찾는 메서드 입니다.
	 * A와 B 모두 포함하면 Z값이 증가합니다.
	 * A만 포함 되면 A값이 증가합니다.
	 * B만 포함 되면 B값이 증가합니다.
	 */
	private void findText() {
		int countA = 0;
		int countB = 0;
		int countZ = 0;
		for(int i=0;i<inputWordList.size();i++) {
			if((inputWordList.get(i).contains(inputTextA) == true) && 
					(inputWordList.get(i).contains(inputTextB) == true)) {
				countZ++;
			} 
			if(inputWordList.get(i).contains(inputTextA) == true) {
				countA++;
			} 
			if(inputWordList.get(i).contains(inputTextB) == true) {
				countB++;
			}
		}
		result(countA, countB, countZ);
	}

	/**
	 * 결과를 도출하는 메서드 입니다.
	 * P(A|B)를 구하고 P(A)를 구합니다.
	 * @param countA
	 * @param countB
	 * @param countZ
	 */
	private void result(int countA, int countB, int countZ ) {

		float countPab =(float)countZ / (float)countB;
		float countPz =(float)countA / (float) WEB_COUNT ;
		
		System.out.println("A = " + countA + "\t\tB = " +
								countB + "\t\tZ = " + countZ);
		System.out.println("P(A|B) = " + countPab + "\t\tP(A) = " + countPz);
	}
}
