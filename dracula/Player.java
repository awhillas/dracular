package dracula;

import dracula.impl.Board;
import dracula.impl.Move;

public interface Player {
	public void parsePastPlay(String pastPlay, Board board);
	/**
	 * Move and action phase for the player. 
	 * Since these happen one directly after the other there is no advantage in
	 * splitting them into separate functions.
	 */
	public void makeMove(Move move, Board board);
	public void addToHealth(int amount);
	public int getHealth();
	public String getLocation();
}
