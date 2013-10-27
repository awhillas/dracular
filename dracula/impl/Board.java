package dracula.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * Copy constructor
	 */
	public static Board makeBoard(Board b) {
		Board newBoard = new Board();
		
		newBoard.setScore(b.getScore());
		newBoard.setTurn(b.getTurn());
		
		return newBoard;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
     * @TODO: Perhaps move this to Dracula class?
     */
	@Override
	public Move[] getLegalMoves() {
		List<Move> options = new ArrayList<Move>();

		List<String> adjacent = map.getAdjacentFor(dracula.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
		
		for (String loc : adjacent) {
			if(!dracula.getTrail().containsLocation(loc) && !loc.equals(GameMap.HOSPITAL)) {
				options.add(new Move(loc));
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
		
		String dLoc = this.dracula.getLocation();		
		int[] distances = new int[players.size()];

		int i = 0;
		while (players.entrySet().iterator().hasNext()) {
			String hunterLoc = players.entrySet().iterator().next().getValue().getLocation();
			distances[i] = map.getMinDistanceBetween(dLoc, hunterLoc);
		}
		
		return distances;
	}
	
	public HashMap<String, Player> getHunters() {
		return this.players;
	}

	@Override
	public BoardState getNextState(Move dracMove, Move[] hunterMoves) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * "Rail moves: The maximum distance that can be moved via rail is determined 
	 * by the sum of the round number (0..366) and the Hunter number (0..3)
	 * 	- sum mod 4 is 0: No train move is permitted for hunters this turn.
	 * 	- sum mod 4 is 1: Hunters may move to any city adjacent to the current 
	 *    city via a rail link.
	 *  - sum mod 4 is 2: Hunters may move to any city adjacent to the current 
	 *    city via a rail link, or any city adjacent via rail to such a city.
	 *  - sum mod 4 is 3: Hunters may move to any city adjacent to the current 
	 *    city via a rail link, or any city adjacent via rail to such a city, 
	 *    or any city adjacent via rail to such a city.  (IE move up to three 
	 *    steps by rail).
	 *  When the rail move is to a non-adjacent city the Hunter does not 
	 *  actually enter the intermediate cities, so any encounters there are not 
	 *  encountered etc."
	 */
	@Override
	public List<List<Move>> getHunterMoves() {
		List<List<Move>> out = new ArrayList<List<Move>>();
		
		for(Player h : players.values()) {
			if(h.getNumber() != -1) {	// i.e. not Dracula
				
				// Seas and Cities!
				List<String> validMoves = map.getAdjacentFor(h.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
								
				// Rail is more complex
				int railHops = (h.getNumber() + getRound()) % 4;
				if(map.isOnRail(h.getLocation()) && railHops > 0) {
					// Breadth First Depth Limited Search of the rail network.
					Set<String> railCities = new HashSet<String>(map.getAdjacentFor(h.getLocation(), EnumSet.of(TravelBy.rail)));
					Set<String> frontier = railCities;
					Set<String> newFrontier = new HashSet<String>();
					
					for(int i = 1; i < railHops; i++) {	// depth
						for(String city : frontier) {
							newFrontier.addAll(map.getAdjacentFor(city, EnumSet.of(TravelBy.rail)));
							newFrontier.removeAll(railCities);
						}
						railCities.addAll(newFrontier);
						frontier = newFrontier;
					}
					
					railCities.remove(h.getLocation());
					validMoves.addAll(railCities);
				}
				out.add(locations2Moves(validMoves));
			}
		}
		return out;
	}
	
	public static ArrayList<Move> locations2Moves(Collection<String> locations) {
		ArrayList<Move> out = new ArrayList<Move>();
		for(String loc : locations) {
			out.add(new Move(loc));
		}
		return out;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO simple test cases
		Board b = new Board();
		b.parsePastPlay("GBE.... SBR.... HLO.... MCA.... DSJ.V.. GSJVD..");
		System.out.println("YAY!");
	}
}