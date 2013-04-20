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
	private boolean waitingForGuess = false;
	private Object syncObject = new Object();
	
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
		canvas.addMouseListener(new LetterClickListener());
		canvas.addKeyListener(new LetterTypeListener());
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

	private String getRandomWord() {
		//get a random generator which will choose a random word below
		RandomGenerator rgen = RandomGenerator.getInstance();
		//valid wordNum 's are 0, 1, ..., (number of words in lexicon minus one)
		int maxIndex = lexicon.getWordCount()-1;
		int randomIndex = rgen.nextInt(0, maxIndex);
		String word = lexicon.getWord(randomIndex);
		//unknown spots in secret word are blanks "-"
		guessWord = "";
		for(int i = 0; i < word.length(); i++){
			guessWord += "-";
		}
		return word;
	}
	
	public void run() {
		while (true) {
			play();
			if(!PlayAgain()) break;
			getConsole().clear();
		}
		println("goodbye");
		exit();
	}

	private void play() {
		//getRandomWord also sets guessWord = "-----", with the appropriate number of -'s
		secretWord = getRandomWord();
		canvas.reset(secretWord, guessWord);
		println("Welcome to Hangman!");
		//static field in HangmanPic class gives total number of parts that can be drawn, make this the total number of guesses
		numGuesses = MAX_GUESSES;
		//isGameOver returns true and prints loss or win message if game is over, otherwise returns false and prints nothing
		while(!isGameOver()) {
			requestGuess();
		}
	}

	private void requestGuess() {
		println("The word now looks like this: " + guessWord);
		println("You have " + numGuesses + " guesses left.");
		print("Choose a letter: ");
		//event loops do nothing unless this flag is set to true
		waitingForGuess = true;
		canvas.requestFocusInWindow();
		//use the synchronizing object to make this thread wait for a letter guess from the player
		synchronized (syncObject) {
			try {
				syncObject.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("caught interrupted exception");
			}
		}
	}
	
	private void guessReceived(char guess) {
		//flag tells us to ignor guesses or not.  We ignore when the main event loop is processing a guess or preparing for another one
		if (waitingForGuess) {
			guess = Character.toUpperCase(guess);
			println(guess);	
			if ('A' <= guess && guess <= 'Z' && isNewGuess(guess)) {
				//found a new guess
				waitingForGuess = false;
				evaluateValidGuess(guess);
				//use synchronizing object to wake up main thread now that we have a guess from the player
				synchronized (syncObject) {
					syncObject.notify();
				}
			} else {
				//found a repeat guess
				println("You already guessed " + guess);
				print("Choose a letter: ");
			}
		}
	}

	boolean isNewGuess(char guess) {
		//did the player already guess this character?
		//Letter objects store a flage telling if they have been guessed or not
		Letter letter = canvas.getLetters().getLetter(guess);
		if (letter.hasBeenGuessed()) {
			return false;
		} 
		//a new guess
		letter.setAsGuessed();
		return true;
	}

	private void evaluateValidGuess(char guess) {
		//this method assumes guess un-guessed letter, so this method only needs to decide if numGuesses is decremented or if guessWord is updated
		
		//by default guess not found in secret word
		boolean found = false;
		//the index variable iterates over all positions of chGuess in secretWord.  Initialize to -1, which is the value of secretWord.indexOf(chGuess) if chGuess is not found
		int index = -1;
		//look for all occurences of guess in the secret word
		while (true) {
			index = secretWord.indexOf(guess, index+1);
			if (index == -1 ) break;
			//if Guess is found in this substring of secretWord, put Guess into index position of guessWord
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
	
	private boolean isGameOver() {
		//out of guesses or word guessed completely? guessWord starts off as "-----"
		if (numGuesses == 0) {
			showLossMessage();
			return true;
		} else if (guessWord.indexOf('-') == -1) {
			showWinMessage();
			return true;
		} else {
			return false;
		}
	}

	private void showLossMessage() {
		println("You lose.");
		canvas.displayWord(guessWord);
		println("The word was: " + secretWord);
		println("You lose.");
	}
	
	private void showWinMessage() {
		println("You guessed the word: " + secretWord);
		println("You win.");
		canvas.playWinAnimation();
	}
	
	private class LetterClickListener implements MouseListener {
		//for clicked guesses
		
		@Override
		public void mousePressed(MouseEvent e) {
			Letter letter = canvas.getLetters().getLetterAt(e.getX(),e.getY());
			if (letter != null) guessReceived(letter.getCh());
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// ignore
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// ignore
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// ignore
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// ignore
		}

	}
	
	private class LetterTypeListener implements KeyListener {
		// for typed guesses
		public void keyPressed(KeyEvent e) {
			guessReceived(e.getKeyChar());
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// ignore
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// ignore
		}
	}
	
}

