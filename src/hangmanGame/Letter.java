package hangmanGame;
import java.awt.Color;
import java.awt.Font;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;


public class Letter extends GCompound {
	
	private static final Color CORRECT_GUESS_COLOR = Color.GREEN;
	private static final Color INCORRECT_GUESS_COLOR = Color.RED;
	private final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 28);
	private final Color DEFAULT_FONT_COLOR = Color.BLUE;
	private Color UNSELECTED_COLOR = Color.WHITE;
		
	/** centers the letter in a box of given width and height with the same (x,y) coordinate*/
	public Letter(String string, double x, double y, double width, double height){
		this.setLocation(x,y);
		ch = string.charAt(0);
		hasBeenGuessed = false;
		letter = new GLabel(string);
		letter.setFont(DEFAULT_FONT);
		letter.setColor(DEFAULT_FONT_COLOR);
		//letter.setLocation((width - letter.getWidth())/2, (height + letter.getAscent())/2);
		letter.setLocation((width - letter.getWidth())/2,(height + letter.getAscent())/2);
		box = new GRect(0,0,width,height);
		box.setFillColor(UNSELECTED_COLOR);
		box.setFilled(true);
		add(box);
		add(letter);
		//add(new GLabel(""+letter.getAscent(),10,10));
		//add(new GLine(width/2, 0, width/2, height));
		//add(new GLine(0, height/2, width, height/2));
	}
	
	public char getCh() {
		return ch;
	}
	
	private GLabel letter;
	private GRect box;
	private char ch;
	private boolean hasBeenGuessed;
	private boolean inWord;

	public boolean hasNotBeenGuessed() {
		return !hasBeenGuessed;
	}
	
	public void setInWord(boolean b) {
		inWord = b;
	}

	public void setAsGuessed() {
		hasBeenGuessed = true;
		if (inWord) {
			box.setFillColor(CORRECT_GUESS_COLOR);
		} else {
			box.setFillColor(INCORRECT_GUESS_COLOR);
		}
	}

	public boolean hasBeenGuessed() {
		return hasBeenGuessed;
	}
}
