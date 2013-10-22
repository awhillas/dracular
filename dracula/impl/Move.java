package dracula.impl;

import dracula.*;

/**
 * @author arbw870
 */
public class Move implements DraculaMove {

	private String newLocation;
	
	public Move(String to) {
		this.newLocation = to;
	}

	@Override
	public String getPlayAsString() {
		return this.newLocation;
	}

	@Override
	public String getMessage() {
		return "Blah blah blah-blaaah!";
	}
}
