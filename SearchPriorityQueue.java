import java.util.ArrayList;

/**
 * @author jemu057
 */

//class to manage the queues of nodes to be expanded for A* and breadth first searchs
//  -for breath first priority is given as 0 (since nodes are expanded in order of appearance)
//  -path argument for add function can be null if path is irrelevant (as when calculating distances)
public class SearchPriorityQueue {

    //stores the list of keys, with those with lower indices being expanded first
    private ArrayList<String> keys;
    //stores the priorities - this list correspons with keys - they should always be the same length
    private ArrayList<Integer> priorities;
    //store the path to reach the current key
    private ArrayList<ArrayList<String>> paths;
    //stores all keys that have already been expanded to avoid loops in the search
    private ArrayList<String> formerKeys;

    //constructor
    public SearchPriorityQueue() {
        this.keys = new ArrayList<String>();
        this.priorities = new ArrayList<Integer>();
        this.paths = new ArrayList<ArrayList<String>>();
        this.formerKeys = new ArrayList<String>();
    }

    //to check a key(i.e. a node) is not already in the queue
    public boolean alreadyQueued(String key) {
        if ((this.keys.contains(key)) || (this.formerKeys.contains(key))) {
            return true;
        } else {
            return false;
        }
    }

    //this list stores the element with the lowest priority at the fron
    public void add(String key, int priority, ArrayList<String> path) {
        for (int i = 0; i <= this.priorities.size(); i++) {
            if (i == this.priorities.size()) {
                this.priorities.add(priority);
                this.keys.add(key);
                if (path != null) {
                    this.paths.add(path);
                }
                break;
            } else {
                if ((this.priorities.get(i)) > priority) {
                    this.priorities.add(i, priority);
                    this.keys.add(i, key);
                    if (path != null) {
                        this.paths.add(path);
                    }
                    break;
                }
            }
        }
    }

    //remove and return the next element in the queue
    public ArrayList<String> getNextPath() {
        return this.paths.get(0);
    }

    //removes the node next to be evaluated from the queue
    public String deQueue() {
        this.priorities.remove(0);
        if (this.paths.size() > 0) {
            this.paths.remove(0);
        }
        String key = this.keys.remove(0);
        this.formerKeys.add(key);
        return key;
    }

    //test if there are any elements currently in the queue
    public boolean isEmptyQueue() {
        return this.keys.isEmpty();
    }
}
