package dracula.impl.ai;

import java.util.*;
import dracula.impl.map.*;

/**
 * @author jemu057
 */
public class PathFinder {
    
    //returns path between two nodes, or returns an empty arrayList if no path exist
    public static ArrayList<String> getPath(String start, String finish, List<String> avoid, Graph graph) {
        //initialise the path as empty
        ArrayList<String> path = new ArrayList<String>();
        SearchPriorityQueue queue = new SearchPriorityQueue();
        queue.add(start, graph.getMinDist(start, finish), path);
        
        //implementation of A* follows - though much of the work is done by the SearchPriorityQueue class
        //begin expanding and dequeuing until either we run out of nodes or we have our path
        //nodes are expanded into a priority queue, such that the min distance heuristic is used to decide which node is next.
        //never expand an avoid node (check before each expansion)
        //each node in the queue will be associated with a unique path (i.e. arrayList of string - copied from the node from which it was expanded)
        boolean goalFound = false;
        while (!(queue.isEmptyQueue())) {
            path = queue.getNextPath();
            String next = queue.deQueue();
            path.add(next);
            
            //check if we have found our goal
            if (next.equals(finish)){
                goalFound = true;
                break;
            }
            
            //if not expand the current node and add the resultant nodes to the queue
            String[] expanded = graph.adjacentVertices(next);
            for (int i = 0; i < expanded.length; i++) {
                if ((!(queue.alreadyQueued(expanded[i]))) && (!(avoid.contains(expanded[i])))) {
                    queue.add(expanded[i], graph.getMinDist(expanded[i], finish), path);
                }
            }
        }
        //only return the path if we found the goal
        if (goalFound){
            return path;
        }
        //otherwise we ran out of nodes to evaluate, just return an empty ArrayList
        else {
            return new ArrayList<String>();
        }
    }
    
    public static void main(String[] args) {
        // Testing
        // For a visual see: http://courses.cs.vt.edu/csonline/DataStructures/Lessons/Graphs/graph.gif
        String[] nodes = {"A", "B", "C", "D", "E", "F"};
        // this should be symmetrical about the diagonal
        int[][] matrix = {{0, 1, 1, 1, 0, 0},
            {1, 0, 1, 0, 1, 0},
            {1, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 1},
            {0, 1, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0}};

        AdjacencyMatrix graph = new AdjacencyMatrix(nodes, matrix);
        
        ArrayList<String> path = getPath("A", "B", new ArrayList<String>(), graph);
        assert path.size() == 2;
        assert path.get(0).equals("A");
        assert path.get(1).equals("B");
        
        path = getPath("A", "F", new ArrayList<String>(), graph);
        assert path.size() == 3;
        assert path.get(0).equals("A");
        assert path.get(1).equals("D");
        assert path.get(2).equals("F");
        
        path = getPath("F", "C", new ArrayList<String>(), graph);
        assert path.size() == 4;
        assert path.get(0).equals("F");
        assert path.get(1).equals("D");
        assert path.get(2).equals("A");
        assert path.get(3).equals("C");
        
        ArrayList<String> avoid = new ArrayList<String>();
        avoid.add("A");
        path = getPath("F", "C", avoid, graph);
        assert path.size() == 5;
        assert path.get(0).equals("F");
        assert path.get(1).equals("D");
        assert path.get(2).equals("E");
        assert path.get(3).equals("B");
        assert path.get(4).equals("C");

        System.out.println("passed all tests!");
    }
}
