import java.net.*;
import java.util.ArrayList;
import java.util.EnumSet;


public class MapTester {

	public static void main(String[] args) {
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
