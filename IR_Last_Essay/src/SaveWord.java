import java.util.HashMap;
import java.util.HashSet;

/**
 * 단어 배열을 가지고 와서 스테밍을 하고,
 * 
 * 중복을 제거하여 hashSet과 hashMap에 저장하는 클래스입니다.
 * 
 * hashMap에는 단어와 그 단어의 빈도수가 저장됩니다.
 * 
 * hashSet에는 단어 리스트가 저장됩니다.
 * 
 * ****** 사용법 ******
 * 
 * 외부 클래스에서 사용법
 * 
 * 1. HashSet 사용법
 * 
 *   1.1) SaveWord saveWord = new SaveWord();
 * 
 *   1.2) saveWord.setHashSetWord(배열);
 * 
 *   1.3) HashSet hashSet = saveWord.getHashSetWord();
 * 
 *   1.4) ArrayList array = new ArrayList(hashSet);
 * 
 *   1.5) System.out.println(array.get[index]);
 * 
 * 2. HashMap 사용법
 * 
 *   2.1) SaveWord saveWord = new SaveWord();
 * 
 *   2.2) saveWord.setHashSetWord(배열);
 * 
 *   2.3) HashMap hashMap = saveWord.getHashMapFrequence();
 * 
 *   2.4) System.out.println(hashMap.get(key));
 * 
 * 3. 통합 예제
 * 
 *   3.1) SaveWord saveWord = new SaveWord();
 * 
 *   3.2) saeWord.setHashSetWord(배열);
 * 
 *   3.3) HashSet hashSet = saveWord.getHashSetWord();
 * 
 *   3.4) HashMap hashMap = saveWord.getHashMapFrequence();
 * 
 *   3.5) ArrayList array = new ArrayList(hashSet);
 * 
 *   3.6) System.out.println(array.get[index] + "  " + hashMap.get(array.get[index]));
 * 
 * @author HunJin
 *
 */
public class SaveWord {

	HashMap<String, Integer> hashMapFrequence = new HashMap<>();
	HashSet<String> hashSetWord = new HashSet<>();
	HashSet<String> totalHashSetWord = new HashSet<>();
	HashMap<String, Integer> totalHashMapWord = new HashMap<>();
	/**
	 * hashSet을 반환합니다.
	 * @return
	 */
	public HashSet<String> getHashSetWord() {
		return hashSetWord;
	}

	/**
	 * hashMap을 반환합니다.
	 * @return
	 */
	public HashMap<String, Integer> getHashMapFrequence() {
		return hashMapFrequence;
	}
	
	/**
	 * totalHashSet을 반환합니다.
	 * @return
	 */
	public HashSet<String> getTotalHashSetWord() {
		return totalHashSetWord;
	}

	/**
	 * totalHashMap을 반환합니다.
	 * @return
	 */
	public HashMap<String, Integer> getTotalHashMapWord() {
		return totalHashMapWord;
	}

	/**
	 * 받아온 배열을 구분합니다.
	 * 
	 * 단어를 스테밍합니다.
	 * 
	 * hashSet에 스테밍한 단어가 있는지 없는지를 검사합니다.
	 * 
	 * hashSet에 없는 단어이면 hashSet에 저장을 합니다.
	 * 
	 * hashMap에 value를 1로 하여 저장합니다.
	 * 
	 * 만약 hashSet에 있는 단어이면 hashMap의 value 값을 1 더하여 저장합니다.
	 * 
	 * @param text
	 */
	public void setHashSetWord(String[] text) {
		hashSetWord = new HashSet<>();
		hashMapFrequence = new HashMap<>();
		for (String string : text) {
			string = new Stemming().getText(string);
			if (hashSetWord.contains(string)) {
				hashMapFrequence.put(string, hashMapFrequence.get(string) + 1);
				continue;
			} else {
				hashSetWord.add(string);
				hashMapFrequence.put(string, 1);
			}
		}
	}
	
	/**
	 * 전체 문서의 단어를 저장하기 위한 메서드 입니다.
	 * 
	 * 기본적으로 setHashSetWord와 같은 기능을 합니다.
	 * 
	 * 다만, 하나의 HashSet과 HashMap을 이용합니다.
	 * 
	 * @param text
	 */
	public void setTotalWord(String[] text) {
		for (String string : text) {
			string = new Stemming().getText(string);
			if(totalHashSetWord.contains(string)) {
				totalHashMapWord.put(string, totalHashMapWord.get(string)+1);
				continue;
			} else {
				totalHashSetWord.add(string);
				totalHashMapWord.put(string, 1);
			}
		}
	}
}
