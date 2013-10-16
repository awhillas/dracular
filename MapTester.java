import java.net.*;
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
		assert m.getAdjacentLocations("MA", EnumSet.allOf(TravelBy.class)).size() == 10;
		assert m.getAdjacentLocations("MA", EnumSet.of(TravelBy.road)).size() == 6;
		assert m.getAdjacentLocations("MA", EnumSet.of(TravelBy.rail)).size() == 4;
		assert m.getAdjacentLocations("MA", EnumSet.of(TravelBy.sea)).size() == 0;
		
		// Cagliari
		assert m.getAdjacentLocations("CG", EnumSet.allOf(TravelBy.class)).size() == 2;
		assert m.getAdjacentLocations("CG", EnumSet.of(TravelBy.road)).size() == 0;
		assert m.getAdjacentLocations("CG", EnumSet.of(TravelBy.rail)).size() == 0;
		assert m.getAdjacentLocations("CG", EnumSet.of(TravelBy.sea)).size() == 2;
				
		// Venice
		assert m.getAdjacentLocations("VE", EnumSet.allOf(TravelBy.class)).size() == 6;
		assert m.getAdjacentLocations("VE", EnumSet.of(TravelBy.road)).size() == 4;
		assert m.getAdjacentLocations("VE", EnumSet.of(TravelBy.rail)).size() == 1;
		assert m.getAdjacentLocations("VE", EnumSet.of(TravelBy.sea)).size() == 1;
		
		assert !m.isLocationAtSea("VE");
		assert m.isLocationAtSea("NS");
	}
}
