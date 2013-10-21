package dracula.testing;
import dracula.GameData;
import dracula.LocationData;


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
		
		//Traps work, hospitals work, Encounters work
		String testString = "GBE.... SBR.... HLO.... MCA.... DSJ.V.. GSJVD.. GNE.... SNET... HGTTT.. MJM.... DKVT... GJM.... DCD....";
		String[] messagesTest;
/*		
		
		LocationData loc1 = new LocationData("GT", "Town");
		LocationData loc2 = new LocationData("NE", "Town");
		Gamedata.MapData.put("GT", loc1);
		Gamedata.MapData.put("NE", loc2);
		Gamedata.MapData.get("GT").traps = 2;
		Gamedata.MapData.get("NE").traps = 1;
		
		//Builds board data from pastPlays string
		Gamedata.doString(testString);
		//Builds current state of Dracula
		//update the game state and player states
		System.out.println("Player: Dracula");
		System.out.println("Health: "+Gamedata.dracula.health);
		System.out.println("Location: "+Gamedata.dracula.location);
		System.out.println("Status: "+Gamedata.dracula.status);
		System.out.println("Events: "+Gamedata.dracula.events);
		
		System.out.println("Actions: "+Gamedata.dracula.action);
		System.out.println("");
		System.out.println("Player: "+Gamedata.HuntersData.get("G").name);
		System.out.println("Health: "+Gamedata.HuntersData.get("G").health);
		System.out.println("Location: "+Gamedata.HuntersData.get("G").location);
		System.out.println("Status: "+Gamedata.HuntersData.get("G").status);
		System.out.println("Encounters: "+Gamedata.HuntersData.get("G").encounters);
		
		System.out.println("");
		System.out.println("Player: "+Gamedata.HuntersData.get("S").name);
		System.out.println("Health: "+Gamedata.HuntersData.get("S").health);
		System.out.println("Location: "+Gamedata.HuntersData.get("S").location);
		System.out.println("Status: "+Gamedata.HuntersData.get("S").status);
		System.out.println("Encounters: "+Gamedata.HuntersData.get("S").encounters);
		
		System.out.println("");
		System.out.println("Player: "+Gamedata.HuntersData.get("H").name);
		System.out.println("Health: "+Gamedata.HuntersData.get("H").health);
		System.out.println("Location: "+Gamedata.HuntersData.get("H").location);
		System.out.println("Status: "+Gamedata.HuntersData.get("H").status);
		System.out.println("Encounters: "+Gamedata.HuntersData.get("H").encounters);
		
		System.out.println("");		
		System.out.println("Player: "+Gamedata.HuntersData.get("M").name);
		System.out.println("Health: "+Gamedata.HuntersData.get("M").health);
		System.out.println("Location: "+Gamedata.HuntersData.get("M").location);
		System.out.println("Status: "+Gamedata.HuntersData.get("M").status);
		System.out.println("Encounters: "+Gamedata.HuntersData.get("M").encounters);
		
		assert Gamedata.dracula.events.length() == 2;
		assert Gamedata.dracula.action.length() == 1;
		assert Gamedata.HuntersData.get("G").encounters.length() == 4;
		assert Gamedata.HuntersData.get("S").encounters.length() == 4;
		assert Gamedata.HuntersData.get("H").encounters.length() == 4;
		assert Gamedata.HuntersData.get("M").encounters.length() == 4;
		
		//All of the data should be input now
		//It is Draculas move, move based on the data
*/
	}
}