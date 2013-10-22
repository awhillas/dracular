package dracula.impl.map;

public class Location {
	
	private String name;
	private int numOfTraps;
	
	public Location(String name) {
		this.name = name;
		this.numOfTraps = 0;
	}
	
	public void setTrap() {
		if (numOfTraps < 3){
			this.numOfTraps++;
		}
	}
	
	public void removeTrap() {
		if (numOfTraps > 0) {
			this.numOfTraps--;
		}
	}
	
	public String getName() {
		return this.name;
	}
}
