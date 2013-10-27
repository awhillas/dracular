package dracula.impl.testing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import dracula.Encounter;
import dracula.Player;
import dracula.impl.Board;
import dracula.impl.DraculaTrail;
import dracula.impl.TravelBy;
import dracula.impl.map.GameMap;
import dracula.impl.map.Map;

public class HunterMove {

	private Board board;
	private Player hunter;
	private List<String> options;
	private List<String> nonOptions;
	private String moveType;
	private String encounters;
	
	public HunterMove(Player hunter, String move, Board board) {
		this.board = board;
		this.hunter = hunter;
		this.moveType = move;
		
	}
	
	public List<String> legalByRail() {
		int sum = hunter.getNumber()+board.getRound();
		List<String> optionTrainLocs = new ArrayList<String>();
		if (sum % 4 == 1) {
			//level 1
			optionTrainLocs = board.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
		} else if (sum % 4 == 2) {
			//level 1
			optionTrainLocs = board.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
			//level 2
			for (String loc : optionTrainLocs) {
				optionTrainLocs.addAll(board.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
		} else if (sum % 4 == 3) {
			//level 1
			optionTrainLocs = board.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.rail));
			//level 2
			for (String loc : optionTrainLocs) {
				optionTrainLocs.addAll(board.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
			//level 3
			for (String loc : optionTrainLocs) {
				optionTrainLocs.addAll(board.getMap().getAdjacentFor(loc, EnumSet.of(TravelBy.rail)));
			}
		}
		return optionTrainLocs;
	}
	
	public String hunterMove() {
		
		List<String> optionLocs = board.getMap().getAdjacentFor(hunter.getLocation(), EnumSet.of(TravelBy.road, TravelBy.sea));
		//optionLocs.addAll(legalByRail());
		
		options = new ArrayList<String>();
		for (String loc : optionLocs) {
			options.add(loc);
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
		
		this.encounters = getEncounters(newLocation);
		return hunter.getName()+newLocation+encounters;
	}
	

	public Player getHunter() {
		return hunter;
	}
	
	public String hunterSearchMove() {
            if (hunter.getLocation().equals(board.getDracula().getLocation())){
                return hunter.getLocation();
            }
            else {
                Map m = new GameMap();
                ArrayList<String> path = m.getRoute(hunter.getLocation(), 
                                board.getDracula().getLocation(), new ArrayList<String>(), TravelBy.road);
                return path.get(1);
            }
	}
	
	public String getEncounters(String location) {
		String[] events = {".",".",".","."};
		DraculaTrail trail = board.getDracula().getTrail();
		if (trail.getLength() > 0) {
			Encounter[] nasties = trail.getEncountersAt(location);
			if (nasties.length > 0) {
				for (Encounter e : nasties) {
					if (e.isTrap()) {
						if (events[0].contains("T")) {
							events[1] = "T";
						} else {
							events[0] = "T";
						}
					} else if (!e.isTrap()) {
						events[2] = "V";
					} 
				}
			}
		}
		if (board.getDracula().getLocation().equals(location)) {
			events[3] = "D";
		} 
		String actions = "";
		for (String e : events)
			actions += e;

		return actions;
	}
	
	public String Encounters() {
		return this.encounters;
	}
	
	
	public String insults() {
		String messages = "";
		
		return messages;
	}
	
	
 	
}
