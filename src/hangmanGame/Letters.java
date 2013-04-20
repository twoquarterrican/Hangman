package hangmanGame;

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
	}
	
	public Letter getLetter(char chGuess) {
		int index = chGuess - 'A';
		if ( 0 <= index && index <= 26) {
			return letters[index];
		}
		return null;
	}
	
	public Letter getLetterAt(double x_canvas, double y_canvas) {
		return (Letter)this.getElementAt(this.getLocalPoint(x_canvas, y_canvas));
	}
	
	private Letter[] letters = new Letter[26];

}
