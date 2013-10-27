package dracula.impl;

import java.util.HashMap;
import java.util.Map.Entry;

import dracula.impl.map.*;
import dracula.*;

/**
 * Encapsulates complete board state.
 * 
 * @author alex
 */
public class Board implements BoardState {
	
	// State.
	private Map map;
	private int turn;
	private int score;

	// Players.
	private Dracula dracula;
	private HashMap<String, Player> players;
			
	public Board() {
		// Init.
		this.map = new GameMap();
		this.turn = 0;
		this.score = 366;		
		
		// Players.
		this.dracula = new Dracula();
		
		this.players = new HashMap<String, Player>();
		players.put("G", new Hunter("Lord Godalming"));
		players.put("S", new Hunter("Dr Seward"));
		players.put("H", new Hunter("Van Helsing"));
		players.put("M", new Hunter("Mina Harker"));
		players.put("D", dracula);
	}
	
	public void parsePastPlay(String pastPlay) {
		String id = pastPlay.substring(0, 1);
		players.get(id).parsePastPlay(pastPlay, this);
		turn++;
	}

	public Dracula getDracula() {
		return this.dracula;
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public int getRound() {
		return this.turn / 5;	// 5 players
	}

	@Override
	public BoardState getNextState(Move move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDracHealth() {
		return this.dracula.getHealth();
	}

	@Override
	public int[] getHunterHealth() {
		int[] out = new int[4];
		int i = 0;
		for (Player player : this.players.values()) {
			if(player != this.dracula) {
				out[i++] = player.getHealth();
			}
		}
		return out;
	}

	@Override
	public Move[] getLegalMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getHunterDistances() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases
		Board b = new Board();
		b.parsePastPlay("GBE.... SBR.... HLO.... MCA.... DSJ.V.. GSJVD..");
	}
}