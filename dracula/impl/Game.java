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
	
	// Players.
	private Dracula dracula;
	private HashMap<String, Hunter> hunters;
			
	public Game() {
		// Init.
		map = new GameMap();
		turn = 0;
		round = 0;
		score = 366;		
		
		// Players.
		dracula = new Dracula();
		
		hunters = new HashMap<String, Hunter>();
		hunters.put("G", new Hunter(this, "Lord Godalming"));
		hunters.put("S", new Hunter(this, "Dr Seward"));
		hunters.put("H", new Hunter(this, "Van Helsing"));
		hunters.put("M", new Hunter(this, "Mina Harker"));
	}
	
	public Dracula getDracula() {
		return this.dracula;
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public void update(String pastPlays, String[] messages) {

		// Update state.
		if (turn == 5) {
			turn = 0;
			round++;
		}
		turn++;

		// Only need to update the last move.
		String[] moves = pastPlays.split(" ");
		String newMove = moves[moves.length - 1];
		
		String name = newMove.substring(0, 1);
		try {
			if (name.toUpperCase().equals("D")) {
				dracula.update(newMove);
			} else {
				hunters.get(name).update(newMove);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * When Hunter encounters something, it notifies the game and the game decides what to do,
	 * e.g. if the code is HE_ENCOUNTERD_DRACULA, game informs dracula to reduce it's health by x.
	 */
	public void onHunterEncounter(Hunter hunter, int hunterEncounterCode) {
		// TODO: Add logic here
	}
	
	/** TODO We may not need this!! Check the logic.
	 * When Dracula has placed some action, it notifies the game and the game decides what to do,
	 * e.g. xxxxx
	 */
	public void onDraculaAction(int actionCode) {
		// TODO: Add logic here
	}
	
	// Hunter Encounter Code	
	public final int HE_ENCOUNTERD_DRACULA = 1;
	public final int HE_ENCOUNTERD_VAMPIRE = 2;
	public final int HE_ENCOUNTERD_TRAP = 3;
	
	// Dracula Action Code
	public final int DE_SET_TRAP = 1;
	public final int DE_SET_VAMPIRE = 2;
}