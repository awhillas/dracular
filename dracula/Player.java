package dracula;
/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public interface Player {
	
	/*
	 * Runs the update of player and move information from the play
	 */
	public void updateFromMove(GameData data);
	
	/*
	 * Checks the encounters and events of the player and updates the health
	 * status of player. This is effected by Dracula/Hunter encounters, traps
	 * and Hospital/Castle
	 */
	public void setHealth(int amount);
	
	/*
	 * Update the location of the player
	 */
	public void setLocation();
	
	/*
	 * Is the player dead, alive, in hospital/castle dracula or hidden
	 * Dead = 0;
	 * Alive = 1;
	 * Hospital/Castle = 2;
	 * Hidden = 3;
	 */
	//public void setStatus();
	
	/* 
	 * Updates the encounter information for both Hunters & Dracula
	 */
	public void setEvents();
	

	public Player copyFromLast(DraculaPlayer activeDracula);
	
	public Player copyFromLast(HunterPlayer activeHunter);

}
