package dracula.impl;

import dracula.Encounter;
import dracula.Player;
import dracula.impl.map.GameMap;

/**
 * This just represents the Dracula piece on the board and all the data 
 * associated with that.
 * 
 * It is NOT the AI for Dracula and so does not figure out which move to make, 
 * see the Game class for that.
 * 
 * @author alex
 */
public class Dracula implements Player {
	
	// Turn-based state.
	private String location;
	private int blood_points;
	private DraculaTrail trail;
	
	public Dracula () {
 		this.blood_points = 40;
 		this.trail = new DraculaTrail();
 		this.location = "nowhere";
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	/*
     * copy constructor
     */
    public Dracula clone(){
        Dracula clone = new Dracula();
        clone.blood_points = this.blood_points;
        clone.location = this.location;
        clone.trail = this.trail.clone();
        return clone;
    }
    
	/**
	 * "Dracula may make a HIDE move and remain in the city he is currently in for a 
	 * second turn. He cannot HIDE at sea. Dracula can only have one HIDE move 
	 * in his trail at any time."
	 * 
	 * "Hide moves can refer to Double back moves or cities."
	 * 
	 * @return	true if Dracula can make a HIDE move, false otherwise.
	 */
	public boolean canHide() {
		return !trail.hasHidden();
	}
	
	/**
	 * "Dracula may make a DOUBLE_BACK move and revisit one city or sea in his trail, 
	 * however whenever he makes a DOUBLE_BACK move the Hunters know he has done 
	 * so. Dracula can only have one DOUBLE_BACK move in his trail at any time."
	 * 
	 * "Double backs can refer to Hide moves, or cities."
	 * 
	 * "D1 if u are doubling back to current location, D2 if you are going back one city etc."
	 * 
	 * @return	true if can make a double back move, false otherwise.
	 */
	public boolean canDoubleBack() {
		return !trail.hasDoubledBack();
	}
	
	public boolean canTeleport() {
		/*
		 * Cannot doubleBack
		 * Cannot move via sea as health < 3
		 * Cannot move to any point on trail
		 * TODO: the sea test is fish here :-/
		*/
		if (!canDoubleBack() && !canHide() && blood_points < 3)
			return false;
		return true;
	}
	
	public DraculaTrail getTrail() {
		return this.trail;
	}

	@Override
	public void addToHealth(int amount) {
		this.blood_points += amount;
		//Dracula's health is permitted to go beyond 40
		if (this.blood_points < 1) {
			this.blood_points = 0; //Game over, man.
		}
	}

	/**
	 * "For each Dracula play (starting with D), the player character is
	 * immediately followed by 6 characters."
	 */
	@Override
	public void parsePastPlay(String pastPlay, Board board) {
		
		// "2 uppercase characters representing the new location of Dracula"
		String action = pastPlay.substring(1, 3);
		
		// now have to calculate where this action will leave us...
		Move move = null;
		if (action.equals("HI")) {
			move = new Move(action, location);
		} else if(action.matches("D[1-5]")) {
			int back = Integer.parseInt(action.substring(1));
			move = trail.getMoveAt(back);
		} else if(action.equals("TP")) {
			move = new Move(action, GameMap.CASTLE);
		} else {
			// assume its a city name
			move = new Move(action, action);
		}
		
		makeMove(move, board);
	}

	/**
	 * Translate a move into a location on the map.
	 * "Dracula leaves a Trap/Vampire as soon as he enters a city, NOT when he 
	 * leaves the city."
	 */
	@Override
	public void makeMove(Move move, Board board) {
		this.trail.addMove(move, this.getNewNasty(board));
		this.location = move.getLocation();
		if (this.location.contains("CD") || this.location.contains("TP"))
			this.addToHealth(10);
	}

	/**
	 * "Dracula places an immature Vampire if he is in a city AND if the round 
	 * whose number is divisible by 13"
	 * 
	 * @param 	board current board state.
	 * @return	a new Encounter i.e. Trap or Vampire
	 */
	private Encounter getNewNasty(Board board) {
		if (board.getMap().isCity(this.getLocation()) && board.getRound() % 13 == 0) {
			return new Vampire(this.location);
		} else {
			return new Trap(this.location);
		}
	}
	
	@Override
	public int getHealth() {
		return this.blood_points;
	}

	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public String getName() {
		return "D";
	}

	@Override
	public int getNumber() {
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases
	}
}
