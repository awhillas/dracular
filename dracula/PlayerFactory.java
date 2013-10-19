/**
 * Written for COMP9024 2013s2 at UNSW Kensington.
 * @author dstacey@cse.unsw.edu.au
 *
 *  EDIT this file
 */

package dracula;

import dracula.Dracula;

public class PlayerFactory {

   public static Dracula getDracula(String pastPlays, String[] messages) {
      return new Game(pastPlays, messages);
   }
   
   public static void main (String[] args) {
	   
	   // testing
	   
	   String pastPlays = "GBE.... SBR.... HLO.... MCA.... DSJ.V.. GSJVD.. GNE....";
	   String[] messages = {};
	   
	   Dracula d = PlayerFactory.getDracula( pastPlays, messages );
	   if (d == null) {
	      throw new UnsupportedOperationException("No Dracula selected.");
	   }
	   DraculaMove result = d.decideMove( );
   }

}
