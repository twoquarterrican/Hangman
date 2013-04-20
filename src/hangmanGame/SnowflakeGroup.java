package hangmanGame;

import acm.graphics.GCompound;

public class SnowflakeGroup extends GCompound {
	
	private double speed;
	
	public SnowflakeGroup() {
		super();
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void move() {
		this.move(0, speed);
	}
	
}
