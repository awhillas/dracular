package dracula.impl;

import dracula.Encounter;

public class Vampire implements Encounter {

	String location;
	
	public Vampire(String location) {
		this.location = location;
	}

	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public boolean isTrap() {
		return false;
	}
}
