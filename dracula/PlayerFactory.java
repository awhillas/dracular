/**
 * Written for COMP9024 2013s2 at UNSW Kensington.
 * @author dstacey@cse.unsw.edu.au
 *
 *  EDIT this file
 */
 
package dracula;
 
import dracula.Dracula;
import dracula.impl.*;
 
public class PlayerFactory {
	
	private static Game game;
	
	public static Dracula getDracula(String pastPlays, String[] messages) {
		if (game == null) {
			game = new Game();
		}

		game.update(pastPlays, messages);
		return game.getDracula();
	}
}