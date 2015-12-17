import java.util.Scanner;

/**
 * 하나의 단어를 받으면 그 단어를 스테밍 하여 반환을 하는 클래스입니다.
 * 
 * 완벽한 스테밍 알고리즘을 구현하지는 못했으나 나름 괜찮은 것 같습니다.
 * 
 * 단어의 특수문자를 제거합니다.
 * 
 * 단어가 동사인지 아닌지를  결정합니다.
 * 
 * 단어가 동사일 경우 그 단어를 제거합니다.
 * 
 * 단어가 명사일 경우 그 단어를 반환합니다.
 * 
 * 단어에 불용어가 섞여 있을 경우 불용어를 삭제하여 반환합니다.
 * 
 * 이 클래스의 테스트 결과가 궁금하면 main() 메서드의 주석을 제거하시길 바랍니다.
 * 
 * ****** 사용법 ****** 
 * 
 * 1. 다른 클래스에서 사용법 
 *  
 *   1.1) Stemming stemming = new Stemming(); // Stemming 클래스를 생성합니다.
 *    
 *   1.2) String word = stemming.getText(단어); // stemming에 단어를 입력함으로서 단어를 Stemming합니다.
 * 
 * 2. 이 클래스에서 사용법 
 *  
 *   2.1) main() 메서드의 주석을 삭제합니다. (Line 67 ~ 72)
 * 
 * @author HunJin
 *
 */
public class Stemming {

	/**
	 * 스테밍을 합니다. 
	 * 
	 * 동사는 그 단어를 삭제합니다. 
	 * 
	 * 불용어일 경우 그 불용어를 삭제합니다. 
	 * 
	 * 동사의 경우 길이가 긴 것부터 비교를합니다.
	 */
	final static private String[] VERBE1 = { "다", "자", "까", "요", "죠", "대", "제", "해" };
	final static private String[] VERBE2 = { "있다", "하다", "이다", "하자", "할까", "에요",
			"하죠", "있죠","있대", "하제", "자요", "돼요", "해요",
			"되다", "돼요", "될까", "됐다", "가요", "가자", "가죠", "한대" };
	final static private String[] VERBE3 = { "입니다", "합니다", "됩니다", "할까요" };
	final static private String[][] VERBE = { VERBE1, VERBE2, VERBE3 };
	final static private String[] DUMP = { "을", "를", "이", "가", "는",
			"면", "에", "한", "로", "의", "와", "과","야" };

	/**
	 * text를 스태밍 하여 반환 합니다.
	 * 
	 * @return
	 */
	public String getText(String text) {
		return stemming(text);
	}

	/**
	 * 이 클래스의 결과가 궁금하면 이(main) 메서드의 주석을 지우세요.
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		System.out.print("스테밍 하고자 하는 단어를 입력하세요 : ");
//		Scanner sc = new Scanner(System.in);
//		Stemming stemming = new Stemming();
//		System.out.println("추출 : " + stemming.getText(sc.nextLine()));
//	}

	/**
	 * 문자를 받으면 그 문자를 특수문자, 동사, 불용어 순으로 스테밍합니다.
	 * 
	 * @param text
	 */
	private String stemming(String text) {
		text = stringReplace(text);
		if(text.equals("")) {
			return text;
		} else {
			text = verbeStemming(text);
			if (text.equals("")) {
				return text;
			} else {
				return dumpStemming(text);
			}
		}

	}
	
	/**
	 * 숫자와 특수문자를 제거하는 메서드입니다.
	 * 
	 * @param text
	 * @return
	 */
	private String stringReplace(String text) {
		String match = "[^\uAC00-\uD7A3xfea-zA-Z\\s]";
		text = text.replaceAll(match, "");
		return text;
	}

	/**
	 * 문자의 길이를 구하여 가장 적합한 스테밍부터 우선 처리합니다.
	 * 
	 * @param text
	 * @return
	 */
	private String verbeStemming(String text) {

		if (text.length() > 3) {
			text = loopText(text, 2);
		} else if (text.length() > 2) {
			text = loopText(text, 1);
		} else if (text.length() > 1) {
			text = loopText(text, 0);
		}
		return text;
	}

	/**
	 * 비교하는 문자의 수를 3개에서 반복적으로 줄여 검토하는 메서드입니다.
	 * 
	 * @param text
	 * @param number
	 * @return
	 */
	private String loopText(String text, int number) {
		for (int i = number; i >= 0; i--) {
			text = verbeStemmingCommon(text, VERBE[i], i + 1);
			if (text.equals("")) {
				break;
			}
		}
		return text;
	}

	/**
	 * 동사를 삭제하는 스테밍에서 공통적으로 수행되는 메서드 입니다.
	 * 
	 * 매핑이 될 경우 그 단어를 삭제시킵니다.
	 * 
	 * @param text
	 * @param verbe
	 * @param count
	 * @return
	 */
	private String verbeStemmingCommon(String text, String[] verbe, int count) {
		String suffixText = suffixText(text, count);
		for (int i = 0; i < verbe.length; i++) {
			if (suffixText.equals(verbe[i])) {
				text = "";
				break;
			}
		}
		return text;
	}

	/**
	 * 명사 스테밍 메서드 입니다.
	 * 
	 * @param text
	 * @return
	 */
	private String dumpStemming(String text) {
		String suffixText = suffixText(text, 1);
		for (int i = 0; i < DUMP.length; i++) {
			if (suffixText.equals(DUMP[i])) {
				text = text.substring(0, text.length() - 1);
				break;
			}
		}
		return text;
	}

	/**
	 * 단어를 워하는 길이만큼 잘라내는 함수입니다.
	 * 
	 * @param text
	 * @param number
	 * @return
	 */
	private String suffixText(String text, int number) {
		return text.substring(text.length() - number, text.length());
	}
}
