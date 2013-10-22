package dracula.impl.map;

import java.util.*;

import dracula.impl.*;
import dracula.impl.ai.PathFinder;


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
	
	private List<Location> allLocations = new ArrayList<Location>();
	private Location hospital;
	private Location castle;
	
	// Mixed land and sea, for quick lookup
	private HashMap<String, String> locationToCodes = new HashMap<String, String>();
	
	public GameMap() {
		// Location Codes.
		loadLocationCodes(GameMapStrings.inlandCities(), this.inlandLocations);
		loadLocationCodes(GameMapStrings.portCities(), this.portLocations);
		loadLocationCodes(GameMapStrings.seas(), this.seaLocations);
		
		// Load Land Maps.
		String[] nodes = new String[inlandLocations.size() + portLocations.size()];
		List<String> combinedLocations = new ArrayList<String>();
		combinedLocations.addAll(inlandLocations);
		combinedLocations.addAll(portLocations);
		combinedLocations.toArray(nodes);
		
		this.roads = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.roadMap(), combinedLocations));
		this.rails = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.railMap(), combinedLocations));
		
		// Load Sea Map.
		nodes = new String[seaLocations.size() + portLocations.size()];
		combinedLocations = new ArrayList<String>();
		combinedLocations.addAll(portLocations);
		combinedLocations.addAll(seaLocations);
		
		combinedLocations.toArray(nodes);
		
		this.seaRoutes = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.seaMap(), combinedLocations));
		
		// Create a list of Locations.
		combinedLocations.addAll(inlandLocations);
		for (String s : combinedLocations) {
			Location loc = new Location(s);
			
			if (s.equals("JM"))
				this.hospital = loc;
			if (s.equals("CD"))
				this.castle = loc;
			
			allLocations.add(loc);
		}
	}
	
	private void loadLocationCodes(List<String> locationStrings, List<String> codeList) {
		for (String line : locationStrings) {
			// process line.  e.g. || AL || Alicante ||
			String[] parts = line.split("\\|\\|");
			String code = parts[1].trim();
			String location = parts[2].trim();

			codeList.add(code);
			
			// Cache.
			locationToCodes.put(location, code);
		}
	}

	private int[][] loadMap(List<String> mapLines, List<String> combinedLocations) {
		int size = combinedLocations.size();
		int[][] matrix = new int[size][size];
		
		for (String line : mapLines) {
		    // process line.  e.g. Alicante -- Madrid 
			String[] parts = line.split("--");
			String loc1 = parts[0].trim();
			String loc2 = parts[1].trim();
			
			// Get index of location code.
			int idx1 = combinedLocations.indexOf(locationToCodes.get(loc1));
			int idx2 = combinedLocations.indexOf(locationToCodes.get(loc2));

			// Update matrix.
			matrix[idx1][idx2] = matrix[idx2][idx1] = 1;
		}
		return matrix;	
	}

	@Override
	public List<Location> getAdjacentFor(Location location, EnumSet<TravelBy> by) {
		List<String> adjacencies = new ArrayList<String>();
		if (by.contains(TravelBy.road)) {
			adjacencies.addAll(Arrays.asList(this.roads.adjacentVertices(location.getName())));
		}
		if (by.contains(TravelBy.rail)) {
			adjacencies.addAll(Arrays.asList(this.rails.adjacentVertices(location.getName())));
		}
		if (by.contains(TravelBy.sea)) {
			adjacencies.addAll(Arrays.asList(this.seaRoutes.adjacentVertices(location.getName())));
		}
		
		List<Location> result = new ArrayList<Location>();
		for (String l : adjacencies) {
			for (Location loc : allLocations) {
				if (loc.getName().equals(l)) {
					result.add(loc);
					break;
				}
			}
		}
		return result;
	}

	@Override
	public boolean isAtSea(Location loc) {
		return this.seaLocations.contains(loc.getName());
	}
	
	@Override
	public Location getHospital() {
		return this.hospital;
	}

	@Override
	public Location getCastle() {
		return this.castle;
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


}
