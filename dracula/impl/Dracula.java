package dracula.impl;

import java.util.*;
import dracula.DraculaMove;
import dracula.impl.map.Location;

public class Dracula implements dracula.Dracula {
	
	// Core state.
	private Game game;
	
	// Turn-based state.
	private Location location;
	private int health;
	private int status;
	
	/* TODO  Do we really need so many states??
	public String events;
	public String action;
	public boolean hidden;
	boolean doubleBack;
	boolean castle;
	int doubleBackLast;
	int hiddenLast;
*/

	
	public Dracula () {
		this.status = 1;
		this.health = 40;
//		this.doubleBackLast = 0;
//		this.hiddenLast = 0;
	}
	
	/**
	 * This is where the AI comes in.
	 * 
	 * "Clarification: The getPlayAsString method in Dracula Move should return 
	 * a string of length 2, being the location that Dracula wants to move to."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula
	 * 
	 * @see dracula.Dracula#decideMove()
	 */
	@Override
	public DraculaMove decideMove() {
		
		List<Location> optionLocs = game.getMap().getAdjacentFor(this.location, EnumSet.of(TravelBy.road, TravelBy.sea));
		
		List<String> options = new ArrayList<String>();
		for (Location loc : optionLocs) {
			options.add(loc.getName());
		}
		//List<String> nonOptions = 
		
		// Replace this with something more intelligent.
		// Perhaps start by eliminating cites with Hunters
		// if all have hunters can we hide or backtrack?
		// If all roads blocked take the sea?
		// If we have to move to a city with a hunter then choose the weakest one.
		// If all the same choose route back to Castle.
		// etc...
		// Else if no hunters choose square farthest away from hunters.
		
		if (canHide()) {
			options.add("HI");
		}
		if (this.canDoubleBack()) {
			options.add("D1");
			options.add("D2");
			options.add("D3");
			options.add("D4");
			options.add("D5");
		}
		if (this.canTeleport()) {
			options.add("TP");
		}
		
		Random random = new Random();
		int next = random.nextInt(options.size());
		
		return new Move(options.get(next));
	}
	
	/**
	 * "Dracula may make a HIDE move and remain in the city he is currently in for a 
	 * second turn.   He cannot HIDE at sea. Dracula can only have one HIDE move 
	 * in his trail at any time."
	 * 
	 * "Hide moves can refer to Double back moves or cities."
	 * 
	 * @return	true if Dracula can make a HIDE move, false otherwise.
	 */
	private boolean canHide() {
		return false;
	}
	
	/**
	 * "Dracula may make a DOUBLE_BACK move and revisit one city or sea in his trail, 
	 * however whenever he makes a DOUBLE_BACK move the Hunters know he has done 
	 * so. Dracula can only have one DOUBLE_BACK move in his trail at any time."
	 * 
	 * "Double backs can refer to Hide moves, or cities."
	 * 
	 * "D1 if u are doubling back to ur current location, D2 if you are going back one city etc."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula?inCohort=unsw/courses/COMP9024/Cohorts/2013Semester2#comment-525f751a78f2f2083f980853
	 * 
	 * @return	true if can make a double back move, false otherwise.
	 */
	private boolean canDoubleBack() {
		return false;
	}
	
	private boolean canTeleport() {
		return false;
	}
	
	
	public void update(String newMove) {
		
//		this.events = Gamedata.move.events.substring(0,2);
//		this.action = Gamedata.move.events.substring(2,3);
		
		this.updateLocation();
		this.updateActions();
	}
	
	private void updateLocation() {
		/*
		this.location = Gamedata.move.location;
		//DoubleBack
		// TODO digits
		if (this.location.contains("D")) {
			doubleBackLast = 6;
			doubleBack = true;
		} else {
			doubleBack = false;
		}
		//Hide move
		if (this.location.contains("HI")) {
			hidden = true;
			hiddenLast = 6;
		} else {
			hidden = false;
		}
		//Sea travel
		if (this.location.contains("Sea")) {
			//this.setHealth(-2);
		}
		
		//Teleport
		// TODO health increases
		if (this.location.contains("TP") || this.location.contains("CD")) {
			castle = true;
			this.setHealth(10);
		} 
		
		if (doubleBackLast > 0) {
			doubleBackLast--;
		}
		if (hiddenLast > 0) {
			hiddenLast--;
		}
		*/
	}
		
	private void updateActions() {

		/*
		//Events - setting traps or vampires
		if (events.contains("T")) {
			//Set a trap
			if (Gamedata.MapData.get(this.location) == null) {
				LocationData loc = new LocationData(this.location, "Town"); //Town, Port, Sea, Home
				loc.setTrap();
				Gamedata.MapData.put(this.location, loc);
			} else {
				Gamedata.MapData.get(this.location).setTrap();
			}
		}
		if (events.contains("V")) {
			if (Gamedata.MapData.get(this.location) == null) {
				LocationData loc = new LocationData(this.location, "Town");
				loc.vampire = true;
				Gamedata.MapData.put(this.location, loc);
			} else {
				Gamedata.MapData.get(this.location).vampire = true;
			}
			Gamedata.vampire = this.location;
		}
		
		//Dracula's Actions
		if (action.contains("V")) {
			// TODO score update
			//Get the vampire location and clear it from map data
			if (Gamedata.MapData.get(Gamedata.vampire).traps == 0) {
				Gamedata.MapData.remove(Gamedata.vampire);
			} else {
				Gamedata.MapData.get(Gamedata.vampire).vampire = false;
			}
			//Gamedata.vampire = "";
		}
		if (action.contains("M")) {
			//Get the trap from the queue and clear it from map data - 
			String trap = Gamedata.DraculaTrail[(Gamedata.round-5) % 6].location;
			Gamedata.MapData.get(trap).removeTrap();
		}
		
		*/
	}

	private void setHealth(int amount) {
		this.health += amount;
		//Draculas health is permitted to go beyond 40
		if (this.health < 1) {
			// TODO status
			this.status = 0; //status 0 which will force a move back to hospital
			this.health = 0;
		}
	}
}
