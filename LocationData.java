/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public class LocationData {
	String location;
	String type;
	boolean vampire;
	int traps;
	
	public LocationData(String location, String type) {
		this.location = location;
		this.type = type; //Sea, Hospital, Town
	}
	
	public void setTrap() {
		if (traps < 3){
			this.traps++;
		}
	}
	
	public void removeTrap() {
		if (traps > 0) {
			this.traps--;
		}
	}
}
