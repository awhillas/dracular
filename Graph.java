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
}
