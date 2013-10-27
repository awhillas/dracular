package dracula.impl;
import dracula.impl.map.*;

/**
 * @author JEM
 */
public interface BoardState {
    /*
     * Returns new BoardState if Dracula had made a certain move
     * Assume hunters will always move closer to dracula (worst case).
     */
    public BoardState getNextState(Move dracMove, Move[] hunterMoves);
    
    /*
     * Returns Dracula's current health in current board state
     */
    public int getDracHealth();
    
    /*
     * returns an array of hunters health in current board state
     */
    public int[] getHunterHealth();
    
    /*
     * Returns a list of legal moves for Dracula to make in current board state
     */
    public Move[] getLegalMoves();
    
    
    /*
     * Returns a 2D array of the moves available for each hunter
     */
    public Move[][] getHunterMoves();
    
    /*
     * returns array of current min dist between drac and hunters (from adjacency matrix)
     * need a way of including sea and land based moves into same distance calc 
     */
    public int[] getHunterDistances();

	BoardState getNextState(Move move);
}
