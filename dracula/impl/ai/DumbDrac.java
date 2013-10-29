package dracula.impl.ai;

import dracula.*;
import dracula.impl.ai.*;

/**
 *
 * @author JEM
 */
public class DumbDrac implements Dracula {
    private DumbMove move = new DumbMove("CD");
    
    public DraculaMove decideMove(){
        this.move = new DumbMove(DracCirclingAI.getDracBestMove(this.move.getPlayAsString()));
        return this.move;
    }
}
