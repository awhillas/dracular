import java.util.*;


public interface Map {
	
	/**
	 * Initialise map with files containing location names and their 2-letter codes.
	 * Each line in the file must be in the format of e.g. "|| AL || Alicante ||"
	 */
	void init(String landLocationsFile, String seaLocationsFile);
	
	/**
	 * Load maps from road, rail and sea map files.
	 * Each line in the file must be in the format of e.g. "Alicante -- Madrid", representing a route 
	 * between a pair of locations. Ordering of the pair is not important. 
	 */
	void loadMaps(String roadMap, String railMap, String seaMap);
	
	/**
	 * Gets a list of locations that can be travelled to from the current location.
	 */
	List<String> getAdjacentLocations(String current, EnumSet<TravelBy> by);
}
