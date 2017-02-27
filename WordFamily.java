import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*name:Zhongzheng Xiang
 * Blackboard login:zxiang5
 * Course number.section:600.226.02
 * assignment number:P1
 * */
public class WordFamily { //update after every guess 
	int length;
	private Map<String, ArrayList<String>> wordMap;
	//the word grouped by the existence of the letter
	private ArrayList<String> currentList;
	
	public WordFamily(int wordLength, ArrayList<String> list){
		length=wordLength;
		currentList=list;
		wordMap=new HashMap<String, ArrayList<String>>();
	}
	
	public void updateWordMap(String letter){ //the input is a letter,grouping
		for(String temp:currentList){
			String exist=getExistOfletter(temp,letter);
			ArrayList<String> list=wordMap.get(exist);
			if(list==null){
				list=new ArrayList<String>();
			}
			list.add(temp);
			wordMap.put(exist, list);
		}
	}
	
	private String getExistOfletter(String in,String letter){
		String result="";
		for(int i=0;i<in.length();i++){
			String temp=in.charAt(i)+"";
			if(temp.equals(letter)){
				result+="1";
			}else{
				result+="0";
			}
		}
		return result;
	}
	
	public Map<String, ArrayList<String>> getWordMap() {
		return wordMap;
	}
	
	public void setWordMap(Map<String, ArrayList<String>> wordMap) {
		this.wordMap = wordMap;
	}
	
	public ArrayList<String> getCurrentList() {
		return currentList;
	}
	
	public void setCurrentList(ArrayList<String> currentList) {
		this.currentList = currentList;
	}
	
}
