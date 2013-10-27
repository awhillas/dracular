/**
 * 
 */
package dracula.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import dracula.Dracula;
import dracula.DraculaMove;
import dracula.impl.map.GameMap;

/**
 * This class is the central class for the Dracular AI.
 * 
 * @author alex
 */
public class Game implements Dracula {
	
	Board board;

	/**
	 * 
	 */
	public Game(String pastPlays) {
		this.board = new Board();
		this.parsePastPlays(pastPlays);
	}
	
	private void parsePastPlays(String pastPlays) {
		String[] moves = pastPlays.split(" ");
		for(String m : moves) {
			board.parsePastPlay(m);
		}
	}

	/**
	 * This is where the AI comes in.
	 * 
	 * "Clarification: The getPlayAsString method in Dracula Move should return 
	 * a string of length 2, being the String that Dracula wants to move to."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula
	 * 
	 * @see dracula.Dracula#decideMove()
	 */
	@Override
	public DraculaMove decideMove() {
		
		List<Move> options = new ArrayList<Move>();
		dracula.impl.Dracula d = board.getDracula();

		List<String> optionLocs = board.getMap().getAdjacentFor(d.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
		for (String loc : optionLocs) {
			options.add(new Move(loc, loc));
		}

		if (d.canHide()) {
			options.add(new Move("HI", d.getLocation()));
		}
		if (d.canDoubleBack()) {
			options.addAll(d.getTrail().getDoubleBackMoves());
		}
		if (board.getDracula().canTeleport()) {
			options.add(new Move("TP", GameMap.CASTLE));
		}
		
		Random random = new Random();
		int next = random.nextInt(options.size());
		
		return options.get(next);	// doesn't matter what location is here.
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases

	}
}
