package hangmanGame;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;

public class BodyParts extends GCompound {
	
	public BodyParts() {
		super();
		initializeParts();
	}
	
	/* head, body, left arm, right arm, left leg, right leg, left foot, right foot*/
	public static final int NUM_BODY_PARTS = 8;
	
	/* Constants for the simple version of the picture (in pixels) */ 
	private static final int SCAFFOLD_HEIGHT = 360; 
	private static final int BEAM_LENGTH = 144; 
	private static final int ROPE_LENGTH = 18; 
	private static final int HEAD_RADIUS = 36; 
	private static final int BODY_LENGTH = 144; 
	private static final int ARM_OFFSET_FROM_HEAD = 28; 
	private static final int UPPER_ARM_LENGTH = 72; 
	private static final int LOWER_ARM_LENGTH = 44; 
	private static final int HIP_WIDTH = 36; 
	private static final int LEG_LENGTH = 108; 
	private static final int FOOT_LENGTH = 28;

	private GCompound[] hangmanPics;
	
	private void initializeParts() {
		//right arm is hangmans right arm assuming we are looking at his front side
		//holds scaffold and all body parts
		hangmanPics = new GCompound[NUM_BODY_PARTS + 1];
		//give upper left corner of each GCompound as arg
		hangmanPics[0] = constructScaffold();
		double tmpy = 0;
		hangmanPics[0].setLocation(0,tmpy);
		hangmanPics[1] = constructHead();
		tmpy += ROPE_LENGTH;
		hangmanPics[1].setLocation(BEAM_LENGTH - HEAD_RADIUS, tmpy);
		hangmanPics[2] = constructBody();
		tmpy += 2*HEAD_RADIUS;
		hangmanPics[2].setLocation(BEAM_LENGTH, tmpy);
		hangmanPics[3] = constructLeftArm();
		tmpy += ARM_OFFSET_FROM_HEAD;
		hangmanPics[3].setLocation(BEAM_LENGTH, tmpy);
		hangmanPics[4] = constructRightArm();
		hangmanPics[4].setLocation(BEAM_LENGTH - UPPER_ARM_LENGTH, tmpy);
		hangmanPics[5] = constructLeftLeg();
		tmpy += -ARM_OFFSET_FROM_HEAD + BODY_LENGTH;
		hangmanPics[5].setLocation(BEAM_LENGTH, tmpy);
		hangmanPics[6] = constructRightLeg();
		hangmanPics[6].setLocation(BEAM_LENGTH - HIP_WIDTH, tmpy);
		//left foot if hangman is facing us
		hangmanPics[7] = constructFoot();
		tmpy += LEG_LENGTH;
		hangmanPics[7].setLocation(BEAM_LENGTH + HIP_WIDTH, tmpy);
		//right foot if hangman is facing us
		hangmanPics[8] = constructFoot();
		hangmanPics[8].setLocation(BEAM_LENGTH - HIP_WIDTH - FOOT_LENGTH, tmpy);
		for (int i = 0; i < hangmanPics.length; i++) {
			//always show the scaffold
			if (i == 0) {
				hangmanPics[i].setVisible(true);
			} else {
				hangmanPics[i].setVisible(false);
			}
			add(hangmanPics[i]);
		}
	}
	
	/** 
	 * Updates the display to correspond to an incorrect guess by the 
	 * user.  Calling this method causes the next body part to appear 
	 * on the scaffold and adds the letter to the list of incorrect 
	 * guesses that appears at the bottom of the window. 
	 */ 
	public void noteIncorrectGuess(int numGuessesRemaining) {
		int numWrongGuesses = NUM_BODY_PARTS - numGuessesRemaining;
		//iterate over all but scaffold
		for (int i = 1; i <= NUM_BODY_PARTS; i++) {
			//if there are 0 wrong Guesses, make sure only scaffold is showing,
			//if there is 1 wrong guess, make sure only scaffold and head are showing, etc.
			if (i <= numWrongGuesses) {
				hangmanPics[i].setVisible(true);
			} else {
				hangmanPics[i].setVisible(false);
			}
		}
	} 

	private GCompound constructFoot() {
		GPoint[] p = new GPoint[2];
		p[0] = new GPoint(0,0);
		p[1] = new GPoint(FOOT_LENGTH, 0);
		return drawLines(p);
		
	}

	private GCompound constructRightLeg() {
		GPoint[] p = new GPoint[3];
		p[0] = new GPoint(0, LEG_LENGTH);
		p[1] = new GPoint(0, 0);
		p[2] = new GPoint(HIP_WIDTH, 0);
		return drawLines(p);
	}

	private GCompound constructLeftLeg() {
		GPoint[] p = new GPoint[3];
		p[0] = new GPoint(0, 0);
		p[1] = new GPoint(HIP_WIDTH, 0);
		p[2] = new GPoint(HIP_WIDTH, LEG_LENGTH);
		return drawLines(p);
	}

	private GCompound constructRightArm() {
		GPoint p1 = new GPoint(0, LOWER_ARM_LENGTH);
		GPoint p2 = new GPoint(0, 0);
		GPoint p3 = new GPoint(UPPER_ARM_LENGTH, 0);
		return drawLines(new GPoint[] {p1, p2, p3});
	}

	private GCompound constructLeftArm() {
		GPoint p1 = new GPoint(0,0);
		GPoint p2 = new GPoint(UPPER_ARM_LENGTH, 0);
		GPoint p3 = new GPoint(UPPER_ARM_LENGTH, LOWER_ARM_LENGTH);
		return drawLines(new GPoint[] {p1, p2, p3});
	}

	private GCompound constructBody() {
		GCompound hp = new GCompound();
		hp.add(new GLine(0, 0, 0, BODY_LENGTH));
		return hp;
	}

	private GCompound constructHead() {
		GCompound hp = new GCompound();
		hp.add(new GOval(0,0, 2*HEAD_RADIUS, 2*HEAD_RADIUS));
		return hp;
	}

	private GCompound constructScaffold() {
		// given upper left corner of scaffold
		GPoint p1 = new GPoint(0, SCAFFOLD_HEIGHT);
		GPoint p2 = new GPoint(0, 0);
		GPoint p3 = new GPoint(BEAM_LENGTH, 0);
		GPoint p4 = new GPoint(BEAM_LENGTH, ROPE_LENGTH);
		return drawLines(new GPoint[] {p1, p2, p3, p4});
	}
	
	private GCompound drawLines( GPoint[] points ) {
		GCompound hp = new GCompound();
		if (points.length < 2 ) {
			return null;
		}
		for (int i = 0; i < points.length - 1; i++) {
			hp.add(new GLine(
					points[i].getX(), points[i].getY(),
					points[i+1].getX(), points[i+1].getY()));
		}
		return hp;
	}

	

}
