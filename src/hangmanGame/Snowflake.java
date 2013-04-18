package hangmanGame;

import acm.graphics.GOval;

public class Snowflake extends GOval {
	
	public Snowflake(double width, double height) {
		this(0,0,width,height);
	}
	
	public Snowflake(double x, double y, double width, double height) {
		super(x,y,width,height);
		speed = 1;
		this.setFilled(true);
	}
	
	public void move() {
		this.move(0, speed);
	}
	
	public void setSpeed(double s){
		speed = s;
	}
	private double speed;
}
