import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*name:Zhongzheng Xiang
 * Blackboard login:zxiang5
 * Course number.section:600.226.02
 * assignment number:P1
 * */

public class WordList {
	private Map<Integer,ArrayList<String>> wordMap;//the word grouped by the length
	
	public WordList(String fileName){
		wordMap=new HashMap<Integer, ArrayList<String>>();
		initWordMap(fileName);
	}
	
	private void initWordMap(String fileName){
		try {
            Scanner in = new Scanner(new File(fileName));
            ArrayList<String> list;
            
            while (in.hasNextLine()) {
                String str= in.nextLine();
                String[] line=str.split(" ");
                for(int i=0;i<line.length;i++){
                	String word=line[i];
                	list=wordMap.get(word.length());
                	if(list==null){
                		list=new ArrayList<String>();
                	}
                	list.add(word);
                	wordMap.put(word.length(),list);                	
                }
            }
            
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	
	public int getRandomLength(){
		Set<Integer> set=wordMap.keySet();
		int size=set.size();
		int i = (int)(Math.random()*(size));
		Object[] temp=set.toArray();
		return (int) temp[i];
	}

	public Map<Integer, ArrayList<String>> getWordMap() {
		return wordMap;
	}

	public void setWordMap(Map<Integer, ArrayList<String>> wordMap) {
		this.wordMap = wordMap;
	}
	
}
