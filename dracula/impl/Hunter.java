package dracula.impl;

import dracula.impl.map.Location;

public class Hunter {
	
	// Core state.
	private String name;
	private Game game;

	
	// Turn-based state.
	private Location location;
	private int health;	
	private int status;
	private boolean isInHospital;
	public String encounters;
	private int encounteredTrap;
	private boolean encounteredDracula;
	
	public Hunter(Game game, String name) {
		this.game = game;
		this.name = name;
		
		this.health = 9;
		this.status = 1;
	}

	public void update(String newMove) throws Exception {
		String name = newMove.substring(0, 1);
		if (!name.equals(this.name))
			throw new Exception(String.format("Trying to update move data %s for the wrong hunter %s", newMove, this.name));
		
		String newLocation = newMove.substring(1, 2);
		if (this.location.getName().equals(newLocation))
			throw new Exception(String.format("Trying to update move data %s for the same location %s", newMove, this.location));
		
		// Location.
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
		 
		if (this.location == GameData.move.location) {
			this.setHealth(3);
		} else {
			this.location = GameData.move.location;
		}
		*/
		
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
