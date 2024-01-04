package cpen221.mp2;

import cpen221.mp2.graph.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap; 

import static org.junit.jupiter.api.Assertions.*;

public class AMGraphTests <V, E> {

    @Test
    public void testTask1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v3, v5, 12);
        Edge<Vertex> e5 = new Edge<>(v5, v6, 15);

        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(5);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertTrue(g.addVertex(v5));     //addVertex true
        assertFalse(g.addVertex(v4));    //addVertex false
        assertTrue(g.hasVertex(v1));     //hasVertex true
        assertFalse(g.hasVertex(v6));    //hasVertex false
        assertTrue(g.addEdge(e4));       //addEdge true
        assertFalse(g.addEdge(e3));      //addEdge false
        assertFalse(g.addEdge(e5));      //addEdge false since vertex not in graph
        assertTrue(g.hasEdge(e1));       //hasEdge true
        assertFalse(g.hasEdge(e5));      //hasEdge false
        assertTrue(g.hasEdge(v1, v2));   //hasEdge true given two vertices
        assertFalse(g.hasEdge(v4, v4));  //hasEdge false given two same vertices
        assertFalse(g.hasEdge(v3, v4));  //hasEdge false given two vertices
        assertEquals(g.edgeLength(v1, v2), 5);  //edgeLengths
        assertEquals(g.edgeLength(v3, v5), 12);

    }

    @Test
    public void testTask2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        Set<Edge> edges = new HashSet<>();
        Set<Vertex> vertices = new HashSet<>();
        Set<Edge> incidentVertex = new HashSet<>();
        edges.add(e2);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);



        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        g.removeEdge(e3);    //removeEdge
        g.removeVertex(v4);  //removeVertex
        g.removeEdge(v1,v2); //removeEdge given vertices

        assertEquals(g.allEdges(), edges);       //allEdges()
        assertEquals(g.allVertices(), vertices); //allVertices()

        //Remaining: getEdge, getNeighbours
    }

    @Test
    public void testTask3(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(6, "E");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v5, v4, 10);

        Set<E> incidents = new HashSet<>();
        Set<E> edges = new HashSet<>();
        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(5);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);


        incidents.add((E) e1); 
        incidents.add((E) e3); 

        assertEquals(incidents, g.allEdges(v1));

        assertEquals(21, g.edgeLengthSum()); // Get length of all edges
        g.removeVertex(v1); // Removing v1 should remove all connections of v1
        assertEquals(7, g.edgeLengthSum());
        g.addVertex(v5); // Add a new vertex and edge
        g.addEdge(e4);
        assertEquals(17, g.edgeLengthSum());


        g.removeVertex(v4);
        g.removeVertex(v3);
        assertEquals(edges, g.allEdges());
        assertEquals(0, g.edgeLengthSum());

    }

    @Test
    public void testGetNeighbour(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(6, "E");
        Vertex v6 = new Vertex(7, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v5, v4, 10);

        Set<E> incidents = new HashSet<>();
        Set<E> edges = new HashSet<>();


        HashMap<V, E> map1 = new HashMap<V, E>(); 
        map1.put((V) v2, (E) e1); 
        map1.put((V) v4, (E) e3); 

        HashMap<V, E> map4 = new HashMap<V, E>();
        map4.put((V) v1, (E) e3); 
        map4.put((V) v5, (E) e4); 

        HashMap<V, E> map6 = new HashMap<V, E>();


        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(6);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6); 

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        
        assertEquals(map1, g.getNeighbourhood(v1)); // 2 neighbours
        assertEquals(map4, g.getNeighbourhood(v4)); // 1 neighbour
        assertEquals(map6, g.getNeighbourhood(v6)); // 0 neighbours (should be empty)
    
    }


    @Test
    public void testTask1AMGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v3, v5, 12);
        Edge<Vertex> e5 = new Edge<>(v5, v6, 15);

        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(6);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertTrue(g.addVertex(v5));     //addVertex true
        assertFalse(g.addVertex(v4));    //addVertex false
        assertTrue(g.hasVertex(v1));     //hasVertex true
        assertFalse(g.hasVertex(v6));    //hasVertex false
        assertTrue(g.addEdge(e4));       //addEdge true
        assertFalse(g.addEdge(e3));      //addEdge false
        assertFalse(g.addEdge(e5));      //addEdge false since vertex not in graph
        assertTrue(g.hasEdge(e1));       //hasEdge true
        assertFalse(g.hasEdge(e5));      //hasEdge false
        assertTrue(g.hasEdge(v1, v2));   //hasEdge true given two vertices
        assertFalse(g.hasEdge(v4, v4));  //hasEdge false given two same vertices
        assertFalse(g.hasEdge(v3, v4));  //hasEdge false given two vertices
        assertEquals(g.edgeLength(v1, v2), 5);  //edgeLengths
        assertEquals(g.edgeLength(v3, v5), 12);

    }

    @Test
    public void testTask2AMGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        Set<Edge> edges = new HashSet<>();
        Set<Vertex> vertices = new HashSet<>();
        Set<Edge> incidentVertex = new HashSet<>();
        edges.add(e2);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);



        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        g.removeEdge(e3);    //removeEdge
        g.removeVertex(v4);  //removeVertex
        g.removeEdge(v1,v2); //removeEdge given vertices

        assertEquals(g.allEdges(), edges);       //allEdges()
        assertEquals(g.allVertices(), vertices); //allVertices()
        assertEquals(g.edgeLengthSum(), 7);  //edgeLengthSum()
        assertEquals(g.getEdge(v2, v3), e2);  //getEdge(v1,v2)



        //Remaining: edgeLengthSum, getEdge
    }




    

    @Test
    public void testTask4(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 5);
        Edge<Vertex> e3 = new Edge<>(v2, v4, 7);
        Edge<Vertex> e4 = new Edge<>(v4, v3, 9);
        
        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(16, g.getDiameter(PathCostType.SUM_EDGES));
        assertEquals(9, g.getDiameter(PathCostType.MIN_EDGE));
        assertEquals(9, g.getDiameter(PathCostType.MAX_EDGE));

        g.removeVertex(v1); 
        g.removeVertex(v2); 

        assertEquals(9, g.getDiameter(PathCostType.SUM_EDGES));
        assertEquals(9, g.getDiameter(PathCostType.MIN_EDGE));
    
    }

    @Test
    public void testCenter(){

        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 5);
        Edge<Vertex> e3 = new Edge<>(v2, v4, 7);
        Edge<Vertex> e4 = new Edge<>(v4, v3, 9);
        
        IGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(v1, g.getCenter(PathCostType.SUM_EDGES));
        g.removeVertex(v1); 
        assertEquals(v2, g.getCenter(PathCostType.SUM_EDGES));

    }


}
