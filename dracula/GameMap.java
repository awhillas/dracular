package dracula;
import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * Represents a game map.
 * Uses AdjacencyMatrix internally to store routes between locations.
 * 
 * @author wgwa945
 * 
 */
public class GameMap implements Map {

	private List<String> inlandLocations = new ArrayList<String>();
	private List<String> portLocations = new ArrayList<String>();
	private List<String> seaLocations = new ArrayList<String>();
	
	private AdjacencyMatrix roads;
	private AdjacencyMatrix rails;
	private AdjacencyMatrix seaRoutes;
	
	// Mixed land and sea, for quick lookup
	private HashMap<String, String> locationToCodes = new HashMap<String, String>();
	
	public void init(String inlandLocations, String portLocations, String seaLocationsFile) {
		try {
			loadLocationCodes(inlandLocations, this.inlandLocations);
			loadLocationCodes(portLocations, this.portLocations);
			loadLocationCodes(seaLocationsFile, this.seaLocations);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadLocationCodes(String locationCodeFile, List<String> codeList) throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(locationCodeFile));
			String line;
	
			while ((line = br.readLine()) != null) {
			    // process line.  e.g. || AL || Alicante ||
				String[] parts = line.split("\\|\\|");
				if (parts.length != 3)
					throw new Exception(String.format("Invalid line \"%s\" in location code file! (%s)", line, locationCodeFile));

				String code = parts[1].trim();
				String location = parts[2].trim();
				
				if (this.locationToCodes.containsKey(location))
					throw new Exception(String.format("Duplicate location or code \"%s, %s\" found in location code file! (%s)", location, code, locationCodeFile));

				codeList.add(code);
				
				// Cache.
				locationToCodes.put(location, code);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null)
				br.close();
		}
	}

	public void loadMaps(String roadMap, String railMap, String seaMap) {
		try {
			// Land.
			String[] nodes = new String[inlandLocations.size() + portLocations.size()];
			List<String> combinedLocations = new ArrayList<String>();
			combinedLocations.addAll(inlandLocations);
			combinedLocations.addAll(portLocations);
			combinedLocations.toArray(nodes);
			
			this.roads = new AdjacencyMatrix(nodes, loadMap(roadMap, combinedLocations));
			this.rails = new AdjacencyMatrix(nodes, loadMap(railMap, combinedLocations));
			
			// Sea.
			nodes = new String[seaLocations.size() + portLocations.size()];
			combinedLocations = new ArrayList<String>();
			combinedLocations.addAll(portLocations);
			combinedLocations.addAll(seaLocations);
			combinedLocations.toArray(nodes);
			
			this.seaRoutes = new AdjacencyMatrix(nodes, loadMap(seaMap, combinedLocations));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param 	mapFile:			a file containing routes between 2 locations			
	 * @param 	combinedLocations	combined inland and port locations, or combined port and sea locations
	 * @return						a matrix for the adjacency matrix
	 * @throws 	Exception
	 */
	private int[][] loadMap(String mapFile, List<String> combinedLocations) throws Exception {
		int size = combinedLocations.size();
		int[][] matrix = new int[size][size];

		BufferedReader br = null;		
		try {
			br = new BufferedReader(new FileReader(mapFile));
			String line;
			
			while ((line = br.readLine()) != null) {
			    // process line.  e.g. Alicante -- Madrid 
				String[] parts = line.split("--");
				if (parts.length != 2)
					throw new Exception(String.format("Invalid line \"%s\" in map file! (%s), ensure each line is in the format of \"Location1 -- Location2\"", line, mapFile));
				String loc1 = parts[0].trim();
				String loc2 = parts[1].trim();
				
				if (!this.locationToCodes.containsKey(loc1))
					throw new Exception(String.format("Invalid location \"%s\" in map file! (%s)", loc1, mapFile));
				if (!this.locationToCodes.containsKey(loc2))
					throw new Exception(String.format("Invalid location \"%s\" in map file! (%s)", loc2, mapFile));
				
				// Get index of location code.
				int idx1 = combinedLocations.indexOf(locationToCodes.get(loc1));
				int idx2 = combinedLocations.indexOf(locationToCodes.get(loc2));

				// Update matrix.
				matrix[idx1][idx2] = matrix[idx2][idx1] = 1;
			}
			return matrix;			
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null)
				br.close();
		}		
	}

	@Override
	public List<String> getAdjacentFor(String location, EnumSet<TravelBy> by) {
		List<String> locs = new ArrayList<String>();
		if (by.contains(TravelBy.road)) {
			locs.addAll(Arrays.asList(this.roads.adjacentVertices(location)));
		}
		if (by.contains(TravelBy.rail)) {
			locs.addAll(Arrays.asList(this.rails.adjacentVertices(location)));
		}
		if (by.contains(TravelBy.sea)) {
			locs.addAll(Arrays.asList(this.seaRoutes.adjacentVertices(location)));
		}
		return locs;
	}

	@Override
	public boolean isAtSea(String loc) {
		return this.seaLocations.contains(loc);
	}
	
	/*
     * uses pathFinder to get the route between two cities (assuming they can be reached from one another)
     */
    @Override
    public ArrayList<String> getRoute(String start, String finish, ArrayList<String> avoid, TravelBy by) {
        switch (by) {
            case road:
                return PathFinder.getPath(start, finish, avoid, roads);

            case rail:
                return PathFinder.getPath(start, finish, avoid, rails);

            case sea:
                return PathFinder.getPath(start, finish, avoid, seaRoutes);

            default:
                return new ArrayList<String>();
        }
    }
    
    @Override
    public int hashCode() {
    	int hash = roads.hashCode() + rails.hashCode() + seaRoutes.hashCode();
    	return Math.abs(hash);
    }
    
    public static GameMap getDracularsMap() {
		GameMap m = new GameMap();

		// Init.
		URL inlandCities = m.getClass().getResource("/maps/inlandCities.txt");
		URL portCities = m.getClass().getResource("/maps/portCities.txt");
		URL seas = m.getClass().getResource("/maps/seas.txt");
		m.init(inlandCities.getPath(), portCities.getPath(), seas.getPath());
		
		// Load Maps.
		URL road = m.getClass().getResource("/maps/road.txt");
		URL rail = m.getClass().getResource("/maps/rail.txt");
		URL sea = m.getClass().getResource("/maps/sea.txt");
		m.loadMaps(road.getPath(), rail.getPath(), sea.getPath());
		
		return m;
    }
}
