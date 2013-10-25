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
    public BoardState getNextState(Move move);
    
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
}
