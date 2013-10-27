package dracula.impl;

import java.util.Random;

import dracula.*;

/**
 * @author arbw870
 */
public class Move implements DraculaMove {
	/**
	 * Action that Player can make. For a Hunter is always the same as the 
	 * location but for Dracular this can be Double-Back or Hide moves which
	 * we need to transalte to a location for internal use while keeping the
	 * action for public use (i.e. return our move to the game controller).
	 */
	private String action;
	private String location;

	public Move(String action, String location) {
		this.action = action;
		this.location = location;
	}

	/**
	 * Many instances action = location are the same
	 */
	public Move(String location) {
		this.action = location;
		this.location = location;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	/**
	 * Only compare actions not locations.
	 * This is used in DraculaTrail to see if an Move is in the Trail.
	 */
	public boolean equals(Move m) {
		if (m.getPlayAsString() == this.action) {
			return true;
		}
		return false;
	}

	@Override
	public String getPlayAsString() {
		return this.action;
	}

	@Override
	public String getMessage() {
		// The Master quotes from Buffy the Vampire Slayer TV series.
		// @see: http://www.buffyquotes.co.uk/master/
		String[] quotes =  {
			"Tonight, I shall walk the Earth, and the stars themselves will hide!",
			"I am weary, and their deaths will bring me little joy. Of course, sometimes a little is enough.",
			"Yes, yes! Shake, earth! This is a sign. We are in the final days. My time is come! Glory, glory! What do you think? 5.1?" ,
			"Oh, good. The feeble banter portion of the fight",
			"You are not the hunter, you are the lamb",
			"You were destined to die. It was written!",
			"I've lost my appetite for this one. She keeps looking at me. I'm trying to eat and she looks at me!" ,
			"Hunt and kill, hunt and kill. Titillating? Yes. Practical? Hardly. Meanwhile, the humans, with their plebeian minds, have brought us a truly demonic concept. Mass production!",
			"Behold the technical wonder which is about to alter the very fabric of our society. Some have argued that such an advancement goes against our nature. They claim that death is our art. I say to them... well, I don't say anything to them because I kill them."
		};
		Random random = new Random();
		int next = random.nextInt(quotes.length);
		return quotes[next];
	}
}
