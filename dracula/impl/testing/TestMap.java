package dracula.impl.testing;

import java.util.*;
import dracula.impl.*;
import dracula.impl.map.*;
import dracula.impl.map.Map;


public class TestMap {

	public static void main(String[] args) {
		
		/*
		 * Map basic tests
		 * 
		 */
		Map m = new GameMap();
		
		// Madrid
		String ma = "MA";
		assert m.getAdjacentFor(ma, EnumSet.allOf(TravelBy.class)).size() == 10;
		assert m.getAdjacentFor(ma, EnumSet.of(TravelBy.road)).size() == 6;
		assert m.getAdjacentFor(ma, EnumSet.of(TravelBy.rail)).size() == 4;
		assert m.getAdjacentFor(ma, EnumSet.of(TravelBy.sea)).size() == 0;
		assert m.isOnRoad(ma);
		assert m.isOnRail(ma);
		assert !m.isOnSeaRoute(ma);
		
		// Cagliari
		String cg = "CG";
		assert m.getAdjacentFor(cg, EnumSet.allOf(TravelBy.class)).size() == 2;
		assert m.getAdjacentFor(cg, EnumSet.of(TravelBy.road)).size() == 0;
		assert m.getAdjacentFor(cg, EnumSet.of(TravelBy.rail)).size() == 0;
		assert m.getAdjacentFor(cg, EnumSet.of(TravelBy.sea)).size() == 2;
		assert !m.isOnRoad(cg);
		assert !m.isOnRail(cg);
		assert m.isOnSeaRoute(cg);
		
		// Venice
		String ve = "VE";
		assert m.getAdjacentFor(ve, EnumSet.allOf(TravelBy.class)).size() == 6;
		assert m.getAdjacentFor(ve, EnumSet.of(TravelBy.road)).size() == 4;
		assert m.getAdjacentFor(ve, EnumSet.of(TravelBy.rail)).size() == 1;
		assert m.getAdjacentFor(ve, EnumSet.of(TravelBy.sea)).size() == 1;
		assert m.isOnRail(ve);
		assert m.isOnRoad(ve);
		assert m.isOnSeaRoute(ve);
		
		assert !m.isAtSea(ve);
		assert m.isAtSea("NS");
		
		// Distance between
		// Bordeaux and Frankfurt, rail: 3, road: 4
		assert m.getMinDistanceBetween("BO", "FR") == 3;
		assert m.getMinDistanceBetween("VE", "VI") == 1;
		
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
