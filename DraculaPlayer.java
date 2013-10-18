/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */


public class DraculaPlayer implements Player {
	GameData Gamedata;
	
	String location;
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
		this.
		/**
		 * Written for COMP9024 2013s2.
		 * 
		 * @author adwi001
		 *
		 */hiddenLast = 0;
	}
	
	@Override
	public void updateFromMove(GameData data) {
		Gamedata = data;
		setLocation();
		setEvents();
	}
	
	@Override
	public void setLocation() {
		this.location = Gamedata.move.location;
	}
		
	@Override
	public void setEvents() {
		if (Gamedata.move.events.substring(0).contains("T")) {
			Gamedata.MapData.get(this.location).setTrap();
		}
		if (Gamedata.move.events.substring(0).contains("V")) {
			Gamedata.MapData.get(this.location).vampire = true;
			Gamedata.vampire = true;
		}
		if (doubleBackLast > 0) {
			doubleBackLast--;
		}
		if (hiddenLast > 0) {
			hiddenLast--;
		}
	}	
}
