package dracula.impl.ai;
import dracula.*;

/**
 *
 * @author JEM
 */
public class DumbMove implements DraculaMove {
    private String playAsString;
    private String message = "hahahaha";
    
    public DumbMove (String play){
        this.playAsString = play;
    }
    
    public String getPlayAsString(){
        return this.playAsString;
    }

   public String getMessage(){
       return this.message;
   }
}
