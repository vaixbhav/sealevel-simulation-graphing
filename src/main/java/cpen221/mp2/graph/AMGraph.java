package cpen221.mp2.graph;

import java.util.*; 

public class AMGraph<V extends Vertex, E extends Edge<V>>
        implements IGraph<V, E> {

    /**
     * Create an empty graph with an upper-bound on the number of vertices
     */

    private HashMap<Integer, V> rowKeeper;
    private ArrayList<V> vertices;
    private ArrayList<E> edges;
    private int[][]  adjacencyMatrix;
    private int maxVertices; 

    public AMGraph(int maxVertices) {
        // TODO: Implement this method
        adjacencyMatrix = new int[maxVertices][maxVertices];
        rowKeeper = new HashMap<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.maxVertices = maxVertices; 
    }

    // Rep Invariants:
    //
    // 1. Vertices List Consistency: The size of the vertices list must be
    //    equal to the number of vertices in the graph.
    // 2. Edges List Consistency: The size of the edges list must be equal to
    //    the number of edges in the graph.
    // 3. Adjacency Matrix Consistency: For each entry [i][j] in the
    //    adjacencyMatrix, the associated vertices, vertices[i] and vertices[j]
    //    must be in the vertices list.
    // 4. No Duplicate Vertices: Each vertex in the vertices list must be unique.
    // 5. No Duplicate Edges: Each edge in the edges list must be unique.
    // 6. No Null Elements: All elements (vertices and edges) in the vertices
    //                      and edges lists must not be null.

    // Abstraction function: Maps the internal state of the AMGraph class to
    //                        the abstract representation of a graph.
    //
    // Vertices: vertices list contains all vertices in the graph.
    // Edges: edges list contains all edges in the graph.
    // Adjacency Matrix: adjacencyMatrix is a 2D array where each entry
    // [i][j] represents the weight or distance between vertices vertices[i] and vertices[j].

    /**
     * Adds a vertex to the graph
     *
     * @param v vertex to be added
     * @return true if the vertex is successfully added,
     *          false if the vertex already exists in the graph
     */
    @Override
    public boolean addVertex(V v) {
        int maxOccupied = -1; 
        int vertexPosition;

        if(hasVertex(v)) return false; 
        if(this.rowKeeper.size() == this.maxVertices) return false; 



        else{
            for (Map.Entry<Integer, V> entry : this.rowKeeper.entrySet()) {
                if(entry.getKey() > maxOccupied) maxOccupied = entry.getKey(); 
            }


            vertexPosition = maxOccupied + 1; 
            this.rowKeeper.put(vertexPosition, v);
            this.vertices.add(v); 
            return true; 
        }
    }

    /**
     * Checks if the graph contains a specific vertex
     *
     * @param v the vertex to check for existence in the graph
     * @return true if the vertex is found in the graph, false otherwise
     */
    @Override
    public boolean hasVertex(V v) {
        for (Map.Entry<Integer, V> entry : this.rowKeeper.entrySet()) {
            if(v.equals(entry.getValue())) return true; 
        }
        return false; 
    }

    /**
     * Adds an edge to the graph
     *
     * @param e the edge to be added
     * @return true if the addition was successful, false if edge already exists in graph
     *         or the edge involves vertices not in the graph
     */
    @Override
    public boolean addEdge(E e) {
        V vertex1 = e.v1();
        V vertex2 = e.v2();

        int vertex1Position = getVertexPosition(this.rowKeeper, vertex1);
        int vertex2Position = getVertexPosition(this.rowKeeper, vertex2);
        
        if(!(hasVertex(vertex1) && hasVertex(vertex2))){
            return false; 
        }
        else if(this.adjacencyMatrix[vertex1Position][vertex2Position] > 0){
            return false; 
        }
        else{
            this.adjacencyMatrix[vertex1Position][vertex2Position] = 1;
            this.adjacencyMatrix[vertex2Position][vertex1Position] = 0; 
            this.edges.add(e);
            return true; 
        }

    }


    /**
     * Gets the position (key) of a vertex in a map
     * @param map the map in which to find the vertex
     * @param v the vertex for which position to obtain
     * @return the position (key) of the vertex in the map
     *          or -1 if the vertex is not found
     */
    private int getVertexPosition(HashMap<Integer, V> map, V v){

        for (Map.Entry<Integer, V> entry : map.entrySet()) {
            if(v.equals(entry.getValue())){
                return entry.getKey(); 
            } 
        }
        return -1; // If given vertex doesn't exist
    }

    /**
     * Check if the graph contains a specific edge
     *
     * @param e the edge to check for existence in the graph
     * @return true if the edge is found in the graph, false otherwise
     */
    @Override
    public boolean hasEdge(E e) {
        V vertex1 = e.v1();
        V vertex2 = e.v2();

        if(!(hasVertex(vertex1) && hasVertex(vertex2))) return false;


        
        int vertex1Position = getVertexPosition(this.rowKeeper, vertex1);
        int vertex2Position = getVertexPosition(this.rowKeeper, vertex2);

        return this.adjacencyMatrix[vertex1Position][vertex2Position] > 0 || this.adjacencyMatrix[vertex2Position][vertex1Position] > 0;
    }

    /**
     * Checks if the graph contains an edge between two vertices
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true if there is an edge between the specified vertices, false otherwise
     */
    @Override
    public boolean hasEdge(V v1, V v2) {
        if(v1.equals(v2)) return false;
        if(v1 == null || v2 == null) return false; 
        Edge<V> e = new Edge<>(v1, v2);
        return hasEdge((E) e);
    }

    /**
     * Gets the length of the edge between two vertices
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the edge between the specified vertices,
     *         or 0 if there is no such edge
     */
    @Override
    public int edgeLength(V v1, V v2) {
        int edgeLength = 0;
        for(E e : this.edges){
            if(e.v1().equals(v1) && e.v2().equals(v2)) {
                edgeLength = e.length();
            }
        }
        return edgeLength;
    }

    /**
     * Gets the sum of lengths of all edges in the graph
     *
     * @return  the sum of lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;
        for (E e : this.edges) {
            sum += e.length(); 
        }
        return sum;
    }

    /**
     * Removes an edge from the graph given a certain edge
     *
     * @param e the edge to be removed
     * @return true if the edge was successfully removed, false if the
     *         edge is not found in the graph
     */
    @Override
    public boolean removeEdge(E e) {
        V vertex1 = e.v1();
        V vertex2 = e.v2();

        int vertex1Position = getVertexPosition(this.rowKeeper, vertex1);
        int vertex2Position = getVertexPosition(this.rowKeeper, vertex2);

        if(!hasEdge(e)) return false; 

        else{
            this.adjacencyMatrix[vertex1Position][vertex2Position] = 0; 
            this.adjacencyMatrix[vertex2Position][vertex1Position] = 0; 
            this.edges.remove(e);
            return true; 
        }
        
    }

    /**
     * Removes an edge from the graph given the two end points of the edge
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true if the edge was successfully removed,
     *          false if the edge was not found in the graph
     */
    @Override
    public boolean removeEdge(V v1, V v2) {
        Edge<V> e = new Edge<>(v1, v2); 
        return removeEdge((E) e);
    }

    /**
     * Removes a vertex and its incident edges from the graph
     *
     * @param v the vertex to be removed
     * @return true if the vertex is successfully removed, false if the vertex
     *         is not found in the graph
     *
     */
    @Override
    public boolean removeVertex(V v) {

        if(!hasVertex(v)) return false; 

        else{
            int position = getVertexPosition(this.rowKeeper, v); 
            for(int i = 0; i < this.adjacencyMatrix.length; i++){
                if(hasEdge(v, this.rowKeeper.get(i))) removeEdge(v, this.rowKeeper.get(i));
            }
            this.rowKeeper.remove(position); 
            this.vertices.remove(v); 
            
            
            return true; 
        }
    }

    /**
     * Gets the edge representing the edge between two given vertices
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the edge between the specified vertices
     */
    @Override
    public E getEdge(V v1, V v2) {
        Edge<V> re = null;
        for (E e : this.edges) {
            if (e.v1().equals(v1) && e.v2().equals(v2) || e.v2().equals(v1) && e.v1().equals(v2)) {
                re = new Edge<>(e.v1(), e.v2(), e.length());
            }
        }
        return (E) re;
    }

    /**
     * Gets a set containing all vertices in the graph
     *
     * @return a set containing all vertices of the graph
     */
    @Override
    public Set<V> allVertices() {
        return new HashSet<>(this.vertices);
    }

    /**
     * Gets a set containing all edges in the graph
     *
     * @return a set containing all edges in the graph
     */
    @Override
    public Set<E> allEdges() {
        return new HashSet<>(this.edges);
    }

    /**
     * Gets a set containing all edges incident to a specific vertex in the graph
     *
     * @param v the vertex for which incident edges are to be received
     * @return a set containing all edges incident to the specified vertex
     */
    @Override
    public Set<E> allEdges(V v) {
        Set<E> toReturn = new HashSet<>();
        for (E e : this.edges) {
            if(e.incident(v)) toReturn.add(e);
        }
        return toReturn; 
    }

    /**
     * Gets collection with all neighbours of the specified vertex
     *
     * @param v the vertex for which the neighbourhood is to be received
     * @return a map where the key represents the neighbouring vertex and the value
     *         is the edge that connects the given vertex to its neighbour
     */
    @Override
    public Map<V, E> getNeighbourhood(V v) {
        Map<V, E> toReturn = new HashMap<V, E>(); 
        int position = getVertexPosition(this.rowKeeper, v); 

        for (int i = 0; i < this.adjacencyMatrix.length; i++) { // Holding v in row
            if(this.adjacencyMatrix[position][i] == 1) toReturn.put(this.rowKeeper.get(i) , (E) new Edge<>(v, this.rowKeeper.get(i)));
        }

        for (int j = 0; j < this.adjacencyMatrix.length; j++) { // Holding v in col
            if(this.adjacencyMatrix[j][position] == 1) toReturn.put(this.rowKeeper.get(j) , (E) new Edge<>(v, this.rowKeeper.get(j)));
        }

        return toReturn;
    }

    /**
     * Calculates the cost of traversing a given path based on a given type of cost metric
     *
     * @param path the path whose cost of traversal should be calculated
     * @param costType the type of cost metric
     * @return the cost of traversing the given path.
     */
    @Override
    public int pathCost(List<V> path, PathCostType costType) {
        int cost;
        switch (costType) {
            case MAX_EDGE : {
                int maxEdge = Integer.MIN_VALUE;
                E e;
                for (int i = 0; i < path.toArray().length - 1; i++) {
                    e = this.getEdge(path.get(i), path.get(i + 1));
                    maxEdge = Math.max(e.length(), maxEdge);
                }
                cost = maxEdge;
                break;
            }
            case MIN_EDGE: {
                int minEdge = Integer.MAX_VALUE;
                E e;
                for (int i = 0; i < path.toArray().length - 1; i++) {
                    e = this.getEdge(path.get(i), path.get(i + 1));
                    System.out.println(e.length());
                    minEdge = Math.min(e.length(), minEdge);
                }
                cost = minEdge;
                break;
            }
            case SUM_EDGES: {
                int sumEdges = 0;
                E e;
                for (int i = 0; i < path.toArray().length - 1; i++) {
                    e = this.getEdge(path.get(i), path.get(i + 1));
                    sumEdges += e.length();
                }
                cost = sumEdges;
                break;
            }

            default: cost = -1;
        }
        return cost;
    }

    /**
     * Finds the path of least cost between two vertices in the graph
     *
     * @param source the starting vertex of the path
     * @param sink the ending vertex of the path
     * @param costType the type of cost metric
     * @return the path of least cost between the source and the sink
     */
    @Override
    public List<V> minimumCostPath(V source, V sink, PathCostType costType) {
        return pathToSink(source, new ArrayList<>(), sink, costType);
    }

    /**
     * Finds the path of least cost between two vertices in the graph
     *
     * @param v1 the beginning vertex of the path
     * @param visitedNodes a list of all the vertices that have already been visited
     * @param sink the ending vertex of the path
     * @param costType the type of cost metric
     * @return the path of least cost between the beginning vertex and the sink
     */
    ArrayList<V>  pathToSink(V v1, ArrayList<V> visitedNodes, V sink, PathCostType costType) {
        ArrayList<V> path = new ArrayList<>(visitedNodes);

        if (visitedNodes.contains(v1)) {
            return new ArrayList<>();
        }
        boolean hasEdge = false;
        for (V v2 : this.vertices) {
            if (hasEdge(v1, v2)) {
                hasEdge = true;
                break;
            }
        }
        if(!hasEdge) {
            return new ArrayList<>();
        }
        if(v1.equals(sink)){
            path.add(v1);
            return path;
        }

        path.add(v1);

        ArrayList<ArrayList<V>> pathList = new ArrayList<>();
        for (V v2 : this.vertices) {
            if (hasEdge(v1, v2)) {
                ArrayList<V> nextPath = pathToSink(v2, path, sink, costType);
                if(nextPath.toArray().length != 0) {
                    pathList.add(nextPath);
                }
            }
        }

        ArrayList<V> minPath = new ArrayList<>();
        int minCost = Integer.MAX_VALUE;
        for(ArrayList<V> nextPath: pathList){
            int cost = pathCost(nextPath, costType);
            if( cost < minCost){
                minPath = nextPath;
                minCost = cost;
            }
        }
        path = minPath;
        return path;
    }

    /**
     * Finds all neighbours within a given range of a vertex
     * @param v
     * @param range
     * @return all vertices that are within a valid distance of vertex v. 
     */
    @Override
    public Map<V, E> getNeighbourhood(V v, int range) {
        HashMap<V, E> neighbourhood = new HashMap<>();
        getNeighbours(v, range, neighbourhood);

        return neighbourhood;
    }

    private void getNeighbours(V v, int range, Map<V, E> neighbourhood) {
        if(range == 0) {
            return;
        }
        Map<V, E> firstNeighbours = getNeighbourhood(v);

        for(Map.Entry<V,E> entry : firstNeighbours.entrySet()) {
            V neighbour = entry.getKey();
            if(!neighbourhood.containsKey(neighbour)) {
                neighbourhood.put(neighbour, getLastEdge(minimumCostPath(v, neighbour, PathCostType.SUM_EDGES)));
                getNeighbours(neighbour, range - 1, neighbourhood);
            }
        }
    }

    private E getLastEdge(List<V> verts) {
        return getEdge(verts.get(verts.size() - 2), verts.get(verts.size() - 1));
    }


    /**
     * Finds the maximum cost (given the costType metric) between 2 vertices
     *
     * @param costType the type of cost metric
     * @return the maximum <code>costType<code> cost between 2 vertices  
    */
    @Override
    public int getDiameter(PathCostType costType) {
        int diameter = Integer.MIN_VALUE;
        int cost = 0;
        List<V> path;

        for (int i = 0; i < this.vertices.size() - 1; i++) {
            for (int j = 0; j < this.vertices.size(); j++) {
                if (i == j) {
                    continue;
                }
                path = minimumCostPath(this.vertices.get(i), this.vertices.get(j), costType);
                cost = pathCost(path, costType);
                diameter = Math.max(diameter, cost);
            }
        }

        return diameter;
    }

    /**
     * Finds the center of the graph
     *
     * @param costType the type of cost metric
     * @return the vertex with the minimum eccentricity 
     */
    @Override
    public V getCenter(PathCostType costType) {
        V center = null;
        int centerCost = Integer.MAX_VALUE;
        int cost;
        List<V> path;

        for (int i = 0; i < this.vertices.size() - 1; i++) {
            for (int j = 0; j < this.vertices.size(); j++) {
                if (i == j) {
                    continue;
                }
                path = minimumCostPath(this.vertices.get(i), this.vertices.get(j), costType);
                cost = pathCost(path, costType);
                if (cost < centerCost) {
                    centerCost = cost;
                    center = this.vertices.get(i);
                }
            }
        }
        return center;
    }
}
