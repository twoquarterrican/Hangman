package hangmanGame;
import acm.program.GraphicsProgram;


@SuppressWarnings("serial")
public class tester extends GraphicsProgram {
	
	public void run() {
		addMouseListeners();
		h = new HangmanCanvas();
		//h.reset();
		add(h);
	}
	
	public void MouseClicked() {
		//h.reset();
	}
	
	private HangmanCanvas h;
}
