package dracula;
/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */


public class DraculaPlayer implements Player {
	GameData Gamedata;
	
	String location;
	String events;
	String action;
	 
	boolean hidden;
	boolean doubleBack;
	boolean castle;
	int doubleBackLast;
	int hiddenLast;
	int health;
	int status;
	
	public DraculaPlayer () {
		this.status = 1;
		this.health = 40;
		this.doubleBackLast = 0;
		this.hiddenLast = 0;
	}
	
	@Override
	public void updateFromMove(GameData data) {
		this.Gamedata = data;
		this.events = Gamedata.move.events.substring(0,2);
		this.action = Gamedata.move.events.substring(2,3);
		this.setLocation();
		this.setEvents();
	}
	
	@Override
	public Player copyFromLast(DraculaPlayer activeDracula) {
		this.location = activeDracula.location;
		this.events = activeDracula.events;
		this.action = activeDracula.action;
		this.hidden = activeDracula.hidden;
		this.doubleBack = activeDracula.doubleBack;
		this.doubleBackLast = activeDracula.doubleBackLast;
		this.hiddenLast = activeDracula.hiddenLast;
		this.health = activeDracula.health;
		this.status = activeDracula.status;
		return this;
	}
	
	@Override
	public void setLocation() {
		this.location = Gamedata.move.location;
		//DoubleBack
		if (this.location.contains("S")) {
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
		//Teleport
		if (this.location.contains("TP") || this.location.contains("CD")) {
			castle = true;
		} else {
			castle = false;
		}
		
		if (doubleBackLast > 0) {
			doubleBackLast--;
		}
		if (hiddenLast > 0) {
			hiddenLast--;
		}
	}
		
	@Override
	public void setEvents() {

		//Events - setting traps or vampires
		if (events.contains("T")) {
			//Set a trap
			if (Gamedata.MapData.get(this.location) == null) {
				LocationData loc = new LocationData(this.location, "LAND");
				loc.setTrap();
				Gamedata.MapData.put(this.location, loc);
			} else {
				Gamedata.MapData.get(this.location).setTrap();
			}
		}
		if (events.contains("V")) {
			if (Gamedata.MapData.get(this.location) == null) {
				LocationData loc = new LocationData(this.location, "LAND");
				loc.vampire = true;
				Gamedata.MapData.put(this.location, loc);
			} else {
				Gamedata.MapData.get(this.location).vampire = true;
			}
			Gamedata.vampire = this.location;
		}
		
		//Dracula's Actions
		if (action.contains("V")) {
			//Get the vampire location and clear it from map data
			if (Gamedata.MapData.get(Gamedata.vampire).traps == 0) {
				Gamedata.MapData.remove(Gamedata.vampire);
			} else {
				Gamedata.MapData.get(Gamedata.vampire).vampire = false;
			}
			Gamedata.vampire = "";
		}
		if (action.contains("M")) {
			//Get the trap from the queue and clear it from map data - TEST 
			String trap = Gamedata.DraculaTrail[(Gamedata.round-5) % 6].location;
			Gamedata.MapData.get(trap).removeTrap();
		}
	}

	@Override
	public Player copyFromLast(HunterPlayer activeHunter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHealth(int amount) {
		this.health += amount;
		if (this.health > 40) {
			this.health = 40;
		} else if (this.health < 1) {
			this.status = 0; //status 0 which will force a move back to hospital
			this.health = 0;
		}
	}	
}

