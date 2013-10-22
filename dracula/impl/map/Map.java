package dracula.impl.map;

import java.util.*;
import dracula.impl.*;


public interface Map {
	
	/**
	 * Gets a list of locations that can be travelled to from the current location.
	 */
	List<Location> getAdjacentFor(Location location, EnumSet<TravelBy> by);
	
	/**
	 * Returns true if the location is at sea
	 */
	boolean isAtSea(Location location);
	
	/**
	 * Returns the location that represents the hospital for hunters
	 */
	Location getHospital();
	
	/**
	 * Returns the location that represents Castle Dracula.
	 */
	Location getCastle();
	
	/**
     * Uses the pathfinder to get Route from one location to another using specified travel type
     */
    ArrayList<String> getRoute(String start, String finish, ArrayList<String> avoid, TravelBy by);
}
