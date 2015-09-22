import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class IR_Main {

	
	static String[] text = {"go","can","do","man","clock","ask"};
	static String[] multi = {"s", "es"};
	static String[] past = {"d", "ed"};
	static String[] now = {"ing"};
		
	static String[][] matching = {text, multi, past, now};
	
	static String Input = null;
	static boolean success = false;
	static 	Set<Integer> rootSet = new HashSet<Integer>();
	static boolean mat = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		new FileRead(rootSet);
		FileRead();
		while(true) {
			Input = Scan();

			mat = rootSet.contains(Input.hashCode());
			if(mat == true) {
				System.out.println(Input);
			} else {
				find();
			}
		}
		
	}
	
	static void FileRead() {
						
			String FileName = "dictionary - make.txt";
			try {
				BufferedReader in = new BufferedReader(new FileReader(FileName));
				String s;

				while ((s = in.readLine()) != null) {
					rootSet.add(s.hashCode());
				}
				
				in.close();
			   
			} catch (IOException e) {
				System.err.println(e); // ������ �ִٸ� �޽��� ���
				System.exit(1);
			}
	}
	

	private static String Scan() {
		// TODO Auto-generated method stub
		String inputText;
		System.out.print("input : ");
		Scanner sc = new Scanner(System.in);
		inputText = sc.nextLine();
		return inputText;
	}
	
	private static void find() {
		for(int i=0;i<Input.length();i++) {
			mat = rootSet.contains(Input.substring(0,Input.length()-i).hashCode());
			if(mat == true) {
				System.out.println(Input.substring(0,Input.length()-i) + " " + Input.substring(Input.length()-i, Input.length()));
				break;
			}
		}
	}

}