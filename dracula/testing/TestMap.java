package dracula.testing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;

import dracula.*;
import dracula.Map;


public class TestMap {

	public static void main(String[] args) {
		
		/*
		 * Map basic tests
		 * 
		 */
		Map m = new GameMap();
		
		// Madrid
		assert m.getAdjacentFor("MA", EnumSet.allOf(TravelBy.class)).size() == 10;
		assert m.getAdjacentFor("MA", EnumSet.of(TravelBy.road)).size() == 6;
		assert m.getAdjacentFor("MA", EnumSet.of(TravelBy.rail)).size() == 4;
		assert m.getAdjacentFor("MA", EnumSet.of(TravelBy.sea)).size() == 0;
		
		// Cagliari
		assert m.getAdjacentFor("CG", EnumSet.allOf(TravelBy.class)).size() == 2;
		assert m.getAdjacentFor("CG", EnumSet.of(TravelBy.road)).size() == 0;
		assert m.getAdjacentFor("CG", EnumSet.of(TravelBy.rail)).size() == 0;
		assert m.getAdjacentFor("CG", EnumSet.of(TravelBy.sea)).size() == 2;
				
		// Venice
		assert m.getAdjacentFor("VE", EnumSet.allOf(TravelBy.class)).size() == 6;
		assert m.getAdjacentFor("VE", EnumSet.of(TravelBy.road)).size() == 4;
		assert m.getAdjacentFor("VE", EnumSet.of(TravelBy.rail)).size() == 1;
		assert m.getAdjacentFor("VE", EnumSet.of(TravelBy.sea)).size() == 1;
		
		assert !m.isAtSea("VE");
		assert m.isAtSea("NS");
		
		/*
		 * Map Hash tests
		 * 
		 */
		int h = m.hashCode();
		assert h == 1234107428;
		
		/*
		// Modify map, change "BE" for Belgrade to "BL", rest is the same
		String inlandCities = TestMap.class.getResource(GameMap.getFullPath("for testing/inlandCities.txt")).getPath();
		String road = TestMap.class.getResource(GameMap.getFullPath("road.txt")).getPath();
		m = createMap(inlandCities, road);
		h = m.hashCode(); 
		assert h == 500910866;
		
		// Continue modifying map, change road between Alicante -- Madrid to Madrid -- Alicante 
		// Hash should be the same.
		inlandCities = TestMap.class.getResource(GameMap.getFullPath("for testing/inlandCities.txt")).getPath();
		road = TestMap.class.getResource(GameMap.getFullPath("for testing/road.txt")).getPath();
		m = createMap(inlandCities, road);
		h = m.hashCode();
		assert h == 500910866;
		
		// Restore map.
		m = createMap();
		h = m.hashCode();
		assert h == 1234107428;
		*/
		
		/*
		 * Path tests
		 * 
		 */		
		ArrayList<String> avoid = new ArrayList<String>();
        ArrayList<String> path = m.getRoute("LS", "AL", avoid, TravelBy.road);
        assert path.size() == 3;
        assert path.get(0).equals("LS");
        assert path.get(1).equals("MA");
        assert path.get(2).equals("AL");
        
        avoid.add("SN");
        avoid.add("MA");
        path = m.getRoute("LS", "AL", avoid, TravelBy.road);
        assert path.size() == 4;
        assert path.get(0).equals("LS");
        assert path.get(1).equals("CA");
        assert path.get(2).equals("GR");
        assert path.get(3).equals("AL");
        
        path = m.getRoute("LS", "CN", avoid, TravelBy.road);
        System.out.print("Path from LS to CN: ");
        for (int i = 0; i < path.size(); i++){
            System.out.print(path.get(i)+ ", ");
        }
        System.out.println();
        
        path = m.getRoute("AT", "LE", avoid, TravelBy.road);
        System.out.print("Path from AT to LE: ");
        for (int i = 0; i < path.size(); i++){
            System.out.print(path.get(i)+ ", ");
        }
        System.out.println();
	}
}
