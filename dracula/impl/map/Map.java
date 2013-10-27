package dracula.impl.map;

import java.util.*;

import dracula.impl.*;


public interface Map {
	
	/**
	 * Gets a list of Strings that can be travelled to from the current String.
	 */
	List<String> getAdjacentFor(String String, EnumSet<TravelBy> by);
	
	/**
	 * Returns true if the String is at sea
	 */
	boolean isAtSea(String String);
	
	 /**
	* Returns true if the location is on the rail
	*/
	boolean isOnRoad(String location);

	/**
	* Returns true if the location is on the road
	*/
	boolean isOnRail(String location);

	/**
	* Returns true if the location is either a port city or at sea
	*/
	boolean isOnSeaRoute(String location);
	
	/**
	 * Is the given location an inland city
	 */
	public boolean isCity(String loc);
	
	/**
	 * Returns the String that represents the hospital for hunters
	 */
	String getHospital();
	
	/**
	 * Returns the String that represents Castle Dracula.
	 */
	String getCastle();
	
	/**
     * Uses the pathfinder to get Route from one String to another using specified travel type
     */
    ArrayList<String> getRoute(String start, String finish, ArrayList<String> avoid, TravelBy by);

    /**
     * Gets the minimum distance between two locations.
     * If there are both road and rail between A and B, return the minimum of the two.
     * 
     * TODO: Incorporate sea routes.
     */
	int getMinDistanceBetween(String loc1, String loc2);
}
