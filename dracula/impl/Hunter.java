package dracula.impl;

import dracula.impl.Board;
import dracula.impl.map.GameMap;
import dracula.Encounter;
import dracula.Player;

public class Hunter implements Player {
	
	// Core state.
	private String name;
	private int number;
	// Turn-based state.
	private String location;	// Two letter code for location.
	private int health;			
	
	public Hunter(String name, int playerNumber) {
		this.number = playerNumber;
		this.name = name;
		this.health = 9;
	}
	
	/* 
	 * Hunter is automatically teleported to hospital if it drops below 1
	 * Otherwise it is limited to 9
	 */
	@Override
	public void addToHealth(int amount) {
		this.health += amount;
		if (this.health > 9) 
			this.health = 9;
	}
	
	@Override
	public int getHealth() {
		return this.health;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	/**
	 */
	@Override
	public void parsePastPlay(String pastPlay, Board board) {
		String newLocation = pastPlay.substring(1, 3);			// 2 uppercase characters representing the new location of the hunter. 
		// 4 letters representing, in order, the encounters that occurred:
		int traps = countOccurrences(pastPlay.substring(3), 'T');	// one 'T' for each Trap encountered (and disarmed)
		boolean vampire = pastPlay.substring(3).contains("V");	// 'V' if an immature Vampire was encountered (and vanquished)
		boolean dracula = pastPlay.substring(3).contains("D");	// 'D' if, finally, Dracula was confronted
		
		this.makeMove(new Move(newLocation, newLocation), board);
	}
	
	public static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}	
	
	

	/**
	 * Move the hunter to the new location.
	 * 
	 * "Hunter encounters, in sequence, 
	 * 1. all Traps for that city which are in the current trail, then 
	 * 2. any immature Vampires, and then 
	 * 3. at last confronts Dracula himself if he is also in that city, 
	 * each of these events occurring in turn until either the Hunter is 
	 * reduced to 0 or less life points or all the encounters have occurred."
	 * 
	 * @param move		2-letter String of new location.
	 * @param board
	 */
	@Override
	public void makeMove(Move move, Board board) {
		this.location = move.getLocation();
		DraculaTrail trail = board.getDracula().getTrail();
		Encounter[] nasties = trail.getEncountersAt(location);
		
		// Traps, one by one in oldest to newest order
		for (Encounter t : nasties) {
			if(t.isTrap()) {
				this.addToHealth(-Encounter.TRAP_COST);
				trail.disarm(t);
				if (this.getHealth() <= 0) {
					this.location = GameMap.HOSPITAL;
					return;
				}
			}
			// Immature Vampires
			if(!t.isTrap()) {
				trail.disarm(t);
			}
		}
		// Dracula encounter
		if (board.getDracula().getLocation().equals(location)) {
			board.getDracula().addToHealth(-Encounter.HUNTER_COST);
			this.addToHealth(-Encounter.DRACULA_COST);
		}
	}

	@Override
	public String getLocation() {
		return this.location;
	}
		 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases
	}
}
