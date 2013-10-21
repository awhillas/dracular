package dracula;
/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public class LocationData {
	String location;
	String type;
	public boolean vampire;
	public int traps;
	
	public LocationData(String location, String type) {
		this.location = location;
		this.type = type; //Sea, Hospital, Town
	}
	
	public boolean setTrap() {
		if (traps < 3){
			this.traps++;
			return true;
		}
		return false;
	}
	
	public boolean removeTrap() {
		if (traps > 0) {
			this.traps--;
			return true;
		}
		return false;
	}
}
