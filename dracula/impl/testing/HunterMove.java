package dracula.impl.testing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import dracula.impl.Game;
import dracula.impl.Hunter;
import dracula.impl.TravelBy;
import dracula.impl.map.Location;

public class HunterMove {
	private Game game;
	private Hunter hunter;
	private List<String> options;
	private List<String> nonOptions;
	private String moveType;
	
	public HunterMove(Hunter hunter, String move, Game game) {
		this.game = game;
		this.hunter = hunter;
		this.moveType = move;
		
	}
	
	public int legalByRail() {
		int sum = hunter.getNumber()+game.getRound();
		if (sum % 4 == 0) {
			return 0;
		} else if (sum % 4 == 1) {
			return 1;
		} else if (sum % 4 == 2) {
			return 2;
		} else if (sum % 4 == 3) {
			return 3;
		}
		return 0;
	}
	
	public String hunterMove() {
		
		List<Location> optionLocs = game.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea, TravelBy.rail));
		
		options = new ArrayList<String>();
		
		for (Location loc : optionLocs) {
			options.add(loc.getName());
		}
		nonOptions = new ArrayList<String>();
		
		nonOptions.add("CD");
		String newLocation = "";
		
		if (moveType.contains("SEARCH")) {
			// TODO
			newLocation = options.get(0);
		} else if (moveType.contains("RANDOM")) {
			Random random = new Random();
			newLocation = options.get(random.nextInt(options.size()));
		}
		
		String actions = getEncounters(newLocation);
		return hunter.getHunter()+newLocation+actions;
	}
	

	public Hunter getHunter() {
		return hunter;
	}
	
	public String getEncounters(String location) {
		String actions = "....";
		List<Location> trail = this.game.getDracula().getTrail();
		int i = 0;
		if (trail.get(i).getName().contains(location)) {
			actions = trail.get(i).getLocationEncounters();
			
		}
		if (this.game.getDracula().getLocation().getName().contains(location)) {
			actions += "D";
		} else {
			actions += ".";
		}
		return actions;
	}
	
	public String insults() {
		String messages = "";
		
		return messages;
	}
 	
}
