package IR_Report1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class IR_Main {

	static final String[] suffixlist = { "ing", "ed", "d", "s", "es", "ly", "ful", "able", "tion", "ous", "ness", "er", "r" }; // suffix

	static String Input = null; // input text
	static Set<Integer> rootSet = new HashSet<Integer>();
	static boolean mat = false; // 단어 검색
	static String FileName = "dictionary - make.txt";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileRead(); // 파일을 로드하여 해시에 단어 저장
					// - 1번만 수행 : 실행시간 단어 수에 관계없이 1
		while (true) {
			Input = Scan(); // 단어 입력
			if (Input.equals("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			mat = rootSet.contains(Input.hashCode());
			if (mat == true) {
				System.out.println("원형 = " + Input + "\t	접미사 = 없음");
			} else {
				find();
			}
		}
	}
	
	static void FileRead() { // 파일을 읽어 해시셋에 저장

		try {
			BufferedReader in;
			in = new BufferedReader(new FileReader(FileName));
			String hashText;

			while ((hashText = in.readLine()) != null) {
				rootSet.add(hashText.hashCode());
			}

			in.close();

		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}
	}

	private static String Scan() { // 단어 입력
		// TODO Auto-generated method stub
		String inputText;
		System.out.print("input : ");
		Scanner sc = new Scanner(System.in);
		inputText = sc.nextLine();
		return inputText;
	}

	private static boolean matching(String suffix) {

		boolean suffixmatching = false;
		// suffix가 suffixlist에 있는지 없는지를 검토 : 원형의 반복 입력 방지
		int i = 0;
		while (!suffixmatching) {
			if (suffixlist[i].equals(suffix) == true) {
				return true;
			} else {
				i++;
				if (i == suffixlist.length) {
					// suffix가 suffixlist에 없는 경우 break
					break;
				}
			}
		}

		return false;
	}

	private static void find() { // 오버로딩
		for (int i = 0; i < Input.length(); i++) {
			String subString = Input.substring(0, Input.length() - i);
			String suffix = Input.substring(Input.length() - i, Input.length());

			mat = rootSet.contains(subString.hashCode());
			// 단어를 뒤에서 하나씩 잘라가면서 해시코드로 확인 - 실행시간 단어 수에 관계없이 1

			if (mat == true) { // 단어를 뒤에서 하나씩 잘라가면서 확인이 될 경우
				mat = matching(suffix);
				// 원형을 중복 할 시에 대한 처리 - ex) dodo --> 원형 do 접미사 do 방지
				if (mat == true) {
					System.out.println("원형 = " + subString + "\t	접미사 = -" + suffix);
				} else {
					System.out.println("없는단어");
				}
				break;
			} else { // 매칭 되는 단어가 없을 경우
				if (suffix.equals("ing")) { // suffix 가 ing일 경우
					mat = find(subString, "ing", "e");
					// 접미사 ing subString+e
					if (mat == true)
						break; // 단어 검색이 완료될 경우에 break
				} else if (suffix.equals("iful")) { // suffix가 iful 일 경우
					mat = find(subString, "ful", "y");
					// 접미사 ful subString+y
					if (mat == true)
						break;
				} else if (suffix.equals("ying")) { // suffix가 ying일 경우
					mat = find(subString, "ing", "ie");
					// 접미사 ing subString+ie
					if (mat == true)
						break;
				} else if (suffix.equals("ied")) { // suffix가 ied일 경우
					mat = find(subString, "ed", "y");
					// 접미사 ed를 subString+y
					if (mat == true)
						break;
				} else if (suffix.equals("iness")) { // suffix가 iness일 경우
					mat = find(subString, "ness", "y");
					// 접미사 ness, subString+y
					if (mat == true)
						break;
				} else if (i == Input.length() - 1 && mat == false) {
					// 끝까지 돌았고, 매칭 되는게 없을 때
					System.out.println("없는 단어");
					break;
				}
			}
		}
	}

	private static boolean find(String text, String matching, String add) {
		// 자른 단어와 더해질 단어를 조합해서 단어를 만들고, 접미사는 matching으로 둠
		mat = rootSet.contains((text + add).hashCode());
		if (mat == true) {
			System.out.println("원형 = " + text + add + "\t	접미사 = -" + matching);
			return true;
		} else {
			return false;
		}
	}
}