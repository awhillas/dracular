package dracula;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

/**
 * Main class that interfaces to our implementation of the Dracular AI
 * Written for COMP9024 2013s2.
 */

/**
 * Bad name but all the good ones were taken :(
 * @author arbw870
 */
public class Gamer implements Dracula {

	private GameData world;
	
	private GameMap map;
	
	/**
	 * Construct the state of play so we can choose a next action
	 */
	public Gamer(String pastPlays, String[] messages) {
		
		// Parse the past plays and build a model of the state of the world
		world = new GameData();
		world.doString(pastPlays);
		map = new GameMap();
	}

	/**
	 * This is where the AI comes in.
	 * 
	 * "Clarification: The getPlayAsString method in Dracula Move should return 
	 * a string of length 2, being the location that Dracula wants to move to."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula
	 * 
	 * @see dracula.Dracula#decideMove()
	 */
	@Override
	public DraculaMove decideMove() {
		String currentCity = world.dracula.location;
		List<String> options = map.getAdjacentFor(currentCity, EnumSet.of(TravelBy.road).of(TravelBy.sea));
		//List<String> nonOptions = 
		
		// Replace this with something more intelligent.
		// Perhaps start by eliminating cites with Hunters
		// if all have hunters can we hide or backtrack?
		// If all roads blocked take the sea?
		// If we have to move to a city with a hunter then choose the weakest one.
		// If all the same choose route back to Castle.
		// etc...
		// Else if no hunters choose square farthest away from hunters.
		
		if (canHide()) {
			options.add("HI");
		}
		if (this.canDoubleBack()) {
			options.add("D1");
			options.add("D2");
			options.add("D3");
			options.add("D4");
			options.add("D5");
		}
		if (this.canTeleport()) {
			options.add("TP");
		}
		
		Random random = new Random();
		int next = random.nextInt(options.size());
		
		return new GamerMove(options.get(next));
	}
	
	/**
	 * "Dracula may make a HIDE move and remain in the city he is currently in for a 
	 * second turn.   He cannot HIDE at sea. Dracula can only have one HIDE move 
	 * in his trail at any time."
	 * 
	 * "Hide moves can refer to Double back moves or cities."
	 * 
	 * @return	true if Dracula can make a HIDE move, false otherwise.
	 */
	private boolean canHide() {
		return false;
	}
	
	/**
	 * "Dracula may make a DOUBLE_BACK move and revisit one city or sea in his trail, 
	 * however whenever he makes a DOUBLE_BACK move the Hunters know he has done 
	 * so. Dracula can only have one DOUBLE_BACK move in his trail at any time."
	 * 
	 * "Double backs can refer to Hide moves, or cities."
	 * 
	 * "D1 if u are doubling back to ur current location, D2 if you are going back one city etc."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula?inCohort=unsw/courses/COMP9024/Cohorts/2013Semester2#comment-525f751a78f2f2083f980853
	 * 
	 * @return	true if can make a double back move, false otherwise.
	 */
	private boolean canDoubleBack() {
		return false;
	}
	
	private boolean canTeleport() {
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Testing the AI...
		
	}

}
