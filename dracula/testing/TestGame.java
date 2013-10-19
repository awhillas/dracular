package dracula.testing;

import dracula.GameData;

public class TestGame {
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
		GameData Gamedata = new GameData();
		String test1 = "GBE.... SBR.... HLO.... MCA.... DC?.V..";
		String test2 = "GBE.... SBR.... HLO.... MCA.... DSJ.V.. GSJVD.. GNE....";
		String[] messages1;
		
		//Builds board data from pastPlays string
		Gamedata.doString(test2);
		//Builds current state of Dracula
		//update the game state and player states
		System.out.println("Player: Dracula");
		System.out.println("Health: "+Gamedata.getDracula().getHealth());
		System.out.println("Location: "+Gamedata.getDracula().getLocation());
		System.out.println("Status: "+Gamedata.getDracula().getStatus());
		System.out.println("Events: "+Gamedata.getDracula().getEvents());
		
		System.out.println("Actions: "+Gamedata.getDracula().getAction());
		System.out.println("");
		System.out.println("Player: "+Gamedata.getHuntersData().get("G").name);
		System.out.println("Health: "+Gamedata.getHuntersData().get("G").health);
		System.out.println("Location: "+Gamedata.getHuntersData().get("G").location);
		System.out.println("Status: "+Gamedata.getHuntersData().get("G").status);
		System.out.println("Encounters: "+Gamedata.getHuntersData().get("G").encounters);
		
		System.out.println("");
		System.out.println("Player: "+Gamedata.getHuntersData().get("S").name);
		System.out.println("Health: "+Gamedata.getHuntersData().get("S").health);
		System.out.println("Location: "+Gamedata.getHuntersData().get("S").location);
		System.out.println("Status: "+Gamedata.getHuntersData().get("S").status);
		System.out.println("Encounters: "+Gamedata.getHuntersData().get("S").encounters);
		
		System.out.println("");
		System.out.println("Player: "+Gamedata.getHuntersData().get("H").name);
		System.out.println("Health: "+Gamedata.getHuntersData().get("H").health);
		System.out.println("Location: "+Gamedata.getHuntersData().get("H").location);
		System.out.println("Status: "+Gamedata.getHuntersData().get("H").status);
		System.out.println("Encounters: "+Gamedata.getHuntersData().get("H").encounters);
		
		System.out.println("");		
		System.out.println("Player: "+Gamedata.getHuntersData().get("M").name);
		System.out.println("Health: "+Gamedata.getHuntersData().get("M").health);
		System.out.println("Location: "+Gamedata.getHuntersData().get("M").location);
		System.out.println("Status: "+Gamedata.getHuntersData().get("M").status);
		System.out.println("Encounters: "+Gamedata.getHuntersData().get("M").encounters);
		
		//HunterPlayer hunter5 = (HunterPlayer) Gamedata.MovesData.get(5).player;
		//HunterPlayer hunter6 = (HunterPlayer) Gamedata.MovesData.get(6).player;
		
//		if (hunter5.equals(hunter6)) {
//			System.out.println("Moves matched");
//		}
		
		assert Gamedata.getDracula().getEvents().length() == 2;
		assert Gamedata.getDracula().getAction().length() == 1;
		assert Gamedata.getHuntersData().get("G").encounters.length() == 4;
		assert Gamedata.getHuntersData().get("S").encounters.length() == 4;
		assert Gamedata.getHuntersData().get("H").encounters.length() == 4;
		assert Gamedata.getHuntersData().get("M").encounters.length() == 4;
		
		//All of the data should be input now
		//It is Draculas move, move based on the data

	}
}
