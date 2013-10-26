package dracula.impl.testing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import dracula.impl.Game;
import dracula.impl.Hunter;
import dracula.impl.TravelBy;
import dracula.impl.ai.PathFinder;
import dracula.impl.map.AdjacencyMatrix;
import dracula.impl.map.GameMap;
import dracula.impl.map.Location;
import dracula.impl.map.Map;

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
	
	public List<Location> legalByRail() {
		int sum = hunter.getNumber()+game.getRound();
		List<Location> optionTrainLocs = new ArrayList<Location>();
		if (sum % 4 == 1) {
			//level 1
			optionTrainLocs = game.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
		} else if (sum % 4 == 2) {
			//level 1
			optionTrainLocs = game.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
			//level 2
			for (Location loc : optionTrainLocs) {
				optionTrainLocs.addAll(game.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
		} else if (sum % 4 == 3) {
			//level 1
			optionTrainLocs = game.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
			//level 2
			for (Location loc : optionTrainLocs) {
				optionTrainLocs.addAll(game.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
			//level 3
			for (Location loc : optionTrainLocs) {
				optionTrainLocs.addAll(game.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
		}
		return optionTrainLocs;
	}
	
	public String hunterMove() {
		
		List<Location> optionLocs = game.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
		optionLocs.addAll(legalByRail());
		
		options = new ArrayList<String>();
		for (Location loc : optionLocs) {
			options.add(loc.getName());
		}
		nonOptions = new ArrayList<String>();
		
		//Dracula Castle isn't allowed
		nonOptions.add("CD");
		String newLocation = "";
		
		/*
		 * Build the hunters move, either random mover or via the pathfinder
		 */
		if (moveType.contains("SEARCH")) {
			newLocation = hunterSearchMove();
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
	
	public String hunterSearchMove() {
        Map m = new GameMap();
        ArrayList<String> path = m.getRoute(hunter.getLocation().getName(), 
        		game.getDracula().getLocation().getName(), new ArrayList<String>(), TravelBy.road);
        return path.get(0);
	}
	
	public String getEncounters(String location) {
		String actions = "....";
		List<Location> trail = this.game.getDracula().getTrail();
		int i = 0;
		if (!trail.isEmpty()) {
			if (trail.get(i).getName().contains(location)) {
				actions = trail.get(i).getLocationEncounters();
			}
			if (this.game.getDracula().getLocation().getName().contains(location)) {
				actions += "D";
			} else {
				actions += ".";
			}
		}
		
		return actions;
	}
	
	public String insults() {
		String messages = "";
		
		return messages;
	}
 	
}
