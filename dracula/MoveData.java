package dracula;
/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

public class MoveData {
	String play;
	Player player;
	String location;
	String events;
	
	public MoveData(String play) {
		this.play = play;
		this.location = play.substring(1, 3);
		this.events = play.substring(3);
	}
}
