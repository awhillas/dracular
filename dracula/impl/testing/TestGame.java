package dracula.impl.testing;

import java.util.HashMap;

import dracula.*;
import dracula.impl.*;
import dracula.impl.map.GameMap;
import dracula.impl.map.Map;
import dracula.impl.ai.DracMoveSearch;


public class TestGame {
	private Board board;
	private String[] messages = null;
	private HashMap<String, Player> hunters;
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
		test.board = new Board();
		test.hunters = test.board.getHunters();
		test.setupGame();
                
                //run for ten or so rounds
                int i = 0;
		while (i < 10){
                    test.emulateGame();
                    i++;
                }
	}
	
	public void setupGame() {
		Map m = new GameMap();
		
		//Madrid, London, Belgrade, Berlin
		//MA, LO, BE, BR
		this.board.parsePastPlay("GMA....");
		this.board.parsePastPlay("SLO....");
		this.board.parsePastPlay("HBE....");
		this.board.parsePastPlay("MBR....");
		
		String loc1 = this.hunters.get("G").getLocation();
		String loc2 = this.hunters.get("S").getLocation();
		String loc3 = this.hunters.get("H").getLocation();
		String loc4 = this.hunters.get("M").getLocation();
		
		//Setup worked
		assert loc1.contains("MA");
		assert loc2.contains("LO");
		assert loc3.contains("BE");
		assert loc4.contains("BR");
		
		//Print round 0 Setup
		printHunterStatus();
		printDraculaStatus();
		printGameStatus();
		
		//Run an update to setup Dracula in the game world
		//board.getDracula().update();
		
		//Run the first round moves for each hunter using a type of dummy AI
		String player1 = HunterMover("G", "RANDOM");
		String player2 = HunterMover("S", "RANDOM");
		String player3 = HunterMover("H", "SEARCH");
		String player4 = HunterMover("M", "SEARCH");
		
		System.out.println("Turn: " + player1);
		System.out.println("Turn: " + player2);
		System.out.println("Turn: " + player3);
		System.out.println("Turn: " + player4);
		
		this.board.parsePastPlay(player1);
		this.board.parsePastPlay(player2);
		this.board.parsePastPlay(player3);
		this.board.parsePastPlay(player4);
		
		/* 
		 * Checks to see if the hunters executed a legal move.
		 * The previous location that they started from should be in the adjacency 
		 * list for their current location. Trail travel excluded
		 
		assert m.getAdjacentFor(hunters.get("G").getLocation(), EnumSet.of(TravelBy.road)).contains(loc1);
		assert m.getAdjacentFor(hunters.get("S").getLocation(), EnumSet.of(TravelBy.road)).contains(loc2);
		assert m.getAdjacentFor(hunters.get("H").getLocation(), EnumSet.of(TravelBy.road)).contains(loc3);
		assert m.getAdjacentFor(hunters.get("M").getLocation(), EnumSet.of(TravelBy.road)).contains(loc4);
		*/
		
		assert hunters.get("H").getHealth() < 9;
		assert board.getDracula().getHealth() < 40;
		
		for (String key : hunters.keySet()) {
			assert hunters.get(key).getHealth() >= 0;
		}

		//Print end of round 1
		printHunterStatus();
		printDraculaStatus();
		printGameStatus();
		
	}
	
	public void emulateGame() {
		while (this.board.getDracula().getHealth() > 0){
			this.board.parsePastPlay(HunterMover("G", "RANDOM"));
			this.board.parsePastPlay(HunterMover("S", "RANDOM"));
			this.board.parsePastPlay(HunterMover("H", "SEARCH"));
			this.board.parsePastPlay(HunterMover("M", "SEARCH"));
			DraculaMove move = DracMoveSearch.getBestMove(board);
                        this.board.parsePastPlay("D" + move.getPlayAsString());
                        printHunterStatus();
                        printDraculaStatus();
                        printGameStatus();
		}
	}
	
	
	public void printDraculaStatus() {
		System.out.println("-----------------------");
		System.out.println("Dracula Status");
		System.out.println("Location: " + this.board.getDracula().getLocation());
		System.out.println("Health: " + this.board.getDracula().getHealth());
		System.out.println("-----------------------");
		System.out.println("");
	}
	
	public void printHunterStatus() {
		System.out.println("-----------------------");
		System.out.println("Hunter Status");
		for (String key : hunters.keySet()) {			
		    System.out.println("Hunter: " +hunters.get(key).getName());
		    System.out.println("Health: " +hunters.get(key).getHealth());
		    System.out.println("Location: " +hunters.get(key).getLocation());
			System.out.println("");
		}
	}
	
	public void printGameStatus() {
		System.out.println("-----------------------");
		System.out.println("Game Status");
		System.out.println("Round: " + this.board.getRound());
		//System.out.println("Score: " + this.board.getScore());
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
		Player hunter = hunters.get(name);
		HunterMove newMove = new HunterMove(hunter, type, this.board);
		return newMove.hunterMove();
	}

}
