package dracula.impl.testing;

import java.util.EnumSet;
import java.util.HashMap;

import dracula.*;
import dracula.impl.*;
import dracula.impl.map.GameMap;
import dracula.impl.map.Location;
import dracula.impl.map.Map;


public class TestGame {
	private Game game;
	private String[] messages = null;
	private HashMap<String, Hunter> hunters;
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
		test.setupGame();
		//test.emulateGame();
		
	}
	
	public void setupGame() {
		Map m = new GameMap();
		//Madrid, London, Belgrade, Berlin
		//MA, LO, BE, BR
		this.game.update("GMA....", this.messages);
		this.game.update("SLO....", this.messages);
		this.game.update("HBE....", this.messages);
		this.game.update("MBR....", this.messages);
		
		Location loc1 = this.hunters.get("G").getLocation();
		Location loc2 = this.hunters.get("S").getLocation();
		Location loc3 = this.hunters.get("H").getLocation();
		Location loc4 = this.hunters.get("M").getLocation();
		assert loc1.getName().contains("MA");
		assert loc2.getName().contains("LO");
		assert loc3.getName().contains("BE");
		assert loc4.getName().contains("BR");
		
		this.game.update(HunterMover("G", "RANDOM"), this.messages);
		this.game.update(HunterMover("S", "RANDOM"), this.messages);
		
		printHunterStatus();
		printDraculaStatus();
		printGameStatus();
		/*
		this.game.update(HunterMover("H", "SEARCH"), this.messages);
		this.game.update(HunterMover("M", "SEARCH"), this.messages);
		
		assert m.getAdjacentFor(hunters.get("G").getLocation(), EnumSet.of(TravelBy.road)).contains(loc1);
		assert m.getAdjacentFor(hunters.get("S").getLocation(), EnumSet.of(TravelBy.road)).contains(loc2);
		assert m.getAdjacentFor(hunters.get("H").getLocation(), EnumSet.of(TravelBy.road)).contains(loc3);
		assert m.getAdjacentFor(hunters.get("M").getLocation(), EnumSet.of(TravelBy.road)).contains(loc4);

		for (String key : hunters.keySet()) {
			assert hunters.get(key).getHealth() >= 0;
		}
		*/
		
	}
	
	public void emulateGame() {
		while (this.game.getDracula().getHealth() > 0 && this.game.getScore() > 1){
			this.game.update(HunterMover("G", "RANDOM"), this.messages);
			this.game.update(HunterMover("S", "RANDOM"), this.messages);
			this.game.update(HunterMover("H", "SEARCH"), this.messages);
			this.game.update(HunterMover("M", "SEARCH"), this.messages);
			
			DraculaMove move = this.game.getDracula().decideMove();
			move.getPlayAsString();
		}
	}
	
	
	public void printDraculaStatus() {
		System.out.println("-----------------------");
		System.out.println("Dracula Status");
		System.out.println("Location: " + this.game.getDracula().getLocation().getName());
		System.out.println("Health: " + this.game.getDracula().getHealth());
		System.out.println("-----------------------");
		System.out.println("");
	}
	
	public void printHunterStatus() {
		System.out.println("-----------------------");
		System.out.println("Hunter Status");
		for (String key : hunters.keySet()) {			
		    System.out.println("Hunter: " +hunters.get(key).getHunter());
		    System.out.println("Health: " +hunters.get(key).getHealth());
		    System.out.println("Location: " +hunters.get(key).getLocation().getName());
			System.out.println("");
		}
	}
	
	public void printGameStatus() {
		System.out.println("-----------------------");
		System.out.println("Game Status");
		System.out.println("Round: " + this.game.getRound());
		System.out.println("Score: " + this.game.getScore());
		System.out.println("-----------------------");
		System.out.println("");
	}
	
	public void dummyRounds() {
		String[] testRounds = new String[16];
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
	
	/*
	 * Create a basic hunter AI and get its first move based on Random or the
	 * path finder
	 */
	public String HunterMover(String name, String type) {
		Hunter hunter = hunters.get(name);
		HunterMove newMove = new HunterMove(hunter, type, this.game);
		return newMove.hunterMove();
	}

}
