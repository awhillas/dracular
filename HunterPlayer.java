/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */


public class HunterPlayer implements Player {
	GameData GameData;
	String name;
	String location;

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
		setEvents();
		setLocation();
	}
	
	@Override
	public void setEvents() {
		if (GameData.move.events.substring(0).contains("D")) {
			this.health -= 4;
			GameData.dracula.health -= 10;
		}
		if (GameData.move.events.substring(0).contains("V")) {
			GameData.vampire = false;
			GameData.MapData.get(this.location).vampire = false;
		}
		if (GameData.move.events.substring(0).contains("TT")) { //??
			GameData.MapData.get(this.location).removeTrap();
			GameData.MapData.get(this.location).removeTrap();
		} else if (GameData.move.events.substring(0).contains("T")) {
			GameData.MapData.get(this.location).removeTrap();
		}
	}

	@Override
	public void setLocation() {
		this.location = GameData.move.location;
	}

}