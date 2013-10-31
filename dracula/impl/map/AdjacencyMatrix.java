package dracula.impl.map;

import java.util.*;

import dracula.impl.*;
import dracula.impl.ai.SearchPriorityQueue;

/**
 * Adjacency Matrix for an undirected graph i.e. matrix is symmetrical.
 *
 * @author arbw870, jemu057
 *
 */
public class AdjacencyMatrix implements Graph {

    /**
     * Vertex list
     */
    private String[] vertices;
    /**
     * Matrix of Edges. 
     *  -value of 1 for adjacent nodes
     *  -higher values indicate min distance between nodes (used for A* heuristic)
     *  -0 indicates two nodes are not connected by edges at all
     */
    private int[][] edges;

    public AdjacencyMatrix(String[] nodes, int[][] edges) {
        this.vertices = nodes;
        this.edges = edges;
        this.updateMinDistances();
    }
    
    /*
     * combine two adjacency matrixes - primarily for min dist calculation
     * can also be used for search
     */
    public static AdjacencyMatrix combine(AdjacencyMatrix one, AdjacencyMatrix two){
        int overlap = 0;
        ArrayList<Integer> uniques = new ArrayList<Integer>();
        for (int i = 0; i < one.vertices.length; i++){
            for (int j = 0; j < two.vertices.length; j++){
                if (one.vertices[i].equals(two.vertices[j])){
                    overlap++;
                }
                else {
                    uniques.add(j);
                }
            }
        }
        
        int combSize = one.vertices.length + two.vertices.length - overlap;
        String[] vertices = new String[combSize];
        int[][] edges = new int[combSize][combSize];
        
        //top right corner of array is same as one
        for (int i = 0; i < one.vertices.length; i++){
            vertices[i] = one.vertices[i];
            for (int j = 0; j < one.vertices.length; j++){
                edges[i][j] = one.edges[i][j];
            }
        }
        
        //remainder is built of the unique vertices in two
        for (int i = one.vertices.length; i < vertices.length; i++){
            int vertex = uniques.remove(0);
            vertices[i] = two.vertices[vertex];
            for (int j = 0; j < vertices.length; j++){
                if (vertices[j] != null){
                    int two_idx;
                    if ((two_idx = indexOf(vertices[j], two.vertices)) != -1){
                        edges[i][j] = two.edges[vertex][two_idx];
                        edges[j][i] = two.edges[vertex][two_idx];
                    }
                    else {
                        edges[i][j] = 0;
                        edges[j][i] = 0;
                    }
                }
            }
        }
        return new AdjacencyMatrix(vertices, edges);
    }

    @Override
    public int numVertices() {
        return vertices.length;
    }

