/**
 * 
 */
package dracula.impl;

import dracula.Encounter;

/**
 * "Traps are slightly harmful things like wolves or mysterious sugar bowls or 
 * sinister pot plants (sometimes traps are called "Minions")."
 * 
 * @author alex
 */
public class Trap implements Encounter {

	String location;
	
	public Trap(String location) {
		this.location = location;
	}

	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public boolean isTrap() {
		return true;
	}
}
