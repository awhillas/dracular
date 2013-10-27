package dracula.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
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
		/*
		 * TODO added the player numbers as we need these to counter their 
		 * possible rail moves in the AI 
		 */
		players.put("G", new Hunter("G", 0)); // Lord Godalming
		players.put("S", new Hunter("S", 1)); // Dr Seward
		players.put("H", new Hunter("H", 2)); // Van Helsing
		players.put("M", new Hunter("M", 3)); // Mina Harker
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
		// TODO: this is not right...?
		return this.turn / 5;	// 5 players
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

    /**
     * Returns a list of legal moves for Dracula to make in current board state
     * @TODO:
     */
	@Override
	public Move[] getLegalMoves() {
		List<Move> options = new ArrayList<Move>();

		List<String> optionLocs = map.getAdjacentFor(dracula.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
		
		for (String loc : optionLocs) {
			if(!dracula.getTrail().containsLocation(loc) && !loc.equals(GameMap.HOSPITAL)) {
				options.add(new Move(loc, loc));
			}
		}
		if (dracula.canHide()) {
			options.add(new Move("HI", dracula.getLocation()));
		}
		if (dracula.canDoubleBack()) {
			options.addAll(dracula.getTrail().getDoubleBackMoves());
		}
		if (dracula.canTeleport()) {
			options.add(new Move("TP", GameMap.CASTLE));
		}
		return options.toArray(new Move[options.size()]);
	}

	@Override
	public int[] getHunterDistances() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public HashMap<String, Player> getHunters() {
		return this.players;
	}

	@Override
	public BoardState getNextState(Move dracMove, Move[] hunterMoves) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move[][] getHunterMoves() {
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