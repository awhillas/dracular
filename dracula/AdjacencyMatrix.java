package dracula;
import java.util.ArrayList;
import java.util.Arrays;

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
    @Override
    public int getMinDist(String node1, String node2) {
        return edges[indexOf(node1)][indexOf(node2)];
    }

    //this is a breath first seach to determine minimum travel distance between vertices
    private void updateMinDistances() {
        //go trhough vertices one at a time
        for (int i = 0; i < this.numVertices(); i++) {
            //create a queue to store the next vertices to be expanded
            SearchPriorityQueue queue = new SearchPriorityQueue();
            queue.add(this.vertices[i], 0, null);
            int currDist = 0;
            //loop until the queue is empy (there are no new nodes to expand)
            while (!(queue.isEmptyQueue())) {
                currDist++;
                String next = queue.deQueue();
                String[] expanded = this.adjacentVertices(next);
                for (int j = 0; j < expanded.length; j++) {
                    if (!(queue.alreadyQueued(expanded[j]))) {
                        //update the minimum distance in the edges matrix
                        this.edges[i][this.indexOf(expanded[j])] = currDist;
                        this.edges[this.indexOf(expanded[j])][i] = currDist;
                        queue.add(expanded[j], 0, null);
                    }
                }
            }
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
