package hangmanGame;
/* 
 * File: HangmanCanvas.java 
 * ------------------------ 
 * This file keeps track of the Hangman display. 
 */  

import java.awt.Font;
import acm.graphics.*;

@SuppressWarnings("serial")
public class HangmanCanvas extends GCanvas { 
		
	/*private instance variables*/
	private GLabel secretWordLabel;
	private BodyParts bodyParts;
	private Letters hangmanLetters;
	private static final int NUM_LETTER_COLS = 7;
	private static final int LETTER_BOX_WIDTH = 40;
	private static final int LETTER_BOX_HEIGHT = 40;
	private Thread winAnimation;
	Animation animation;
	
	/** Resets the display so that only the scaffold appears, and sets up the label holding the secret word, as well as the 
	 * alphabet.  The alphabet knows the word because it colors the guessed letters based on whether they were correct or incorrect
	 * */ 
	public void reset(String secretWord, String guessWord) { 
		endAnimations();
		removeAll();
		setUpHangmanPic();
		//the guessWord is currently something like "--------"
		setUpSecretWordLabel(guessWord);
		//the secret Word is the actual word the player is trying to guess
		drawAlphabet(secretWord);
		//troubleshooting: need to know word
		//add(new GLabel(secretWord,5,50));
	} 

	private void endAnimations() {
		//if the hangman animation ha has been initialized, tell it to stop execution
		if (animation != null) animation.end();
		//now the thread which was running animation.startAnimation() will come to an end.  Wait for it to end before moving on.  This insures we don't
		//have any leftover confetti, or snowflakes on the screen (or whatever the current celebration theme is)
		if (winAnimation != null) {
			try {
				winAnimation.join();
			} catch (InterruptedException e) {
				//don't care if winAnimation was interrupted
			}
		}
	}

	private void setUpHangmanPic() {
		bodyParts = new BodyParts();
		//compute left edge of hangmanPic
		add(bodyParts, (this.getWidth()-bodyParts.getWidth())/2,0);
	}

	private void setUpSecretWordLabel(String guessWord) {
		secretWordLabel = new GLabel(guessWord);
		secretWordLabel.setFont(new Font("Courier New", Font.BOLD, 28));
		secretWordLabel.setLocation(
				(getWidth()-secretWordLabel.getWidth())/2, 
				bodyParts.getY() + bodyParts.getHeight() + secretWordLabel.getHeight()*1.1);
		add(secretWordLabel);
	}

	/** 
	 * Updates the word on the screen to correspond to the current 
	 * state of the game.  The argument string shows what letters have 
	 * been guessed so far; unguessed letters are indicated by hyphens. 
	 */ 
	public void displayWord(String guessWord) { 
		secretWordLabel.setLabel(guessWord);
	} 


	
	private void drawAlphabet(String secretWord) {
		double x = (getWidth() - LETTER_BOX_WIDTH*NUM_LETTER_COLS)/2;
		double y = secretWordLabel.getY() + LETTER_BOX_HEIGHT/2;
		hangmanLetters = new Letters(x,y,
				NUM_LETTER_COLS, LETTER_BOX_WIDTH, LETTER_BOX_HEIGHT,
				secretWord);
		add(hangmanLetters);
	}
	
	
	public Letters getLetters() {
		return hangmanLetters;
	}

	public void noteCorrectGuess(String guessWord) {
		secretWordLabel.setLabel(guessWord);
	}


	public void noteIncorrectGuess(int numGuesses) {
		bodyParts.noteIncorrectGuess(numGuesses);
	}


	public BodyParts getBodyParts() {
		return bodyParts;
	}

	public void playWinAnimation() {
		winAnimation = new Thread(new Runnable(){
			public void run() {
				//constructor needs the canvas which it will add the celebration objects to.
				animation = new Animation(HangmanCanvas.this);
				animation.startAnimation();
			}
		});
		//setting gameNotRestartedYet to false will terminate winAnimation();
		winAnimation.start();
	}



}