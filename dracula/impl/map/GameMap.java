package dracula.impl.map;

import java.util.*;
import java.util.Map.Entry;

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
	
	public static final String HOSPITAL = "JM";
	public static final String CASTLE = "CD";

	private List<String> inlandStrings = new ArrayList<String>();
	private List<String> portStrings = new ArrayList<String>();
	private List<String> seaStrings = new ArrayList<String>();
	
	private AdjacencyMatrix roads;
	private AdjacencyMatrix rails;
	private AdjacencyMatrix seaRoutes;
        private AdjacencyMatrix roadAndSea;
	
	private List<String> allLocations = new ArrayList<String>();
	private String hospital;
	private String castle;
	
	// Mixed land and sea, for quick lookup
	private HashMap<String, String> locationToCodes = new HashMap<String, String>();
	
	public GameMap() {
		// String Codes.
		loadStringCodes(GameMapStrings.inlandCities(), this.inlandStrings);
		loadStringCodes(GameMapStrings.portCities(), this.portStrings);
		loadStringCodes(GameMapStrings.seas(), this.seaStrings);
		
		// Load Land Maps.
		String[] nodes = new String[inlandStrings.size() + portStrings.size()];
		List<String> combinedStrings = new ArrayList<String>();
		combinedStrings.addAll(inlandStrings);
		combinedStrings.addAll(portStrings);
		combinedStrings.toArray(nodes);
		
		this.roads = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.roadMap(), combinedStrings));
		this.rails = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.railMap(), combinedStrings));
		
		// Load Sea Map.
		nodes = new String[seaStrings.size() + portStrings.size()];
		combinedStrings = new ArrayList<String>();
		combinedStrings.addAll(portStrings);
		combinedStrings.addAll(seaStrings);
		
		combinedStrings.toArray(nodes);
		
		this.seaRoutes = new AdjacencyMatrix(nodes, loadMap(GameMapStrings.seaMap(), combinedStrings));
                
                //combined adjacency matrix for road and sea
                this.roadAndSea = AdjacencyMatrix.combine(roads, seaRoutes);
		
		// Create a list of Locations.
		combinedStrings.addAll(inlandStrings);
		for (String s : combinedStrings) {
			if (s.equals("JM"))
				this.hospital = s;
			if (s.equals("CD"))
				this.castle = s;
			
			allLocations.add(s);
		}
	}
	
	private void loadStringCodes(List<String> locationStrings, List<String> codeList) {
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

	private int[][] loadMap(List<String> mapLines, List<String> combinedStrings) {
		int size = combinedStrings.size();
		int[][] matrix = new int[size][size];
		
		for (String line : mapLines) {
		    // process line.  e.g. Alicante -- Madrid 
			String[] parts = line.split("--");
			String loc1 = parts[0].trim();
			String loc2 = parts[1].trim();
			
			// Get index of location code.
			int idx1 = combinedStrings.indexOf(locationToCodes.get(loc1));
			int idx2 = combinedStrings.indexOf(locationToCodes.get(loc2));

			// Update matrix.
			matrix[idx1][idx2] = matrix[idx2][idx1] = 1;
		}
		return matrix;	
	}

	@Override
	public List<String> getAdjacentFor(String location, EnumSet<TravelBy> by) {
		List<String> locations = new ArrayList<String>();
		
		if (by.contains(TravelBy.road)) {
			if (location.equals(""))
				locations.addAll(Arrays.asList(this.roads.getVertices()));
			else
				locations.addAll(Arrays.asList(this.roads.adjacentVertices(location)));
		}
		if (by.contains(TravelBy.rail)) {
			if (location.equals(""))
				locations.addAll(Arrays.asList(this.rails.getVertices()));
			else
				locations.addAll(Arrays.asList(this.rails.adjacentVertices(location)));
		}
		if (by.contains(TravelBy.sea)) {
			if (location.equals(""))
				locations.addAll(Arrays.asList(this.seaRoutes.getVertices()));
			else
				locations.addAll(Arrays.asList(this.seaRoutes.adjacentVertices(location)));
		}
		return locations;
	}

	@Override
	public boolean isAtSea(String loc) {
		return this.seaStrings.contains(loc);
	}

	@Override
	public boolean isOnRoad(String location) {
		return isLocationInMatrix(this.roads, location);
	}

	@Override
	public boolean isOnRail(String location) {
		return isLocationInMatrix(rails, location);
	}
	
	@Override
	public boolean isOnSeaRoute(String location) {
		return isLocationInMatrix(seaRoutes, location);
	}
	
	private boolean isLocationInMatrix(AdjacencyMatrix matrix, String location) {
		// Location is the key of an edge?
		boolean is = matrix.edgesAsReadonly().containsKey(location);
		if (is)
			return true;
		
		// Location is in the values of an edge which is keyed under another location?
		for(Entry<String, List<String>> edges : matrix.edgesAsReadonly().entrySet()) {
			if (edges.getValue().contains(location))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean isCity(String loc) {
		return !this.isAtSea(loc);
	}	
	
	@Override
	public String getHospital() {
		return this.hospital;
	}

	@Override
	public String getCastle() {
		return this.castle;
	}
	
	/*
     * uses pathFinder to get the route between two cities (assuming they can be reached from one another)
     */
    @Override
    public ArrayList<String> getRoute(String start, String finish, List<String> avoid, TravelBy by) {
        switch (by) {
            case road:
                return PathFinder.getPath(start, finish, avoid, roads);

            case rail:
                return PathFinder.getPath(start, finish, avoid, rails);

            case sea:
                return PathFinder.getPath(start, finish, avoid, seaRoutes);

            default:
                return PathFinder.getPath(start, finish, avoid, roadAndSea);
        }
    }
    
    @Override
    public int hashCode() {
    	int hash = roads.hashCode() + rails.hashCode() + seaRoutes.hashCode();
    	return Math.abs(hash);
    }

    /**
     * Gets the minimum distance between two locations.
     * If there are both road and rail between A and B, return the minimum of the two.
     * 
     */
	@Override
	public int getMinDistanceBetween(String loc1, String loc2) {
		/*int dRoad = roads.getMinDist(loc1, loc2);
		int dRail = rails.getMinDist(loc1, loc2);
		int dSea = seaRoutes.getMinDist(loc1, loc2);
		
		int min = 0;
		
		if (dRoad > 0) {
			if (min == 0 || dRoad < min) 
				min = dRoad;
		}
		if (dRail > 0) {
			if (min == 0 || dRail < min) 
				min = dRail;
		}
		
		if (dSea > 0) {
			if (min == 0 || dSea < min) 
				min = dSea;
		}
		
		return min;
                */
                return this.roadAndSea.getMinDist(loc1, loc2);
	}
        
        @Override 
        public List<String> getCities(){
            return this.inlandStrings;
        }
        
}
