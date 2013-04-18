package hangmanGame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.graphics.GCompound;

public class Letters extends GCompound {
	
	
	public Letters(double x, double y, int num_letter_cols, 
			double letter_box_width, double letter_box_height,
			String secretWord) {
		this.setLocation(x,y);
		//variable letter will increment over the letters of the alphabet using character addition
		char letter = 'A';
		for (int i = 0; i < 26; i++) {
			//apparently you can't cast from char to string.  It seems like concatenating with the empty string is the obvious thing to do.
			letters[i] = new Letter(""+letter, 
					(i%num_letter_cols)*letter_box_width, 
					(i/num_letter_cols)*letter_box_height, 
					letter_box_width, letter_box_height);
			if (secretWord.indexOf(letter) == -1) {
				letters[i].setInWord(false);
			} else {
				letters[i].setInWord(true);
			}
			add(letters[i]);
			letter++;
		}
		listener = new ClickLetter();
		this.addMouseListener(listener);
		listenForGuess = false;
	}
	
	public Letter getLetter(char chGuess) {
		int index = chGuess - 'A';
		if ( 0 <= index && index <= 26) {
			return letters[index];
		}
		return null;
	}
	
	private class ClickLetter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			//ignore
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
		public void mousePressed(MouseEvent e) {
			if (listenForGuess) {
				clickLetterAt(e.getX(),e.getY());
			}			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//ignore			
		} 
		
	}
	
	public char clickLetterAt(double x_canvas, double y_canvas) {
		Letter letter = (Letter)this.getElementAt(this.getLocalPoint(x_canvas, y_canvas));
		if (letter != null) {
			//make sure the letter clicked has not been clicked yet, and make sure we are not clicking two different letters very quickly
			if (letter.hasNotBeenGuessed() && listenForGuess) {
				letter.setAsGuessed();
				letterJustGuessed = letter.getCh();
				//tell readline what guess we have
				//this.getComponent().transferFocusBackward();
				//robot.simulateInput(letter.getCh());
				listenForGuess = false;
			}
			return letter.getCh();
		}
		return 0;
	}
	
	private ClickLetter listener;
	private boolean listenForGuess;
	private Letter[] letters = new Letter[26];
	private char letterJustGuessed;


	public char waitForGuess() {
		listenForGuess = true;
		while (true) {
			if (!listenForGuess) break;
		}
		return letterJustGuessed;
	}
	
}
