import java.util.ArrayList;
import java.util.Arrays;

/**
 * Adjacency Matrix for an undirected graph i.e. matrix is symmetrical.
 * @author arbw870
 *
 */
public class AdjacencyMatrix implements Graph {

	/**
	 * Vertex list
	 */
	private String [] vertices;
	/**
	 * Matrix of Edges. If there is an edge between 2 Vertices then its cost is 
	 * recorded here, null otherwise.
	 */
	private int [][] edges;
	
	public AdjacencyMatrix(String[] nodes, int [][] edges) {
		this.vertices = nodes;
		this.edges = edges;
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
				if(edges[i][j] != 0) {
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
				if(edges[index][i] != 0) {
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
				if(edges[index][i] != 0) {
					out.add(vertices[i]);
				}
			}
		}		
		return out.toArray(new String[0]);
	}

	@Override
	public boolean areAdjacent(String v, String w) {
		// Input checking needed here.
		return edges[indexOf(v)][indexOf(w)] != 0;
	}
	
	public int getEdgeCost(String v, String w) {
		return edges[indexOf(v)][indexOf(w)];
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing
		// For a visual see: http://courses.cs.vt.edu/csonline/DataStructures/Lessons/Graphs/graph.gif
		String[] nodes = {"A", "B", "C", "D", "E", "F"};
		// this should be symmetrical about the diagonal
	    int [][] matrix= {  {0, 1, 1, 1, 0, 0}, 
							{1, 0, 1, 0, 1, 0}, 
							{1, 1, 0, 0, 0, 0}, 
							{1, 0, 0, 0, 1, 1}, 
							{0, 1, 0, 1, 0, 0}, 
							{0, 0, 0, 1, 0, 0}};
	    
	    AdjacencyMatrix graph = new AdjacencyMatrix(nodes, matrix);
	    
	    assert graph.numEdges() == 7: "Grpah should have 7 edges found "+graph.numEdges();
	    assert graph.numVertices() == 6: "Graph should have 6 vertices, found "+graph.numVertices();
	    
	    assert graph.areAdjacent("A", "B");
	    assert graph.areAdjacent("A", "C");
	    assert graph.areAdjacent("A", "D");
	    assert graph.areAdjacent("C", "B");
	    assert graph.areAdjacent("D", "E");
	    assert graph.areAdjacent("D", "E");
	    
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
	    
	    System.out.println("PASSED all tests!");
	}
}
