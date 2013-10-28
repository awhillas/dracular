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
    public Move getDracBestMove(String dracLocation) {
        String next;
        switch (dracLocation) {
            case "CD":
                next = "KL";
                break;
            case "KL":
                next = "SZ";
                break;
            case "SZ":
                next = "BE";
                break;
            case "BE":
                next = "SO";
                break;
            case "SO":
                next = "BC";
                break;
            case "BC":
                next = "GA";
                break;
            case "GA":
                next = "CD";
                break;
            default:
                next = "SZ";
                break;
        }
        return new Move(next);
    }
}
