package dracula.impl.map;

public class Location {
	
	private String name;
	private boolean vampire;
	private int numOfTraps;
	
	public Location(String name) {
		this.name = name;
		this.numOfTraps = 0;
	}
	
	public void setTrap() {
		if (numOfTraps < 2){
			this.numOfTraps++;
		}
	}
	
	public void removeTrap() {
		if (numOfTraps > 0) {
			this.numOfTraps--;
		}
	}
	
	public String getLocationEncounters() {
		String actions = "";
		if (this.numOfTraps == 1) {
			actions = "T.";
		} else if (this.numOfTraps == 2) {
			actions = "TT";
		}
		if (vampire) {
			actions += "V";
		} else {
			actions += ".";
		}
		return actions;
	}
	
	public void setVampire(){
		this.vampire = true;
	}
	
	public void removeVampire() {
		this.vampire = false;
	}
	
	public String getName() {
		return this.name;
	}
}
