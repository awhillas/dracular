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
	private int doubleBack;
	private int hidden;
	private String draculaMoveAction;
	private List<Location> trail;
	
	public Dracula (Game game) {
		this.game = game;
 		this.health = 40;
		this.doubleBack = 0;
		this.hidden = 0;
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
		if (this.hidden > 0)
			return false;
		return true;
	}
	
	/**
	 * "Dracula may make a DOUBLE_BACK move and revisit one city or sea in his trail, 
	 * however whenever he makes a DOUBLE_BACK move the Hunters know he has done 
	 * so. Dracula can only have one DOUBLE_BACK move in his trail at any time."
	 * 
	 * "Double backs can refer to Hide moves, or cities."
	 * 
	 * "D1 if u are doubling back to current location, D2 if you are going back one city etc."
	 * @see https://www.openlearning.com/unsw/courses/COMP9024/Pages/TheFuryOfDracula?inCohort=unsw/courses/COMP9024/Cohorts/2013Semester2#comment-525f751a78f2f2083f980853
	 * 
	 * @return	true if can make a double back move, false otherwise.
	 */
	private boolean canDoubleBack() {
		if (this.doubleBack > 0)
			return false;
		return true;
	}
	
	//Needs to be in DraculaMove?
	private boolean canSetVampire() {
		if (game.getRound() > 0 && game.getRound() % 13 == 0)
			return true;
		return false;
	}
	
	private boolean canTeleport() {
		/*
		 * Cannot doubleBack
		 * Cannot move via sea as health < 3
		 * Cannot move to any point on trail
		 * ????
		*/
		if (doubleBack > 0 || hidden > 0 || health < 3)
			return false;
		return true;
	}
	
	public List<Location> getTrail() {
		return this.trail;
	}
	
	public void update(String newMove) {
		
		if (doubleBack > 0)
			doubleBack--;
		if (hidden > 0)
			hidden--;
		
		this.updateLocation();
		this.updateActions();
		this.updateTrail();
	}
	
	public void doubleBackTo(int positionInTrail) {
		if (doubleBack == 0 ) {
			this.doubleBack = 6;
			//this.newAILocation = this.trail.get(positionInTrail);
		}
	}
	
	public void hideMove() {
		if (hidden == 0)
			hidden = 6;
	}
	
	private void updateLocation() {
		//this.location = this.newAILocation decision from AI
		
		//Sea travel
		if (game.getMap().isAtSea(this.location))
			this.setHealth(-2);
		//Teleport to or already in Castle Dracula
		if (this.location.getName().contains("TP") || this.location.getName().contains("CD"))
			this.setHealth(10);
	}
	
	private void updateTrail() {
		this.trail.add(this.location);
		this.trail.remove(0);
	}
		
	private void updateActions() {
		if (this.draculaMoveAction.contains("T")) {
			this.location.setTrap();
		}
		if (this.draculaMoveAction.contains("V")) {
			game.setVampire();
			this.location.setVampire();
		}
		
		/*
		 * I don't think we need to do these as they are tracked by the game?
		 * All we need to do is update the old location with the new location
		 * in the dracula trail
		 * 
		//Dracula's Actions
		if (action.contains("V")) {
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
	
	public Location getLocation() {
		return this.location;
	}
	
	public int getHealth() {
		return this.health;
	}

	public void setHealth(int amount) {
		this.health += amount;
		//Draculas health is permitted to go beyond 40
		if (this.health < 1) {
			this.health = 0; //Game over, man.
		}
	}
}
