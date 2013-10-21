package dracula;
/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public class Move {
	GameData Gamedata;
	String moveTo;
	String setVampire;
	String setTrap;
	String moveString;
	String action; 
	
	public Move(GameData data) {
		this.Gamedata = data;
		this.setVampire = ".";
		this.setTrap = ".";
		this.action = ".";
	}
	
	public void searchMove() {
		
	}
	
	public void doMove() {
		
	}
	
	public void doAction() {
		if (Gamedata.round % 13 == 0 && Gamedata.vampire == "") {
			this.action = "V";
			Gamedata.vampire = "";
			Gamedata.MapData.get(Gamedata.dracula.location).vampire = false;
		} else {
			// TODO cancel out last trap from map
			this.action = "M";
		}
	}
	
	public void setVampire() {
		if (Gamedata.round % 13 == 0) {
			Gamedata.vampire = "";
			Gamedata.MapData.get(Gamedata.dracula.location).vampire = true;
		}
	}
	
	public void setTrap() {
		Gamedata.MapData.get(Gamedata.dracula.location).setTrap();
	}
	
	public void doubleBackMove() {
		if (Gamedata.dracula.doubleBackLast == 0) {
			Gamedata.dracula.doubleBack = true;
			Gamedata.dracula.doubleBackLast = 6;
		}
	}
	
	public void hideMove() {
		if (Gamedata.dracula.hiddenLast == 0) {
			Gamedata.dracula.hidden = true;
			Gamedata.dracula.hiddenLast = 6;
		}
	}

	public void getString() {
		moveString += "D";
		moveString += moveTo;
		moveString += setVampire;
		moveString += setTrap;
		moveString += action;
		moveString += ".";
	}
}
