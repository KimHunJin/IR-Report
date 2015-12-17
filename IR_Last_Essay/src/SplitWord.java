
/**
 * 문자열을 구분하는 클래스입니다.
 * 
 * ****** 사용법 ******
 * 
 * 다른 클래스에서 사용법
 * 
 *   1) String[] text = new SplitWord.split(text);
 * 
 * @author HunJin
 *
 */
public class SplitWord {

	/**
	 * 문자열을 공백을 기준으로 구분합니다.
	 * 
	 * @param text
	 * @return
	 */
	public String[] getSplit(String text) {
		return text.split(" ");
	}
}
