package hangmanGame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class Game extends ConsoleProgram {

	public static final int APPLICATION_WIDTH = 620;
	public static final int APPLICATION_HEIGHT = 690;
	public static final int APPLICATION_X = 0;
	public static final int APPLICATION_Y = 0;
	private static final int MAX_GUESSES = BodyParts.NUM_BODY_PARTS;
	
	private Lexicon lexicon;
	private String secretWord;
	private int numGuesses;
	private String guessWord;
	private HangmanCanvas canvas;
	
	public static void main(String[] args) {
		new Game().start(args);
	}
	
	public void init() {
		//initialize the list of possible words
		lexicon = new Lexicon();
		//initialize the canvas.  This splits the screen in half, with left side = console and right side = canvas.
		//Cannot do this in the run() method
		canvas = new HangmanCanvas();
		add(canvas);
//		canvas.addMouseListener(new LetterClickListener());
//		addKeyListener(new LetterTypeListener());
	}

	public void run() {
		while (true) {
			play();
			if(!PlayAgain()) break;
		}
		println("goodbye");
		exit();
	}

	private boolean PlayAgain() {
		while(true) {
			String again = readLine("Play Again? (Y/N) :");
			if(again.equals("")) continue;
			char chAgain = again.toUpperCase().charAt(0);
			if (chAgain == 'Y') {
				return true;
			}
			if (chAgain == 'N') {
				return false;
			}
			println("You entered " + chAgain);
			println("This is not a valid response.");
		}
	}

	private void play() {
		//getRandomWord also sets guessWord = "-----", with the appropriate number of -'s
		secretWord = getRandomWord();
		canvas.reset(secretWord, guessWord);
		println("Welcome to Hangman!");
		//static field in HangmanPic class gives total number of parts that can be drawn, make this the total number of guesses
		numGuesses = MAX_GUESSES;
		takeGuesses();
		showResult();
	}
	
	private void takeGuesses() {
		//game loop
		while(true) {
			println("The word now looks like this: " + guessWord);
			println("You have " + numGuesses + " guesses left.");
			//make sure we get just one character which has not been guessed before
			char guess = getAValidGuess();
			//evaluate..() updates either numGuesses or guessWord
			evaluateValidGuess(guess);
			//out of guesses or word guessed completely? guessWord starts off as "-----"
			if (numGuesses == 0 || guessWord.indexOf('-') == -1) break;
		}

	}

	private void showResult() {
		if (numGuesses == 0) {
			println("You lose.");
			canvas.displayWord(guessWord);
			println("The word was: " + secretWord);
			println("You lose.");
		} else {
			println("You guessed the word: " + secretWord);
			println("You win.");
			canvas.playWinAnimation();
		}
	}

	boolean isNewGuess(char guess) {
		//did the player already guess this character?
		Letter letter = canvas.getLetters().getLetter(guess);
		if (letter.hasBeenGuessed()) {
			//a repeat guess
			println("You already guessed " + guess);
			return false;
		} 
		//a new guess
		letter.setAsGuessed();
		return true;
	}

	private void evaluateValidGuess(char guess) {

		//this method assumes guess un-guessed letter, so this method
		//only needs to decide if numGuesses is decremented or if guessWord is updated
		
		//by default chGuess not found in secretWord
		boolean found = false;
		//the index variable iterates over all positions of chGuess in secretWord.  Initialize to -1, which
		//is the value of secretWord.indexOf(chGuess) if chGuess is not found
		int index = -1;
		//sentinel for while loop is chGuess no longer found in substring of secretWord ranging from index+1 to the end
		while (true) {
			//get the index of chGuess starting from next place to look (whole word if index == -1
			index = secretWord.indexOf(guess, index+1);
			//if chGuess not found in this substring of secretWord, break out of the loop
			if (index == -1 ) break;
			//if chGuess is found in this substring of secretWord, put chGuess into index position of guessWord
			guessWord = guessWord.substring(0, index) + guess + guessWord.substring(index+1);
			found = true;
		}
		if (!found) {
			//guess is incorrect
			println("There are no "+guess+"\'s in the word.");
			numGuesses--;
			canvas.noteIncorrectGuess(numGuesses);
		} else {
			//guess is correct
			println(guess + " is a correct guess!");
			canvas.noteCorrectGuess(guessWord);
		}
	}
	
	//returns a guess guaranteed to be in the alphabet and not already guessed
	private char getAValidGuess() {
		while(true) {
			String line;
			line = readLine("Choose a letter: ");
			if (line.equals("")) continue;
			char guess = line.toUpperCase().charAt(0);
			if ('A' <= guess && guess <= 'Z' && isNewGuess(guess)) return guess;
		}
	}

	private String getRandomWord() {
		//get a random generator which will choose a random word below
		RandomGenerator rgen = RandomGenerator.getInstance();
		//valid wordNum 's are 0, 1, ..., (number of words in lexicon minus one)
		int maxIndex = lexicon.getWordCount()-1;
		int randomIndex = rgen.nextInt(0, maxIndex);
		String word = lexicon.getWord(randomIndex);
		guessWord = "";
		for(int i = 0; i < word.length(); i++){
			guessWord += "-";
		}
		return word;
	}
	public void mousePressed(MouseEvent e) {
		
		System.out.println("unclassed mouse event fired");
		String message = "mouse event at (" + e.getX() + "," + e.getY() + ")\n";
		message += "  Letter clicked was ";
		Letter letter = canvas.getLetters().getLetterAt(e.getX(),e.getY());
		message += (letter == null ? "null" : letter.getCh());
		System.out.println(message);
	}
	
//	private class LetterClickListener implements MouseListener {
//
//		@Override
//		public void mouseClicked(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			System.out.println("mouse pressed");
//			Game.this.getWriter().write("mouse pressed");
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// ignore
//			
//		}
//
//	}
//	
//	private class LetterTypeListener implements KeyListener {
//		public void keyPressed(KeyEvent e) {
//			String message = "key event:\n";
//			message += "  Key pressed was " + e.getKeyChar();
//			System.out.println(message);
//		}
//
//		@Override
//		public void keyReleased(KeyEvent arg0) {
//			// ignore
//
//		}
//
//		@Override
//		public void keyTyped(KeyEvent arg0) {
//			// ignore
//			System.out.println("stuff");
//		}
//	}
	
}
