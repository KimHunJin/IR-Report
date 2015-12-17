import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 파일 관리 클래스 입니다.
 * 
 * 파일에 저장할 수 있습니다.
 * 
 * ****** 사용법 ******
 * 
 * 다른 클래스에서 사용법
 * 
 * new FileManagement.saveFile(FileName, documentContents);
 * @author HunJin
 *
 */
public class FileManagement {
	
	public void saveFile(String mURL, String document) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(mURL+".txt"),true));
			bw.write(document);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}