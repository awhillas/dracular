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
import dracula.impl.ai.DracMoveSearch;
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
		/*
		Move[] options = board.getLegalMoves();
		
		Random random = new Random();
		int next = random.nextInt(options.length);
		
		return options[next];	// doesn't matter what location is here.
		*/
		return DracMoveSearch.getBestMove(board);
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases
	}
}
