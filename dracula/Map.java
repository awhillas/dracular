package dracula;
import java.util.*;


public interface Map {
	
	/**
	 * Gets a list of locations that can be travelled to from the current location.
	 */
	List<String> getAdjacentFor(String location, EnumSet<TravelBy> by);
	
	/**
	 * Returns true if the location is at sea
	 */
	boolean isAtSea(String location);
	
	/*
     * Uses the pathfinder to get Route from one location to another using specified travel type
     */
    ArrayList<String> getRoute(String start, String finish, ArrayList<String> avoid, TravelBy by);
}
