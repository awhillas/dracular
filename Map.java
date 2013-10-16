import java.util.*;


public interface Map {
	
	/**
	 * Initialise map with files containing location names and their 2-letter codes.
	 * Each line in the file must be in the format of e.g. "|| AL || Alicante ||"
	 */
	void init(String inlandLocations, String portLocations, String seaLocations);
	
	/**
	 * Load maps from road, rail and sea map files.
	 * Each line in the file must be in the format of e.g. "Alicante -- Madrid", representing a route 
	 * between a pair of locations. Ordering of the pair is not important. 
	 */
	void loadMaps(String roadMap, String railMap, String seaMap);
	
	/**
	 * Gets a list of locations that can be travelled to from the current location.
	 */
	List<String> getAdjacentFor(String location, EnumSet<TravelBy> by);
	
	/**
	 * Returns true if the location is at sea
	 */
	boolean isAtSea(String location);
}
