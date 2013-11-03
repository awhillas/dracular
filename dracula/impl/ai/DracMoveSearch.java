package dracula.impl.ai;

import dracula.impl.*;
import java.util.*;

/**
 *
 * @author JEM
 */
public class DracMoveSearch {
    /*
     * ideally performs a breadth first search given a certain depth to find the best move
     * (i.e. the move that leads to the best set of states (average score of set))
     */
     
    /*
     * currently search depth is only one
     * if we assume 5 possible moves for each of 5 playes
     * expanding one state with lead to 5^5 = ~3000 new states
     * If depth was 2 then we would be looking at about 10,000,000 states
     * For searching any deeper than 2 we would definitely need some way of pruning
     */

    static Move[] dracMoves;
    static List<List<Move>> hunterMoves;
    static int[] hunterMovesIdx = new int[4];

    
    public static Move getBestMove(BoardState state) {
       List<Move> options = new ArrayList<Move>();
        //check if first move
        if (state.getTurn() == 4) {
            List<String> cities = state.getMap().getCities();
            for (String s : cities){
                options.add(new Move(s));
            }
        } //if health below certain level then go to castle dracula
        else if (state.getDracHealth() < 60) {
            String dLoc = state.getDracula().getLocation();
            List<String> trail = state.getDracula().getTrail().getLocations();
            List<String> hunters = state.getHunterLocations();
            List<String> avoid = new ArrayList<String>();
            avoid.addAll(trail);
            avoid.addAll(hunters);
            avoid.add("JM");
            //first try a path back to "CD" that avoids moving into a hunter
            List<String> path = state.getMap().getRoute(dLoc, "CD", avoid, TravelBy.roadAndSea);
            if (path.size() >= 2) {
                options.add(new Move(path.get(1)));
            } else {
                avoid = new ArrayList<String>();
                avoid.addAll(trail);
                avoid.add("JM");
                //if no valid path then just try any path back to CD
                path = state.getMap().getRoute(dLoc, "CD", trail, TravelBy.roadAndSea);
                if (path.size() >= 2) {
                    options.add(new Move(path.get(1)));
                }
            }
        }
        //check if anything has been added to options
        if (options.isEmpty()) {
            Move[] dracMoves = state.getLegalMoves();
            if (dracMoves.length == 0){
            }
            for (int i = 0; i < dracMoves.length; i++) {
                options.add(dracMoves[i]);
            }
        }
        if (options.isEmpty()){
            options.add(new Move("TP"));
        }
        //search for best move in options
        Move best = options.get(0);
        state.getDracula().setLocation(best.getLocation());
        double bestScore = BoardStateScorer.getScore(state);
        for (Move m : options) {
            state.getDracula().setLocation(m.getLocation());
            if (BoardStateScorer.getScore(state) > bestScore) {
                bestScore = BoardStateScorer.getScore(state);
                best = m;
            }
        }
        return best;
        
        
        /*
        dracMoves = state.getLegalMoves();
        hunterMoves =  state.getHunterMoves();

        Move bestMove = dracMoves[0];
        int bestAvScore = 0;

        //test each possible move for dracula
        for (int i = 0; i < dracMoves.length; i++) {
            double avScore = 0;
            int statesEval = 0;
            initMoveComb();
            
            do {
                Move[] nextHunterMoves = new Move[4];
                for (int j = 0; j < 4; j++) {
                    //write the new set of hunter moves
                    nextHunterMoves[j] = hunterMoves.get(j).get(hunterMovesIdx[j]);
                }
                //calculate the average score for all states evaluated so far
                avScore = ((avScore * (statesEval / (statesEval + 1))) + (BoardStateScorer.getScore(state.getNextState(dracMoves[i], nextHunterMoves)) / (statesEval + 1)));
                statesEval++;
            } while (nextMoveComb());
            //if average score is better than the best average score then this is the move to make
            if (avScore > bestAvScore) {
                bestMove = dracMoves[i];
            }
        }
        return bestMove;
        */
    }
    

    /*
     * initialise the move combination (a set of indices for the available moves for each of the players
     */
    private static void initMoveComb() {
        for (int i = 0; i < hunterMovesIdx.length; i++) {
            hunterMovesIdx[i] = 0;
        }
    }

    /*
     * set the next move combinataion for hunters
     * AND test if all combinations have been trialled
     * will return false when finished
     */
    private static boolean nextMoveComb() {
        for (int i = 0; i < hunterMovesIdx.length; i++) {
            if (hunterMovesIdx[i] == hunterMoves.get(i).size() - 1) {
                hunterMovesIdx[i] = 0;
            } else {
                hunterMovesIdx[i]++;
                return true;
            }
        }
        return false;
    }
}
