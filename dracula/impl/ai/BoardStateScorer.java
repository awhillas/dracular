package dracula.impl.ai;

import java.util.List;

import dracula.impl.*;

/**
 * @author JEM
 */

public class BoardStateScorer {
    /*
     * takes into account how many options a current state has
     * as well as drac health
     * as well as hunter health (average)
     * as well as distance from hunters (average)
     * and generates a score for the state
     * 
     * will need to put some effort into determining optimal scores
     * ideally we could test our dracula against a set of  random hunters
     * and use different weights
     */
    
    private static BoardState state;
    private static int hDWeight = 100;
    private static int dHWeight = 50;
    private static int hHWeight = -100;
    private static int aMWeight = 100;
    private static int hCWeight = -200;
    
    
    private static int avHuntDist;
    private static int dracHealth;
    private static int avHuntHealth;
    private static int availMoves;
    private static int huntCollisions;

    /*
     * returns the score for the given boardState
     */
//    public static int getScore(BoardState bs) {
//        state = bs;
//        update();
//        return avHuntDist * hDWeight + dracHealth * dHWeight + avHuntHealth * hHWeight + availMoves * aMWeight + huntCollisions * hCWeight;
//    }
    
    /**
     * Calculate the weighted harmonic mean of the distances.
     * Harmonic mean penalizes the average if dracula is too close to any of the hunters
     * and is maximized when distances to all hunters are maximized. 
     * However, if a hunter's health is low, even if dracula is very close to it, 
     * it may still give a higher mean. 
	 *
     */
    public static double getScore(BoardState state) {
    	double sum = 0.0;
    	int[] distancesToHunters = state.getHunterDistances();
    	int[] huntersHealths = state.getHunterHealth();
    	
    	for (int i = 0; i < distancesToHunters.length; i++) {
    		// Distance.
    		int d = distancesToHunters[i];
    		
    		// Health.
    		int h = huntersHealths[i];			// Assuming state.getHunterDistances() has the same sequence as state.getHunterHealth()
    		
    		sum += 1.0 / (1 + (d * (h/9)));
    	}
    	//bonus for moving to CD
        double bonus = 0.0;
        if (state.getDracula().getLocation().contains("CD")){
            bonus = 1.0;
        }
    	// Harmonic Mean + bonus
    	double mean = ((double)distancesToHunters.length) / sum + bonus;
    	return mean;
    }
    
    /*
     * updates the static fields of the BoardStateScorer
     */
     private static void update() {
        setAverageHunterDist();
        setAverageHunterHealth();
        dracHealth = state.getDracHealth();
        availMoves = state.getLegalMoves().length;
    }

    /*
     * sets the average hunter distances and counts collisions
     */
    private static void setAverageHunterDist() {
        int[] distances = state.getHunterDistances();
        int sum = 0;
        huntCollisions = 0;
        for (int i = 0; i < distances.length; i++) {
            sum += distances[i];
            if (distances[i] == 0) {
                huntCollisions++;
            }
        }
        avHuntDist = sum / distances.length;
    }

    /*
     * sets the average hunter health
     */
    private static void setAverageHunterHealth() {
        int[] healths = state.getHunterHealth();
        int sum = 0;
        for (int i = 0; i < healths.length; i++) {
            sum += healths[i];
        }
        avHuntHealth = sum / healths.length;
    }
}
