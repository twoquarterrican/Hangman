package hangmanGame;
import acm.program.Program;

//extending the class Program lets us use the pause(TIME) function
@SuppressWarnings("serial") public class Animation extends Program {
	
	private static final int NUM_GROUPS = 6;
	private SnowflakeGroup[] groups = new SnowflakeGroup[NUM_GROUPS];
	private HangmanCanvas canvas;
	private boolean playAnimation;
	
	public Animation(HangmanCanvas canvas) {
		this.canvas = canvas;
		createSnowflakes();
	}
		
	public void startAnimation() {
		//another method must turn this flag off to terminate this method
		playAnimation = true;
		while(playAnimation) {
			//pause inherited from Program
			pause(7);
			moveGroups();
		}
	}

	public void stopAnimation() {
		playAnimation = false;
	}
	
	private void createSnowflakes() {
		for (int i = 0; i < NUM_GROUPS; i++) {
			groups[i] = new SnowflakeGroup(canvas.getWidth(),canvas.getHeight());
			addToCanvas(groups[i],i);
		}
	}
	
	private void addToCanvas(SnowflakeGroup group, int i) {
		//even-numbered groups start above screen, odd-numbered groups start on-screen
		i = i%2;
		canvas.add(group, 0, -i*canvas.getHeight());
	}
	
	private boolean isOffScreen(SnowflakeGroup gCompound) {
		return gCompound.getY() > canvas.getHeight();
	}
	
	private void resetIfNeeded(SnowflakeGroup group) {
		if (isOffScreen(group)) {
			canvas.remove(group);
			canvas.add(group,0,-canvas.getHeight());
		}
	}
	
	private void moveGroups() {
		for (int i = 0; i < NUM_GROUPS; i++) {
			groups[i].move();
			resetIfNeeded(groups[i]);
		}
	}

}
