package dracula.impl.testing;

import java.util.HashMap;

import dracula.*;
import dracula.impl.*;
import dracula.impl.map.Location;


public class TestGame {
	private Game game;
	String[] messages = null;
	HashMap<String, Hunter> hunters;
	/*
	 * The MapData has to keep track of the various states:
	 * Hunter positions 
	 * Dracula positions
	 * Traps
	 * Vampires
	 */
	
	/*
	 * The GameData stores all the information on:
	 * Hunter & Dracula moves
	 * Player information
	 * It also stores the Dracula trail (as an an array of 6 moves)
	 */
	
	/*
	 * The DraculaPlayer stores all his current states.
	 * Location
	 * Health
	 * Moves since last double back
	 * Moves since last hide
	 * Moves since last vampire
	 */
	
	/* 
	 * Other things to track
	 * Player health (Player)
	 * - Encounters
	 * - Traps
	 * - Sea travel
	 * - Hospital/Castle
	 * Player Status (Player)
	 * Score (GameData)
	 * Trap life (GameData, GraphMap)
	 * Vampire life (GameData, GraphMap)
	 */
	
	
	public static void main(String[] args) {
		TestGame test = new TestGame();
		test.game = new Game();
		test.hunters = test.game.getHunters();
		
		//Madrid, London, Belgrade, Berlin
		//MA, LO, BE, BR
		test.game.update("GMA....", test.messages);
		test.game.update("SLO....", test.messages);
		test.game.update("HBE....", test.messages);
		test.game.update("MBR....", test.messages);
		test.runTest();
		
	}
	
	public void runTest() {
		while (this.game.getDracula().getHealth() > 0 && this.game.getScore() > 1){
			// TODO get back draculas information for the search move
			/*
			 * 
			 */
			this.game.update(randomHunterMover("G"), this.messages);
			this.game.update(randomHunterMover("S"), this.messages);
			this.game.update(randomHunterMover("H"), this.messages);
			this.game.update(randomHunterMover("M"), this.messages);
		}
	}
	
	public void dummyRounds() {
		String[] testRounds = new String[32];
		testRounds[0]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[1]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[2]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[3]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[4]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[5]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[6]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[7]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[8]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[9]  = "GBE.... SBR.... HLO.... MCA....";
		testRounds[10] = "GBE.... SBR.... HLO.... MCA....";
		testRounds[11] = "GBE.... SBR.... HLO.... MCA....";
		testRounds[12] = "GBE.... SBR.... HLO.... MCA....";
		testRounds[13] = "GBE.... SBR.... HLO.... MCA....";
		testRounds[14] = "GBE.... SBR.... HLO.... MCA....";
		testRounds[15] = "GBE.... SBR.... HLO.... MCA....";
	}
	
	public String randomHunterMover(String name) {
		Hunter hunter = hunters.get(name);
		HunterMove newMove = new HunterMove(hunter, "RANDOM", this.game);
		return newMove.hunterMove();
	}
	
	public String SearchHunterMover(String name) {
		Hunter hunter = hunters.get(name);
		HunterMove newMove = new HunterMove(hunter, "SEARCH", this.game);
		return newMove.hunterMove();
	}
}
