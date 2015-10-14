package search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
	
	String FileName = "kakaotalk2.txt";
	
	private int FileRead() { // 파일을 읽어 해시셋에 저장

		int count = 0;
		int countMax = 0;
		try {
			BufferedReader in;
			in = new BufferedReader(new FileReader(FileName));
			String text;
			String match = "";
			
			 Pattern pattern = Pattern.compile(match, Pattern.CASE_INSENSITIVE);
			 Matcher matcher;

			
			/*while((text = in.readLine())!=null) {
				String[] text2 = text.split(" ");
				for(int i=0;i<text2.length;i++) {
					if(pattern.matcher(text2[i]).find()) {
						count++;
					}
					countMax++;
				}
			}*/
			
			while((text = in.readLine())!=null) {
				if(pattern.matcher(text).find()) {
					count++;
				}
				countMax++;
			}
			
			in.close();
			
			System.out.println(countMax + " / " + count);

		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		Search search = new Search();
		search.FileRead();
		
	}
}
