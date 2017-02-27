/* author: XZZ
 * function: Guessing a chosen word in several times
 * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Game {
	private int countOfGames;
	private int countOfWin;
	private int countOfLose;
	private int length; //the guessed words 
	private int countOfguess;
	private ArrayList<String> currentList;
	private WordFamily wordFamily;
	private static WordList wordList; //the origin words
	private static String guessword="";
	String tips="";
	
	public Game(){
		currentList=new ArrayList<String>();
	}
	
	public static void main(String[] args) {
		boolean goOn=true;
		Game game=new Game();
		
		//init the game 
		System.out.println("Welcome to Hangman!");
		System.out.print("Please input your file name:");
		Scanner in=new Scanner(System.in);
		String fileName=in.nextLine();
		System.out.println("Word importing...");
		wordList=new WordList(fileName);
		System.out.println("Succeed!");
		
		while(goOn){//one game
			System.out.println("GAME START!");
			//STEP 1:get random length and set guesses;
			int random =wordList.getRandomLength();
			game.setLength(random);
			game.setCountOfguess(6);
		
			ArrayList<String> initList=wordList.getWordMap().get(random);
			game.setCurrentList(initList);
			for(int i=0;i<random;i++){
				guessword+="-";
			}

			System.out.println("The length of the word is "+random);
			//STEP 2:repeat input
			boolean isLetter=true;
			String letter="";
			while(game.getCountOfguess()>0&&isLetter){
			System.out.println("The word now looks like:"+guessword);
			System.out.println("You have "+game.getCountOfguess()+" guesses left! (Each time guess one letter in the word.)");
			game.wordFamily=new WordFamily(random, game.getCurrentList());
			System.out.print("Your guess:");
			letter=in.nextLine();
			if(letter.length()>1){
				if(letter.equals("DEBUG")){ //print the current list	
					game.printTheList();
					continue;
				}else{
				isLetter=false;
				break;
				}
			}
				//deal 
				game.dealWithInput(letter);
			}
			
			//step3:result and restart
			game.setCountOfGames(game.getCountOfGames()+1);
			if(game.getCurrentList().contains(letter)){
				game.setCountOfWin(game.getCountOfWin()+1);
				System.out.println("You win!The word is "+letter+"!");
			}else{
				game.setCountOfLose(game.getCountOfLose()+1);
				System.out.println("You lose!");
			}
			
			boolean flag = true;
			while(flag){
    			System.out.print("Go on?Y/N:");
    			String choose=in.nextLine().toUpperCase();
    			if(choose.equals("N")){
    				goOn = false;
    				flag = false;
    			}else if(choose.equals("Y")){
    			    goOn = true;
    			    flag = false;
    			}
			}
			guessword="";
			game.tips="";
			letter="";
		}
		//step4:end
		System.out.println("Game Over!");
		System.out.println("You played "+game.getCountOfGames()+" times, won "
		+game.getCountOfWin()+" times and lost "+game.getCountOfLose()+" times!");
		in.close();
	}
	
	private void dealWithInput(String letter){
		wordFamily.updateWordMap(letter);
		Entry<String, ArrayList<String>> entry=chooseTheList(letter);
		String judge=entry.getKey();
		ArrayList<String> newList=entry.getValue();
		countOfguess--;
		if(newList==null||newList.size()==0){
			System.out.println("No!There is no "+letter+" in the word!");
		}else{
			System.out.println(tips);
			currentList=newList;
			wordFamily.setCurrentList(newList);
			StringBuffer strb = new StringBuffer(guessword);
			for(int i=0;i<judge.length();i++){
				if(judge.charAt(i)=='1'){					
			           //StringBuffer strb
					strb.setCharAt(i,(letter.toCharArray())[0]); 
				}
			}
			guessword=strb.toString();
		}
	}
	private Entry<String, ArrayList<String>> chooseTheList(String letter){
		Map<String, ArrayList<String>> map=wordFamily.getWordMap();	
		 Iterator<Entry<String, ArrayList<String>>> it = map.entrySet().iterator();
		 Entry<String, ArrayList<String>> result = null;
		 if(it.hasNext()){
			 result=it.next();
		 }
		 while (it.hasNext()) {
		   Entry<String, ArrayList<String>> entry = it.next();
		   int newSize=entry.getValue().size();
		   int oldSize=result.getValue().size();
		   if(newSize>oldSize) {
			   result=entry;
		   }else if(newSize==oldSize){
			   int countOfNew=countTrue(entry.getKey());
			   int countOfOld=countTrue(result.getKey());
			   if(countOfNew<countOfOld){
				   result=entry;
			   }
		   }
		  }
		if(countTrue(result.getKey())==0){
			tips="There is no "+letter+" in the word!";
		}else{
			tips="Yes!";
		}
		return result;
	}
	private int countTrue(String key){
		int countTrue=0;
		for(int i=0;i<key.length();i++){
			if(key.charAt(i)=='1'){countTrue++;}
		}
		return countTrue;
	}
	private void printTheList(){
		for(int i=0;i<currentList.size();i++){
			if((i+1)%10==0){
			System.out.print(currentList.get(i)+"\n");
			}else{
			System.out.print(currentList.get(i)+" ");	
			}
		}
		System.out.println("\n");
	}
	public int getCountOfGames() {
		return countOfGames;
	}

	public void setCountOfGames(int countOfGames) {
		this.countOfGames = countOfGames;
	}

	public int getCountOfWin() {
		return countOfWin;
	}

	public void setCountOfWin(int countOfWin) {
		this.countOfWin = countOfWin;
	}

	public int getCountOfLose() {
		return countOfLose;
	}

	public void setCountOfLose(int countOfLose) {
		this.countOfLose = countOfLose;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCountOfguess() {
		return countOfguess;
	}

	public void setCountOfguess(int countOfguess) {
		this.countOfguess = countOfguess;
	}

	public ArrayList<String> getCurrentList() {
		return currentList;
	}

	public void setCurrentList(ArrayList<String> currentList) {
		this.currentList = currentList;
	}

	public WordFamily getWordFamily() {
		return wordFamily;
	}

	public void setWordFamily(WordFamily wordFamily) {
		this.wordFamily = wordFamily;
	}
	
}
