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
		map = GameMap.getDracularsMap();
	}

	/**
	 * This is where the AI comes in.
	 * @see dracula.Dracula#decideMove()
	 */
	@Override
	public DraculaMove decideMove() {
		String currentCity = world.dracula.location;
		List<String> options = map.getAdjacentFor(currentCity, EnumSet.allOf(TravelBy.class));
		
		// Replace this with something more intelligent.
		// Perhaps start by eliminating cites with Hunters
		// if all have hunters can we hide or backtrack?
		// If all roads blocked take the sea?
		// If we have to move to a city with a hunter then choose the weakest one.
		// If all the same choose route back to Castle.
		// etc...
		// Else if no hunters choose square farthest away from hunters.
		
		Random random = new Random();
		int next = random.nextInt(options.size());
		
		return new GamerMove(options.get(next));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Testing the AI...
		
	}

}