    @Override
    public int numEdges() {
        int count = 0;
        for (int i = 0; i < edges.length; i++) {
            // undirected graph so mirrored about the diagonal...
            for (int j = i; j < edges[i].length; j++) {
                if (edges[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private int indexOf(String v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].compareTo(v) == 0) {
                return i;
            }
        }
        return -1;	// error
    }
    
    private static int indexOf(String v, String[] arr){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(v) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int degree(String v) {
        int index = this.indexOf(v);
        int count = 0;
        if (index != -1) {	// maybe should throw an error here?
            for (int i = 0; i < edges[index].length; i++) {
                if (edges[index][i] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    @Override
    public String[] getVertices() {
    	return this.vertices;
    }

    @Override
    public String[] adjacentVertices(String v) {
        ArrayList<String> out = new ArrayList<String>();
        int index = this.indexOf(v);
        if (index != -1) {	// maybe should throw an error here?
            for (int i = 0; i < edges[index].length; i++) {
                if (edges[index][i] == 1) {
                    out.add(vertices[i]);
                }
            }
        }
        return out.toArray(new String[0]);
    }

    @Override
    public boolean areAdjacent(String v, String w) {
        // Input checking needed here.
        return edges[indexOf(v)][indexOf(w)] == 1;
    }

    //returns the min distance between two nodes (stored in the edge array)
    /*
     * return -1 in case of failure
     */
    @Override
    public int getMinDist(String node1, String node2) {
        if ((indexOf(node1) != -1) &&(indexOf(node2) != -1)){
            return edges[indexOf(node1)][indexOf(node2)];
        }
        else {
            return -1;
        }
    }

    //this is a breath first seach to determine minimum travel distance between vertices
    private void updateMinDistances() {
        //go trhough vertices one at a time
        for (int i = 0; i < this.numVertices(); i++) {
            //create a queue to store the next vertices to be expanded
            SearchPriorityQueue queue = new SearchPriorityQueue();
            queue.add(this.vertices[i], 0, null);
            ArrayList<Integer> distances = new ArrayList<Integer>();
            distances.add(0);
            //loop until the queue is empy (there are no new nodes to expand)
            while (!(queue.isEmptyQueue())) {
                int currDist = distances.get(0) + 1;
                String next = queue.deQueue();
                distances.remove(0);
                String[] expanded = this.adjacentVertices(next);
                for (int j = 0; j < expanded.length; j++) {
                    if (!(queue.alreadyQueued(expanded[j]))) {
                        //update the minimum distance in the edges matrix
                        this.edges[i][this.indexOf(expanded[j])] = currDist;
                        this.edges[this.indexOf(expanded[j])][i] = currDist;
                        queue.add(expanded[j], 0, null);
                        distances.add(currDist);
                    }
                }
            }
        }
    }

    private HashMap<String, List<String>> namedEdges;
    
    public HashMap<String, List<String>> edgesAsReadonly() {
		// Lazy creation.
    	if (namedEdges == null) {
    		namedEdges = new HashMap<String, List<String>>();
    		
    		for (int i = 0; i < edges.length; i++) {
    			for (int j = 0; j < edges.length; j++) {
    				if (edges[i][j] == 1) {
    					String vi = this.vertices[i];
    					String vj = this.vertices[j];
    					
    					// Edge already added under j?
    					if (namedEdges.containsKey(vj) && namedEdges.get(vj).contains(vi)) {
    						// Do nothing.
    					}
    					else {
    						// Add under i, i first time?
    						if (!namedEdges.containsKey(vi)) {
        						List<String> edge = new ArrayList<String>();
        						edge.add(vj);
        						namedEdges.put(vi, edge);
    						}
    						else {
    							// append j to i's edges
    							if (!(namedEdges.get(vi).contains(vj))) {
    								namedEdges.get(vi).add(vj);
    							}
    							else {
    								// Should never get here!
    							}
    						}
    					}
    				}
    			}
    		}
    		
    		// Make readonly.
    		Collections.unmodifiableMap(namedEdges);
    	}
    	return namedEdges;
    }
    
    @Override
    public int hashCode() {
    	int hash = 0;
    	
    	for (String v : edgesAsReadonly().keySet()) {
    		int hv = v.hashCode(); // Hash of v
    		for (String e : edgesAsReadonly().get(v)) {
    			int he = e.hashCode(); // Hash of e
    			
    			// Symmetric hashing to ensure ordering of hv and he doesn't affect result.
    			// Take both sum and product, concatenate as string 
    			String h = (hv + he) + "" + (hv * he);
    			hash += h.hashCode();
    		}
    	}
    	return Math.abs(hash);
    }
    
    @Override
    public String toString(){
        String s = "";
        s += "\t";
        for (int i = 0; i < this.numVertices(); i++){
            s += this.vertices[i] + "\t";
        }
        s += "\n";
        for (int i = 0; i < this.numVertices(); i++){
            s += this.vertices[i] + "\t";
            for (int j = 0; j < this.numVertices(); j++){
                s += this.edges[i][j] + "\t";
            }
            s += "\n";
        }
        return s;
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

        assert graph.numEdges() == 7 : "Grpah should have 7 edges found " + graph.numEdges();
        assert graph.numVertices() == 6 : "Graph should have 6 vertices, found " + graph.numVertices();

        assert graph.areAdjacent("A", "B");
        assert graph.areAdjacent("A", "C");
        assert graph.areAdjacent("A", "D");
        assert graph.areAdjacent("C", "B");
        assert graph.areAdjacent("D", "E");
        assert graph.areAdjacent("D", "F");

        assert graph.degree("A") == 3;
        assert graph.degree("B") == 3;
        assert graph.degree("C") == 2;
        assert graph.degree("D") == 3;
        assert graph.degree("E") == 2;
        assert graph.degree("F") == 1;

        String[] adj = graph.adjacentVertices("A");
        assert adj.length == 3;
        assert Arrays.asList(adj).contains("B");
        assert Arrays.asList(adj).contains("C");
        assert Arrays.asList(adj).contains("D");

        adj = graph.adjacentVertices("B");
        assert adj.length == 3;
        assert Arrays.asList(adj).contains("A");
        assert Arrays.asList(adj).contains("C");
        assert Arrays.asList(adj).contains("E");

        adj = graph.adjacentVertices("C");
        assert adj.length == 2;
        assert Arrays.asList(adj).contains("A");
        assert Arrays.asList(adj).contains("B");

        adj = graph.adjacentVertices("D");
        assert adj.length == 3;
        assert Arrays.asList(adj).contains("A");
        assert Arrays.asList(adj).contains("E");
        assert Arrays.asList(adj).contains("F");

        adj = graph.adjacentVertices("E");
        assert adj.length == 2;
        assert Arrays.asList(adj).contains("B");
        assert Arrays.asList(adj).contains("D");

        adj = graph.adjacentVertices("F");
        assert adj.length == 1;
        assert Arrays.asList(adj).contains("D");
        
        assert graph.getMinDist("A", "B") == 1;
        assert graph.getMinDist("A", "C") == 1;
        assert graph.getMinDist("A", "D") == 1;
        assert graph.getMinDist("A", "E") == 2;
        assert graph.getMinDist("A", "F") == 2;
        
        assert graph.getMinDist("B", "A") == 1;
        assert graph.getMinDist("B", "C") == 1;
        assert graph.getMinDist("B", "D") == 2;
        assert graph.getMinDist("B", "E") == 1;
        assert graph.getMinDist("B", "F") == 3;
        
        //could add more og these assertions but seems to be working

        System.out.println("PASSED all tests!");
    }
}
