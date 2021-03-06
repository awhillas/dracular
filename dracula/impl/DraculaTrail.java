/**
 * 
 */
package dracula.impl;

import java.util.ArrayList;
import java.util.LinkedList;

import dracula.Encounter;

/**
 * Holds the list of the last 6 moves (or actions) that the Dracular player
 * has made.
 * Note these are not the actual locations which can be calculated using 
 * this object.
 * All the querys we have for the Trial should be in here.
 * 
 * @author alex
 */
public class DraculaTrail {

	public static int TRAIL_LENGTH = 6;
	
	private LinkedList<Move> trail;
	private LinkedList<Encounter> nasties;

	/**
	 * 
	 */
	public DraculaTrail() {
		this.trail = new LinkedList<Move>();
		this.nasties = new LinkedList<Encounter>();
	}
	/*
	 * copy constructor
	 * TODO: clone is not a good word for this :-(
	 */
	public DraculaTrail clone(){
	    DraculaTrail clone = new DraculaTrail();
	    clone.nasties = new LinkedList<Encounter>();
	    for (Encounter e : this.nasties){
	        clone.nasties.add(e);
	    }
	    clone.trail = new LinkedList<Move>();
	    for (Move m : this.trail){
	        clone.trail.add(m);
	    }
	    return clone;
	}
	
	/**
	 * Add a move and an Encouter to the trail.
	 * Returns the last Encounter on the trail if the trail becomes too long 
	 * (so it can be used by the board if its a Vampire).
	 * 
	 * @param move
	 * @param nasty
	 * @return
	 */
	public Encounter addMove(Move move, Encounter nasty) {
		// Record the move
		this.trail.offer(move);	// add to end of Queue
		if (this.trail.size() > TRAIL_LENGTH) {
			this.trail.poll();	// remove from head of Queue
		}
		// Check the encounter
		this.nasties.offer(nasty);
		if(this.nasties.size() > TRAIL_LENGTH) {
			return this.nasties.poll();
		}
		return null;
	}
	
	public int getLength() {
		return trail.size();
		/*
		int count = 0;
		for(Move move : trail) {
			if (move != null) {
				count++;
			}
		}
		return count;
		*/
	}
	
	/**
	 * Get a list of all encounters for the given location.
	 * @param location
	 * @return
	 */
	public Encounter[] getEncountersAt(String location) {
		ArrayList<Encounter> out = new ArrayList<Encounter>();
		for(Encounter e : nasties) {
			if(e.getLocation().equals(location)) {
				out.add(e);
			}
		}
		return out.toArray(new Encounter[out.size()]);
	}
	
	public boolean disarm(Encounter disarmed) {
		return this.nasties.removeFirstOccurrence(disarmed);
	}
	
	public boolean containsMove(String action) {
		for(Move move : trail) {
			if(move.getPlayAsString().equals(action)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsLocation(String location) {
		for(Move move : trail) {
			if(move.getLocation().equals(location)) {
				return true;
			}
		}
		return false;	
	}
	
	/*
         * returns list of locations in trail
         */
        public ArrayList<String> getLocations(){
            ArrayList<String> locs = new ArrayList<String>();
            for(Move move : trail) {
			locs.add(move.getLocation());
		}
            return locs;
        }

	public boolean hasDoubledBack() {
		for(int i = 1; i < TRAIL_LENGTH + 1; i++) {
			if(this.containsMove("D"+Integer.toString(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean hasHidden() {
		return this.containsMove("HI");
	}
	
	public ArrayList<Move> getDoubleBackMoves() {
		ArrayList<Move> out = new ArrayList<Move>();
		if(!this.hasDoubledBack()) {
			for (int i = 1; i < this.getLength(); i++) {
				out.add(new Move("D"+i, getMoveAt(i).getLocation()));
			}
		}
		return out;
	}

	/**
	 * Return the move at the given move index (from 0-5)
	 */
	public Move getMoveAt(int index) {
		if(index >= 0 && index < trail.size()) {
			// Because the LinkedList adds to the end and we want to lookup 
			// with the index inverted i.e. zero should always be the last move
			return trail.get(trail.size() - index - 1);
		}
		return null;
	}
	
	public String toString() {
		return "Trail:"+this.trail+"(Traps:"+this.nasties+")";
	}
}
