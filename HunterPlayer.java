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
	int traps;
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
		this.location = GameData.move.location;
		this.traps = 0;
		this.draculaEncounter = false;
		setEvents();
	}
	
	public Player copyFromLast(HunterPlayer activeHunter) {
		this.draculaEncounter = activeHunter.draculaEncounter;
		this.location = activeHunter.location;
		this.encounters = activeHunter.encounters;
		this.traps = activeHunter.traps;
		this.health = activeHunter.health;
		this.status = activeHunter.status;
		return this;
	}
	
	@Override
	public void setEvents() {
		//Dracula is encountered
		if (encounters.substring(0).contains("D")) {
			this.draculaEncounter = true;
			this.health -= 4;
			GameData.dracula.health -= 10;
		}
		//Vampire is discovered
		if (encounters.substring(0).contains("V")) {
			GameData.vampire = false;
			//GameData.MapData.get(this.location).vampire = false;
		}
		//Traps are in sequence or a single trap
		if (encounters.substring(0).contains("TT")) { 
			this.traps = 2;
			//Spec says traps are disarmed? Health points?
			//GameData.MapData.get(this.location).removeTrap();
			//GameData.MapData.get(this.location).removeTrap();
		} else if (encounters.substring(0).contains("T")) {
			this.traps = 1;
			//GameData.MapData.get(this.location).removeTrap();
		}
	}

	@Override
	public void setLocation() {
		
	}

	@Override
	public Player copyFromLast(DraculaPlayer activeDracula) {
		// TODO Auto-generated method stub
		return null;
	}

}