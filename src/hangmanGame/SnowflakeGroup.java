package hangmanGame;

import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.util.RandomGenerator;

public class SnowflakeGroup extends GCompound {
	
	private double speed;
	private int width;
	private int height;
	
	private static RandomGenerator rgen = new RandomGenerator();
	
	private static final int N_ROWS = 15;
	private static final int MIN_SNOWFLAKES_PER_ROW = 5;
	private static final int MAX_SNOWFLAKES_PER_ROW = 10;
	private static final double MIN_SPEED = 0.3;
	private static final double MAX_SPEED = 0.9;
	private	double MIN_SNOWFLAKE_DIAMETER = 2;
	private	double MAX_SNOWFLAKE_DIAMETER = 5;
	
	public SnowflakeGroup(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		fillWithSnowflakes();
	}
			
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void move() {
		this.move(0, speed);
	}
	
	private void fillWithSnowflakes() {
		speed = rgen.nextDouble(MIN_SPEED, MAX_SPEED);
		fillRows();
	}
	
	private void fillRows() {
		int rowHeight = height/N_ROWS;
		for (int row = 0; row < N_ROWS; row++) {
			fillRowBoundedBy(row*rowHeight, (row+1)*rowHeight);
		}
		
	}
	
	private void fillRowBoundedBy(int y_small, int y_large) {
		int numSnowflakes = rgen.nextInt(MIN_SNOWFLAKES_PER_ROW, MAX_SNOWFLAKES_PER_ROW);
		for (int snowflake = 0; snowflake < numSnowflakes; snowflake++) {
			addRandomSnowflakeInRowBoundedBy(y_small, y_large);
		}
	}
	
	private void addRandomSnowflakeInRowBoundedBy(int y_small, int y_large) {
		int x = rgen.nextInt(0, width);
		int y = rgen.nextInt(y_small, y_large);
		double diameter = rgen.nextDouble(MIN_SNOWFLAKE_DIAMETER, MAX_SNOWFLAKE_DIAMETER);
		GOval snowflake = new GOval(x,y,diameter,diameter);
		Color color = rgen.nextColor();
		snowflake.setColor(color);
		snowflake.setFilled(true);
		add(snowflake);
	}

}
