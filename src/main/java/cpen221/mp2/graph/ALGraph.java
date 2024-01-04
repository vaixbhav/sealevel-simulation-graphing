package cpen221.mp2.graph;

import java.util.*;

public class ALGraph<V extends Vertex, E extends Edge<V>>
        implements IGraph<V, E> {

    private ArrayList<V> vertices;
    private ArrayList<E> edges;
    private HashMap<V, ArrayList<V>> adjacencyList;


    // Rep Invariants:
    //
    // 1. Vertices List Consistency: The size of the vertices list must
    //    be equal to the number of vertices in the graph.
    // 2. Edges List Consistency: The size of the edges list must be equal
    //    to the number of edges in the graph.
    // 3. Adjacency List Consistency: For each vertex v in the adjacencyList,
    //    the list associated with v in the
    //    adjacencyList must contain only vertices that are present in the vertices list.
    // 4. No Duplicate Vertices: Each vertex in the vertices list must be unique.
    // 5. No Duplicate Edges: Each edge in the edges list must be unique.
    // 6. No Null Elements: All elements (vertices and edges) in the vertices
    //    and edges lists,
    //    and values in the adjacencyList, must not be null.


    // Abstraction function: Maps the internal state of the ALGraph class to the
    // abstract representation of a graph.
    //
    // Vertices: vertices list contains all vertices in the graph.
    // Edges: edges list contains all edges in the graph.
    // Adjacency List: adjacencyList is a map where each vertex v is associated with a list of vertices that are adjacent to v.
    public ALGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }


    /**
     * Adds a vertex to the graph
     *
     * @param v vertex to be added
     * @return true if the vertex is successfully added,
     *          false if the vertex already exists in the graph
     */
    @Override
    public boolean addVertex(V v) {
        if(containsVertex(v,  this.vertices)) return false;

        else {
            this.vertices.add(v);
            this.adjacencyList.put(v, new ArrayList<>());
            return true;
        }
    }

    /**
     *  Checks if a vertex exists in the given list
     *
     * @param v       the vertex to be checked
     * @param toCheck the list of vertices to check for the presence of the vertex v
     * @return true if the vertex v is found in the list, false otherwise
     */
    private boolean containsVertex(V v, List<V> toCheck){
        for (V vertex : toCheck) {
            if(v.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the graph contains a specific vertex
     *
     * @param v the vertex to check for existence in the graph
     * @return true if the vertex is found in the graph, false otherwise
     */
    @Override
    public boolean hasVertex(V v) {
        return (containsVertex(v, this.vertices));
    }

    /**
     * Checks if a specific edge exists in the given list.
     *
     * @param e       the edge to be checked
     * @param toCheck the list of edges to check for the presence of the edge e
     * @return true if the edge is found in the list, false otherwise
     */
    private boolean containsEdge(E e, List<E> toCheck){
        for (E edge : toCheck) {
            if(e.equals(edge)) {
                return true;
            }
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
        if(!(this.vertices.contains(e.v1()) && this.vertices.contains(e.v2()))) {
            return false;
        } else if(this.edges.contains(e)) {
            return false;
        } else {
            this.edges.add(e);
            this.adjacencyList.get(e.v1()).add(e.v2());
            this.adjacencyList.get(e.v2()).add(e.v1());
            return true;
        }
    }

    /**
     * Check if the graph contains a specific edge
     *
     * @param e the edge to check for existence in the graph
     * @return true if the edge is found in the graph, false otherwise
     */
    @Override
    public boolean hasEdge(E e) {
        return containsEdge(e, this.edges);
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
        for(E e : this.edges){
            if(e.v1().equals(v1) && e.v2().equals(v2) || e.v2().equals(v1) && e.v1().equals(v2)) {
                return true;
            }
        }
        return false;
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
        int edgeLengthSum = 0;
        for(E e : this.edges){
            edgeLengthSum += e.length();
        }
        return edgeLengthSum;
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

        Iterator<E> myIterator = this.edges.iterator();
        while(myIterator.hasNext()) {
            E edge = myIterator.next();
            if(edge.equals(e)) {
                adjacencyList.get(edge.v1()).remove(edge.v2());
                adjacencyList.get(edge.v2()).remove(edge.v1());
                myIterator.remove();
                return true;
            }
        }
        return false;
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

        Iterator<E> myIterator = this.edges.iterator();
        while(myIterator.hasNext()) {
            E edge = myIterator.next();
            if(edge.v1().equals(v1) && edge.v2().equals(v2)) {
                myIterator.remove();
                adjacencyList.get(edge.v1()).remove(edge.v2());
                adjacencyList.get(edge.v2()).remove(edge.v1());
                return true;
            }
        }
        return false;
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

        Iterator<V> myIterator = this.vertices.iterator();
        while(myIterator.hasNext()) {
            V vertex = myIterator.next();
            if(vertex.equals(v) && hasVertex(v)) {
                removeEdgeforVertex(v);                         //remove edge for given vertex
                adjacencyList.remove(v);                        //removes vertex (key) from adjacency list
                removeVertexfromAL(v);                          //removes vertex (values) from array list of values
                myIterator.remove();                            //removes vertex from vertices list
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all edges incident to the given vertex from the graph
     *
     * @param v the vertex for which the incident edges are to be removed
     */
    private void removeEdgeforVertex(V v) {
        this.edges.removeIf(edge -> edge.v1().equals(v) || edge.v2().equals(v));
    }

    /**
     * Removes a vertex from the adjacency list of the graph
     *
     * @param v the vertex to be removed from the adjacency list
     */
    private void removeVertexfromAL(V v) {
        for(V vert : adjacencyList.keySet()) {
            adjacencyList.get(vert).remove(v);
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
        for(E e : this.edges){
            if(e.v1().equals(v1) && e.v2().equals(v2) || e.v2().equals(v1) && e.v1().equals(v2)) {
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

        HashMap<V, E> neighbourhood = new HashMap<>();
        for(V vertex : adjacencyList.get(v)) {
            neighbourhood.put(vertex, getEdge(vertex, v));
        }
        return neighbourhood;
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
     * Returns the list of all neighbours of the given vertex up to a given range
     *
     * @param v the vertex whose neighbourhood we need to find
     * @param range the distance from the vertex up to which we need to find neighbours
     * @return all neighbours in the given range from the given vertex
     */
    @Override
    public Map<V, E> getNeighbourhood(V v, int range) {
        HashMap<V, E> neighbourhood = new HashMap<>();
        getNeighbours(v, range, neighbourhood);
        if(!neighbourhood.isEmpty()) {
            neighbourhood.remove(v);
        }
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
        int cost;
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
