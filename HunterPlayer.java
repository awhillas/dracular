/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public class HunterPlayer implements Player {
	GameData GameData;
	String name;
	String location;
	String encounters;
	boolean draculaEncounter;
	boolean hospital;
	int trapEncounter;
	int health;
	int status;
	
	public HunterPlayer(String name) {
		this.name = name;
		this.health = 9;
		this.status = 1;
	}
	
	@Override
	public void updateFromMove(GameData data) {
		this.GameData = data; 
		this.encounters = GameData.move.events;
		this.trapEncounter = 0;
		this.draculaEncounter = false;
		setLocation();
		setEvents();
	}
	
	public Player copyFromLast(HunterPlayer activeHunter) {
		this.draculaEncounter = activeHunter.draculaEncounter;
		this.location = activeHunter.location;
		this.encounters = activeHunter.encounters;
		this.trapEncounter = activeHunter.trapEncounter;
		this.health = activeHunter.health;
		this.status = activeHunter.status;
		return this;
	}
	
	@Override
	public void setLocation() {
		/*
		 * If the Hunter is in the same city or sea they were in last turn 
		 * he/she gains 3 life points (subject to a maximum of 9 points) 
		 * (ie they are resting/researching/recovering from combat)
		 */
		if (this.location == GameData.move.location) {
			this.setHealth(3);
		} else {
			this.location = GameData.move.location;
		}
		//Hospital
		if (this.location.contains("JM")) {
			hospital = true;
			this.setHealth(9);
		} else {
			hospital = false;
		}
	}
	
	@Override
	public void setEvents() {
		//Dracula is encountered
		if (encounters.substring(0).contains("D")) {
			this.draculaEncounter = true;
			this.setHealth(-4);
			GameData.dracula.setHealth(-10);
		}
		//Vampire is discovered
		if (encounters.substring(0).contains("V")) {
			GameData.vampire = "";
			GameData.MapData.get(this.location).vampire = false;
		}
		//Traps are in sequence or a single trap
		if (encounters.substring(0).contains("TT")) { 
			this.trapEncounter = 2;
			this.setHealth(-4); 
			//
			GameData.MapData.get(this.location).removeTrap();
			GameData.MapData.get(this.location).removeTrap();
		} else if (encounters.substring(0).contains("T")) {
			this.trapEncounter = 1;
			this.setHealth(-2); 
			GameData.MapData.get(this.location).removeTrap();
		}
		//Location is empty, remove it
		if (GameData.MapData.get(this.location) != null) {
			if (GameData.MapData.get(this.location).traps == 0 && !GameData.MapData.get(this.location).vampire) {
				GameData.MapData.remove(this.location);
			}
		}
	}


	@Override
	public Player copyFromLast(DraculaPlayer activeDracula) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setHealth(int amount) {
		this.health += amount;
		if (this.health > 9) {
			this.health = 9;
		} else if (this.health < 1) {
			this.status = 0; //status 0 which will force a move back to hospital
			this.health = 0;
		}
	}
}
