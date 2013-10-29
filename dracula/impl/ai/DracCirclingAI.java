package dracula.impl.ai;

import dracula.impl.*;

/**
 * @author JEM
 * 
 * Very simple drac AI, just sends drac around in a circle (it is impossible for these moves to be illegal
 * since they will never be in the trail by the time drac catches up. Periodically passes through castle
 * dracula to regenerate some health
 */
public class DracCirclingAI {
    public static String getDracBestMove(String dracLocation) {
        
        if (dracLocation == "CD")
        	return "KL";
        
        if (dracLocation == "KL")
            return "SZ";
                
        if (dracLocation == "SZ")
        	return "BE";
                
        if (dracLocation == "BE")
        	return "SO";
        
        if (dracLocation == "SO")
        	return "BC";

        if (dracLocation == "BC")
        	return "GA";
                
        if (dracLocation == "GA")
        	return "CD";
                
         return "SZ";
    }
}
