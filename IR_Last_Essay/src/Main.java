import java.io.EOFException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

	final static int TOTALFILENUMBER = 100;

	ArrayList<Double> arrTestDF = new ArrayList<>(); // DF값
	ArrayList<HashSet> arrHashSet = new ArrayList<>(); // hashSet으로 이루어진 리스트
	ArrayList<HashMap> arrHashMap = new ArrayList<>(); // hashMap으로 이루어진 리스트
	ArrayList<String> arrUrl = new ArrayList<>(); // url리스트
	HashSet<String> totalWordHashSet = new HashSet<>(); // 모든 단어가 저장 될 해시셋
	HashMap<String, Integer> totalWordHashMap = new HashMap<>(); // 모든 단어가 저장 될 해시맵

	ArrayList<String> arrLink;

	String mURL = null;
	double random = 0.0;
	HtmlParsing htmlParsing = new HtmlParsing();
	SaveWord saveWordTest = new SaveWord();

	void addURL(String mURL) {
		arrUrl.add(mURL);
	}

	/**
	 * url로부터 글을 가져와서 해시에 저장을 하는 메서드입니다.
	 */
	void loop() {
		mURL = htmlParsing.randomAddress();  // 랜덤 url을 가져와서
		arrUrl.add(mURL);  // url 목록 리스트에 저장
		String document = "doc_" + mURL.substring(19, mURL.length());  // 파일 생성
		new FileManagement().saveFile("file/" + mURL.substring(19, 
		 mURL.length()), document+"/");  // 파일 저장
		new FileManagement().saveFile("file/"+document, mURL);  // 파일 생성
		String originalDocument = htmlParsing.getText(mURL);  // 웹 파싱
		new FileManagement().saveFile("file/"+document, originalDocument);  // 파싱 문서 저장
		String[] testSplit = new SplitWord().getSplit(originalDocument);  // 띄어쓰기로 문자 구분
		saveWordTest.setHashSetWord(testSplit);  // hashSet에 단어 저장
		saveWordTest.setTotalWord(testSplit);  // totalHashSet에 단어 저장
		HashSet<String> hashSetTest = saveWordTest.getHashSetWord();
		HashMap<String, Integer> hashMapest = saveWordTest.getHashMapFrequence();
		arrHashSet.add(hashSetTest);
		arrHashMap.add(hashMapest);
	}

	/**
	 * 실행함수입니다.
	 */
	void start() {
		for (int i = 0; i < TOTALFILENUMBER; i++) {
			loop();
		}
		totalWordHashMap = saveWordTest.getTotalHashMapWord();
		totalWordHashSet = saveWordTest.getTotalHashSetWord();
		// ArrayList<String> arr = new ArrayList<>(arrHashSet.get(0));
		// for (int i = 0; i < arr.size(); i++) {
		// System.out.println(arr.get(i) + " " +
		// arrHashMap.get(0).get(arr.get(i)));
		// }

		ArrayList<String> arr2 = new ArrayList<>(totalWordHashSet);
		String doc = "";

		for (String string : arr2) {
			arrLink = new ArrayList<>();
			// DF, TF, IDF, Weight를 초기화 합니다.
			int mDF = 0;
			double mTF = 0.0;
			double mIDF = 0.0;
			double mWeight = 0.0;
			for (int i = 0; i < arrHashSet.size(); i++) {
				if (arrHashSet.get(i).contains(string)) {  // 단어가 문서내에 있을 경우 df를 증가시킵니다.
					mDF++;
					// 리스트에 url을 저장합니다.
					arrLink.add(arrUrl.get(i).substring(19, arrUrl.get(i).length()) + "");
				}
			}
			for (int i = 0; i < arrHashMap.size(); i++) {
				// tf, idf, weight를 계산합니다.
				if (arrHashMap.get(i).containsKey(string)) {
					mTF = new Weight().getTF((int) arrHashMap.get(i).get(string));
					mIDF = new Weight().getIDF(TOTALFILENUMBER, mDF);
					mWeight = new Weight().getWeight(mTF, mIDF);
//					 System.out.println(
//					 "문서 " + i + " " + arrUrl.get(i) + " - " + string + " : "
//					 + arrHashMap.get(i).get(string)
//					 + " TF : " + mTF + " IDF : " + mIDF + " Weight : " +
//					 mWeight);
				}
			}
			if (string.equals("")) {
				continue;
			} else { // 파일에 저장합니다.
				String index = "{/";
				for (String stringLink : arrLink) {
					index += stringLink + "/";
				}
				index += "}";
				// System.out.println(string + " " +
				// totalWordHashMap.get(string) + " " + mDF + " " + index);
				doc = string + "/" + totalWordHashMap.get(string) + "/" + mDF + "/" + index;
				new FileManagement().saveFile("file\\idf", doc);

			}
		}

		String testWord;
		boolean isCheck = true;
		do {

			System.out.print("질의어 입력 : ");
			Scanner sc = new Scanner(System.in);
			testWord = sc.nextLine();
			if (testWord.equals("q")) {
				isCheck = false;
				System.out.println("종료되었습니다.");
				break;
			}

			int mDF = 0;
			for (int i = 0; i < arrHashSet.size(); i++) {
				if (arrHashSet.get(i).contains(testWord)) {
					mDF++;
				}
			}
			for (int i = 0; i < arrHashMap.size(); i++) {
				if (arrHashMap.get(i).containsKey(testWord)) {
					double mTF = new Weight().getTF((int) arrHashMap.get(i).get(testWord));
					double mIDF = new Weight().getIDF(TOTALFILENUMBER, mDF);
					double mWeight = new Weight().getWeight(mTF, mIDF);
					System.out.println(
							"문서 " + i + " " + arrUrl.get(i) + " - " + testWord + " : " 
					+ arrHashMap.get(i).get(testWord) + " TF : " + mTF 
					+ " IDF : " + mIDF + " Weight : " + mWeight);
				}
			}
			System.out.println(testWord + " :  DF : " + mDF);
		} while (isCheck);
	}

	public static void main(String[] args) {

		Main a = new Main();
		a.start();

	}
}