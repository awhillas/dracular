/**
 * Written for COMP9024 2013s2.
 * @author adwi001
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class GameData {
	ArrayList<MoveData> MovesData = new ArrayList<MoveData>(); 
	MoveData[] DraculaTrail = new MoveData[6];
	//Queue<MoveData> DraculaTrail;
	HashMap<String, HunterPlayer> HuntersData = new HashMap<String, HunterPlayer>();
	HashMap<String, LocationData> MapData = new HashMap<String, LocationData>();
	DraculaPlayer dracula;
	String[] playerMoves;
	
	MoveData move;
	boolean vampire;
	int turn;
	int round;
	int score;

	public GameData() {
		
		turn = 0;
		round = 0;
		score = 300;
		vampire = false;
		
		//Get the default map data from the graph inputs
		//BuildMap(MapData);
		
		HuntersData.put("G", new HunterPlayer("Lord Godalming"));
		HuntersData.put("S", new HunterPlayer("Dr Seward"));
		HuntersData.put("H", new HunterPlayer("Van Helsing"));
		HuntersData.put("M", new HunterPlayer("Mina Harker"));
		
		dracula = new DraculaPlayer();
	}

	
	public void doString(String pastPlays) {
		playerMoves = pastPlays.split(" ");
		
		//String lastPlay = playerMoves[playerMoves.length-1];
		for (int i = 0; i < playerMoves.length; ++i) {
			if (turn == 5) {
				turn = 0;
				round++;
			}
			turn++;
			String player = playerMoves[i].substring(0, 1);
			move = new MoveData(playerMoves[i]);
			if (player.contains("D")) {
				dracula.updateFromMove(this);
				DraculaPlayer newDracula = new DraculaPlayer();
				move.player = newDracula.copyFromLast(dracula);
				DraculaTrail[round % 6] = move;
			} else {
				HuntersData.get(player).updateFromMove(this);
				HunterPlayer newHunter = new HunterPlayer(HuntersData.get(player).name);
				move.player = newHunter.copyFromLast(HuntersData.get(player)); 
			}
			MovesData.add(move);
		}
	}
	
	public void doMessages(String[] messages) {
		
	}
}