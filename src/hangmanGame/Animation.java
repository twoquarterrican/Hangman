package hangmanGame;
import java.awt.Color;

import acm.graphics.GOval;
import acm.program.Program;
import acm.util.RandomGenerator;

//extending the class Program lets us use the pause(TIME) function
@SuppressWarnings("serial") public class Animation extends Program {
	
	private static final int ROWS_PER_GROUP = 15;
	private static final int MIN_SNOWFLAKES_PER_ROW = 5;
	private static final int MAX_SNOWFLAKES_PER_ROW = 10;
	private static final double MIN_SPEED = 0.3;
	private static final double MAX_SPEED = 0.9;
	private static final int NUM_GROUPS = 6;
	private	double MIN_SNOWFLAKE_DIAMETER = 2;
	private	double MAX_SNOWFLAKE_DIAMETER = 5;
	private SnowflakeGroup[] groups = new SnowflakeGroup[NUM_GROUPS];
	RandomGenerator rgen = new RandomGenerator();
	
	private HangmanCanvas canvas;
	private boolean playAnimation;
	
	public Animation(HangmanCanvas canvas) {
		this.canvas = canvas;
		createSnowflakes();
	}
	
	private void createSnowflakes() {
		for (int i = 0; i < NUM_GROUPS; i++) {
			groups[i] = new SnowflakeGroup();
			fillGroup(groups[i]);
			addToCanvas(groups[i],i);
		}
	}
	
	private void fillGroup(SnowflakeGroup group) {
		group.setSpeed(rgen.nextDouble(MIN_SPEED, MAX_SPEED));
		fillRows(group);
	}
	
	private void fillRows(SnowflakeGroup group) {
		int rowHeight = canvas.getHeight()/ROWS_PER_GROUP;
		for (int row = 0; row < ROWS_PER_GROUP; row++) {
			fillRow(group, row*rowHeight, (row+1)*rowHeight);
		}
		
	}
	
	private void fillRow(SnowflakeGroup group, int y_top, int y_bottom) {
		int numSnowflakes = rgen.nextInt(MIN_SNOWFLAKES_PER_ROW, MAX_SNOWFLAKES_PER_ROW);
		for (int snowflake = 0; snowflake < numSnowflakes; snowflake++) {
			addSnowflake(group, y_top, y_bottom);
		}
	}
	
	private void addSnowflake(SnowflakeGroup group, int y_top, int y_bottom) {
		int x = rgen.nextInt(0, canvas.getWidth());
		int y = rgen.nextInt(y_top, y_bottom);
		double diameter = rgen.nextDouble(MIN_SNOWFLAKE_DIAMETER, MAX_SNOWFLAKE_DIAMETER);
		GOval snowflake = new GOval(x,y,diameter,diameter);
		Color color = rgen.nextColor();
		snowflake.setColor(color);
		snowflake.setFilled(true);
		group.add(snowflake);
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
	
	public void startWinAnimation() {
		//another method must turn this flag off to terminate this method
		playAnimation = true;
		while(playAnimation) {
			//pause inherited from Program
			pause(7);
			moveGroups();
		}
	}

	public void end() {
		playAnimation = false;
	}
}
