package hangmanGame;
import java.util.ArrayList;

import acm.program.Program;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class Animation extends Program {
	
	private HangmanCanvas canvas;
	private boolean playAnimation;
	
	public Animation(HangmanCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void startWinAnimation() {
		RandomGenerator rgen = new RandomGenerator();
		//easier to think of density of snowflakes in terms of rows
		int pixels_per_row = 20;
		int min_num_snowflakes_per_row = 10;
		int max_num_Snowflakes_per_row = 30;
		//each snowflake has its own internal speed
		double min_speed = 0.2;
		double max_speed = 0.7;
		ArrayList<Snowflake> snowflakes = new ArrayList<Snowflake>();
		double min_snowflake_diameter = 2;
		double max_snowflake_diameter = 5;
		//get a random number of snowflakes per row
		int num_snowflakes_this_row = rgen.nextInt(min_num_snowflakes_per_row, max_num_Snowflakes_per_row);
		//make snowflakes one row at a time
		for(int row = 0; row < (canvas.getHeight())/pixels_per_row; row++)
			for(int i = 0; i < num_snowflakes_this_row; i++){
				//get a random diameter for each snowflake
				double snowflake_diameter = rgen.nextDouble(min_snowflake_diameter,max_snowflake_diameter);
				//construct the snowflake with random position within the row and random color and given random diameter
				Snowflake snowflake = new Snowflake(
						//random x coordinate within screen, don't worry about falling off the right end
						rgen.nextInt(0,(int)canvas.getWidth()),
						//snowflakes start above screen row 0 is the y range -pixels_per_row <= y <= 0
						-(row + 1)*pixels_per_row + rgen.nextInt( 0, pixels_per_row),
						snowflake_diameter, snowflake_diameter);
				//also choose a random internal speed for each snowflake
				snowflake.setSpeed(rgen.nextDouble(min_speed, max_speed));
				snowflake.setColor(rgen.nextColor());
				//add to the arraylist
				snowflakes.add(snowflake);
				//add to the screen
				canvas.add(snowflake);
		}
		//another method must turn this switch off to terminate this method
		playAnimation = true;
		while(playAnimation) {
			pause(7);
			//move all the snowflakes
			for (int i = 0; i < snowflakes.size(); i++) {
				snowflakes.get(i).move();
				if (snowflakes.get(i).getY() > canvas.getHeight()) {
					snowflakes.get(i).move(0, -canvas.getHeight() - pixels_per_row);
				}
			}
			
		}
	}

	public void end() {
		playAnimation = false;
	}
}
