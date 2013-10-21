package dracula;

import java.util.*;

/**
 * @author arbw870
 *
 */
public interface Graph {
	
	public int numVertices();	
	
	public int numEdges();
	
	public int getMinDist(String node1, String node2);
	
	/**
	 * @param 	v	Vertex
	 * @return	The degree of the vertex v
	 */
	public int degree(String v);
	
	/**
	 * @param 	v	Vertex
	 * @return	Array of Vertices adjacent (connected) to v
	 */
	public String[] adjacentVertices(String v);

	/**
	 * @param 	v	Vertex
	 * @param 	w	Vertex
	 * @return	True if the two vertices are adjacent, false otherwise.
	 */
	public boolean areAdjacent(String v, String w);
	
	/**
	 * Gets a read-only map of the edges in the graph. Edges are un-directed and each appears only once. 
	 * Key: a vertex. 
	 * Value: vertices immediately connected to the keyed vertex.
	 */
	public HashMap<String, List<String>> edgesAsReadonly();
}
