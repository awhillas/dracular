package dracula.impl;

import dracula.impl.map.Location;

public class Hunter {
	
	// Core state.
	private String name;
	private Game game;
	private int number;
	
	// Turn-based state.
	private Location location;
	private int health;	
	private boolean isInHospital;
	public String encounters;
	
	public Hunter(Game game, String name, int num, Location loc) {
		this.location = loc;
		this.game = game;
		this.name = name;
		this.number = num;
		this.health = 9;
	}

	public void update(String newMove) throws Exception {
		String name = newMove.substring(0, 1);
		if (!name.contains(this.name))
			throw new Exception(String.format("Trying to update move data %s for the wrong hunter %s", newMove, this.name));
		
		String newLocation = newMove.substring(1, 3);
		
//		if (!location.getName().contains(newLocation))
//			throw new Exception(String.format("Trying to update move data %s for the same location %s", newMove, this.location));
//		
//		// Location.
		updateLocation(newLocation);
		
		// Encounters.
		String encounterString = newMove.substring(3);
		
		updateEncounters(encounterString);
	}
	
	private void updateLocation(String newLocation) {
		/* TODO
		 * If the Hunter is in the same city or sea they were in last turn 
		 * he/she gains 3 life points (subject to a maximum of 9 points) 
		 * (ie they are resting/researching/recovering from combat)
		 */
		if (this.location.getName() == newLocation) {
			this.setHealth(3);
		} else {
			this.location = new Location(newLocation);
		}
		// Hospital
		if (this.location == game.getMap().getHospital()) {
			isInHospital = true;
			setHealth(9);
		} else {
			isInHospital = false;
		}
	}
	
	private void updateEncounters(String encounters) {
		
		// Encountered Dracula.
		if (encounters.substring(0).contains("D")) {
			game.onHunterEncounter(this, game.HE_ENCOUNTERD_DRACULA);
		}
		
		// Vampire is discovered
		if (encounters.substring(0).contains("V")) {
			this.location.removeVampire();
			game.onHunterEncounter(this, game.HE_ENCOUNTERD_VAMPIRE);
		}
		
		// Traps are in sequence or a single trap
		if (encounters.substring(0).contains("TT")) {
			this.location.removeTrap();
			game.onHunterEncounter(this, game.HE_ENCOUNTERD_TRAP);
			this.location.removeTrap();
			game.onHunterEncounter(this, game.HE_ENCOUNTERD_TRAP);
		} 
		else if (encounters.substring(0).contains("T")) {
			this.location.removeTrap();
			game.onHunterEncounter(this, game.HE_ENCOUNTERD_TRAP); 
		}
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String getHunter() {
		return this.name;
	}
	
	public int getHealth() {
		return this.health;
	}
 	
	/* 
	 * Hunter is automatically teleported to hospital if it drops below 1
	 * Otherwise it is limited to 9
	 */
	public void setHealth(int amount) {
		this.health += amount;
		if (this.health > 9 || this.health < 1) 
			this.health = 9;
	}
}
