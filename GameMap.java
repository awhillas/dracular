import java.io.*;
import java.util.*;


/**
 * Represents a game map.
 * Uses AdjacencyMatrix internally to store routes between locations.
 * 
 * @author wgwa945
 * 
 */
public class GameMap implements Map {

	private List<String> landLocations = new ArrayList<String>();
	private List<String> seaLocations = new ArrayList<String>();
	
	private AdjacencyMatrix roads;
	private AdjacencyMatrix rails;
	private AdjacencyMatrix seaRoutes;
	
	// Mixed land and sea, for quick lookup
	private HashMap<String, String> locationToCodes = new HashMap<String, String>();
	private HashMap<String, String> codeToLocations = new HashMap<String, String>();
	
	public void init(String landLocationsFile, String seaLocationsFile) {
		try {
			loadLocationCodes(landLocationsFile, true);
			loadLocationCodes(seaLocationsFile, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMaps(String roadMap, String railMap, String seaMap) {
		try {
			// Land.
			String[] nodes = new String[landLocations.size()];
			landLocations.toArray(nodes);
			this.roads = new AdjacencyMatrix(nodes, loadMap(roadMap, true));
			this.rails = new AdjacencyMatrix(nodes, loadMap(railMap, true));
			
			// Sea.
			nodes = new String[seaLocations.size()];
			seaLocations.toArray(nodes);
			this.seaRoutes = new AdjacencyMatrix(nodes, loadMap(seaMap, false));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadLocationCodes(String locationCodeFile, boolean isLand) throws Exception {
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
				
				/* A location can belong to both land and sea
				if (this.locationToCodes.containsKey(location) || this.codeToLocations.containsKey(code))
					throw new Exception(String.format("Duplicate location or code \"%s, %s\" found in location code file! (%s)", location, code, locationCodeFile));
				*/
				if (isLand) {
					landLocations.add(code);
				}
				else {
					seaLocations.add(code);
				}
				
				// Cache.
				codeToLocations.put(code, location);
				locationToCodes.put(location, code);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null)
				br.close();
		}
	}
	
	private int[][] loadMap(String mapFile, boolean isLand) throws Exception {
		int[][] matrix = isLand 
				? new int[landLocations.size()][landLocations.size()]
				: new int[seaLocations.size()][seaLocations.size()];

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
				List<String> locations = isLand? landLocations : seaLocations;
				int idx1 = locations.indexOf(locationToCodes.get(loc1));
				int idx2 = locations.indexOf(locationToCodes.get(loc2));

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
	public List<String> getAdjacentLocations(String current, EnumSet<TravelBy> by) {
		List<String> locs = new ArrayList<String>();
		if (by.contains(TravelBy.road)) {
			for (String s : roads.adjacentVertices(current)) {
				locs.add(s);
			}
		}
		if (by.contains(TravelBy.rail)) {
			for (String s : rails.adjacentVertices(current)) {
				locs.add(s);
			}
		}
		if (by.contains(TravelBy.sea)) {
			for (String s : seaRoutes.adjacentVertices(current)) {
				locs.add(s);
			}
		}
		return locs;
	}
}
