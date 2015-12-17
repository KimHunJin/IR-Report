/**
 * 밑을 제어할 수 있는 로그 클래스입니다.
 * 
 * ****** 사용법 ******
 * 
 * double log = new BaseLog.logB(지수,밑);
 * 
 * @author HunJin
 *
 */
public class BaseLog {
	
	public double logB(double x, double b) {
		return Math.log10(x) / Math.log10(b);
	}
}