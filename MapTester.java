import java.net.*;


public class MapTester {

	public static void main(String[] args) {
		GameMap m = new GameMap();
		
		URL landLocations = m.getClass().getResource("/maps/landLocations.txt");
		URL seaLocations = m.getClass().getResource("/maps/seaLocations.txt");
		
		URL road = m.getClass().getResource("/maps/road.txt");
		URL rail = m.getClass().getResource("/maps/rail.txt");
		URL sea = m.getClass().getResource("/maps/sea.txt");
		
		m.init(landLocations.getPath(), seaLocations.getPath());
		m.loadMaps(road.getPath(), rail.getPath(), sea.getPath());
	}
}
