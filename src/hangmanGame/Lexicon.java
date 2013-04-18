package hangmanGame;


/* 
 * File: HangmanLexicon.java 
 * ------------------------- 
 * This file contains a stub implementation of the HangmanLexicon 
 * class that you will reimplement for Part III of the assignment. 
 */ 

import java.io.*;
import java.util.ArrayList;
import acm.util.ErrorException;

public class Lexicon { 

	private BufferedReader br;
	private ArrayList<String> wordList = new ArrayList<String>();
	
	public Lexicon() {
		//initialize br
		openTextFile();
		//initialize wordList
		readThroughTextFile();
	}
	
	private void readThroughTextFile() {
		wordList = new ArrayList<String>();
		try{
			while(true) {
			String line = br.readLine();
			if (line == null) break;
			wordList.add(line);
			}
			br.close();
		} catch (IOException e) {
				throw new ErrorException(e);
		}
	}
	
	/** Returns the number of words in the lexicon. */ 
	public int getWordCount() { 
		return wordList.size(); 
	} 

	/** Returns the word at the specified index. */ 
	public String getWord(int index) {
		int choose = 0;
		String word = null;
		switch (choose) {
		case 0: word = getWordFromSmallList(index);
		case 1: word = getWordFromTextFile(index);
		}
		return word;
	}
	
	private String getWordFromSmallList(int index) {
		index = Math.abs(index)%10;
		switch (index) { 
		case 0: return "BUOY"; 
		case 1: return "COMPUTER"; 
		case 2: return "CONNOISSEUR"; 
		case 3: return "DEHYDRATE"; 
		case 4: return "FUZZY"; 
		case 5: return "HUBBUB"; 
		case 6: return "KEYHOLE"; 
		case 7: return "QUAGMIRE"; 
		case 8: return "SLITHER"; 
		case 9: return "ZIRCON"; 
		default: throw new ErrorException("getWord: Illegal index"); 
		} 
	}; 
	
	public String getWordFromTextFile(int index) {
		index = Math.abs(index) % wordList.size();
		return wordList.get(index);
	}
	
	private void openTextFile() {
		String file = "HangmanLexicon.txt";
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new ErrorException(e);
		}
	}
} 