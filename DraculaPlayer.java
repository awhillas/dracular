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
		this.location = Gamedata.move.location;
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
		
	}
		
	@Override
	public void setEvents() {
		if (events.contains("T")) {
			//Gamedata.MapData.get(this.location).setTrap();
		}
		if (events.contains("V")) {
			//Gamedata.MapData.get(this.location).vampire = true;
			Gamedata.vampire = true;
		}
		if (action.contains("V")) {
			Gamedata.vampire = false;
			//Get the vampire location and clear it from map data
		}
		if (action.contains("M")) {
			//Get the trap from the queue and clear it from map data
			String trap = Gamedata.DraculaTrail[(Gamedata.round-5) % 6].location;
			//Gamedata.MapData.get(trap).removeTrap();
		}
		if (doubleBackLast > 0) {
			doubleBackLast--;
		}
		if (hiddenLast > 0) {
			hiddenLast--;
		}
	}

	@Override
	public Player copyFromLast(HunterPlayer activeHunter) {
		// TODO Auto-generated method stub
		return null;
	}	
}
