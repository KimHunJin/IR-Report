package search;

public class LogMethod {
	
	public static void main(String[] args) {
		
		System.out.println("log2(8) : " + baseLog(8,2));
		System.out.println("log3(9) : " + baseLog(9,3));
	}
	
	static double baseLog(double x, double base) {
		
		return Math.log10(x) / Math.log10(base);
	}
	
}
