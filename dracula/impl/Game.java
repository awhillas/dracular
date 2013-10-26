package dracula.impl;

import java.util.HashMap;

import dracula.impl.map.*;
import dracula.*;

public class Game {

	// State.
	private Map map;
	private int turn;
	private int round;
	private int score;
	private int vampireAliveInRounds;
	// Players.
	private Dracula dracula;
	private HashMap<String, Hunter> hunters = new HashMap<String, Hunter>();
			
	public Game() {
		// Init.
		map = new GameMap();
		turn = 0;
		round = 0;
		score = 366;		
		// Players 
		dracula = new Dracula(this);
	}
	
	public Dracula getDracula() {
		return this.dracula;
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public HashMap<String, Hunter> getHunters() {
		return this.hunters;
	}
	
	public void update(String pastPlays, String[] messages) {

		// Update state.
		if (turn == 5) {
			turn = 0;
			round++;
		}
		turn++;
		
		//Game tracks the vampire scoring
		//Dracula tracks the rounds 
		if (vampireAliveInRounds == 1) {
			this.vampireAliveInRounds--;
			this.score -= 13;
		} else if (this.vampireAliveInRounds > 1) {
			this.vampireAliveInRounds--;
		}

		// Only need to update the last move.
		String[] moves = pastPlays.split(" ");
		String newMoveString = moves[moves.length - 1];
		String name = newMoveString.substring(0, 1);
		Location location = new Location(newMoveString.substring(1,3));
		int hunterNum = 1;
		try {
			
			//dracula.update();
			if (!hunters.containsKey(name)) {
				hunters.put(name, new Hunter(this, name, hunterNum++, location));					
			} else {
				hunters.get(name).update(newMoveString);	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * When Hunter encounters something, it notifies the game and the game decides what to do,
	 * e.g. if the code is HE_ENCOUNTERD_DRACULA, game informs dracula to reduce it's health by x.
	 * 
	 * "Notice that the magical teleportation to the hospital will not show up in 
	 * the game history, players will have to deduce that they are in the hospital." 
	 */
	public void onHunterEncounter(Hunter hunter, int hunterEncounterCode) {
		if (hunterEncounterCode == this.HE_ENCOUNTERD_DRACULA) {
			dracula.setHealth(-10);
			hunter.setHealth(-4);
			//this.score -= 
		}
		if (hunterEncounterCode == this.HE_ENCOUNTERD_VAMPIRE) 
			this.vampireAliveInRounds = 0;
		if (hunterEncounterCode == this.HE_ENCOUNTERD_TRAP) 
			hunter.setHealth(-2);
			//this.score -= 
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getRound() {
		return this.round;
	}
	
	public void setVampire() {
		this.vampireAliveInRounds = 6; 
	}
	
	// Hunter Encounter Code	
	public final int HE_ENCOUNTERD_DRACULA = 1;
	public final int HE_ENCOUNTERD_VAMPIRE = 2;
	public final int HE_ENCOUNTERD_TRAP = 3;
	
	// Dracula Action Code
	public final int DE_SET_TRAP = 1;
	public final int DE_SET_VAMPIRE = 2;
}