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
		
	public static Dracula getDracula(String pastPlays, String[] messages) {
		return new Game(pastPlays);
	}
}