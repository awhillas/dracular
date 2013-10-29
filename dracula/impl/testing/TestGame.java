package dracula.impl.testing;

import java.util.Arrays;
import java.util.HashMap;

import dracula.*;
import dracula.impl.*;
import dracula.impl.map.GameMap;
import dracula.impl.map.Map;
import dracula.impl.ai.DracCirclingAI;
import dracula.impl.ai.DracMoveSearch;


public class TestGame {
	private Board board;
	private String[] messages = null;
	private HashMap<String, Player> hunters;
	private int gameScore;
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
		//test.dummyTurn();
                
                //run for ten or so rounds
        int i = 0;
		while (i < 10){
              test.emulateGame();
              i++;
       }
	}
	
	public void setupGame() {
		
		this.gameScore =  366;
		
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
		for (String key : hunters.keySet()) {
			assert hunters.get(key).getHealth() >= 0;
		}
		
		System.out.println("Setup OK..");
		System.out.println("Start: GMA... SLO... HBE... MBR...");
	}
	
	public void dummyTurn() {
		
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
		
		System.out.println("Turn: " + player1 + " " + player2 + " " + player3 + " " + player4);
		
		this.board.parsePastPlay(player1);
		this.board.parsePastPlay(player2);
		this.board.parsePastPlay(player3);
		this.board.parsePastPlay(player4);

		//Print end of round 1
		printHunterStatus();
		printDraculaStatus();
		printGameStatus();
	}
	
	public void emulateGame() {
		while (this.board.getDracula().getHealth() > 0 && this.gameScore > 0){
			System.out.println("Round: " + this.board.getRound() + 
								" HunterHealth: " +Arrays.toString(board.getHunterHealth())+
								" DraculaHealth: " + board.getDracHealth() +
								" Score: " +gameScore);
			
			String player1 = HunterMover("G", "RANDOM");
			board.parsePastPlay(player1);
			System.out.print("Turn: " + player1);
			
			String player2 = HunterMover("S", "RANDOM");
			board.parsePastPlay(player2);
			System.out.print(" " + player2);
			
			String player3 = HunterMover("H", "RANDOM");
			board.parsePastPlay(player3);
			System.out.print(" " + player3);
			
			String player4 = HunterMover("M", "SEARCH");
			board.parsePastPlay(player4);
			System.out.print(" " + player4);

			DraculaMove move = DracMoveSearch.getBestMove(board);
			String player5 = "D" + move.getPlayAsString();
            board.parsePastPlay(player5);
            System.out.print(" " + player5);
            System.out.println("");
            scoring(player5);
		}
		System.out.println("GAME OVER!");
		System.out.println("Round: " + this.board.getRound() + 
				" HunterHealth: " +Arrays.toString(board.getHunterHealth())+
				" DraculaHealth: " + board.getDracHealth() +
				" Score: " +gameScore);
		System.exit(0);
	}
	
	public void scoring(String play) {
		this.gameScore--;
		int[] hunterHealth = board.getHunterHealth();
		for (int health : hunterHealth) {
			if (health < 1) {
				gameScore -= 6;
			}
		}
		if (play.substring(3).contains("V")) {
			gameScore -= 13;
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
