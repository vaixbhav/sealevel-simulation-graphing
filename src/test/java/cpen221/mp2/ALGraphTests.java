package cpen221.mp2;

import cpen221.mp2.graph.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap; 

import static org.junit.jupiter.api.Assertions.*;

public class ALGraphTests<V extends Vertex, E> {

    @Test
    public void testTask1ALGraph() {
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

        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

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
    public void testTask2ALGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();
        Set<Edge<V>> edges = new HashSet<>();
        Set<Vertex> vertices = new HashSet<>();
        edges.add((Edge<V>) e2);
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
        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

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
    }

    @Test
    public void testGetNeighbour(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(6, "E");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v5, v4, 10);


        HashMap<V, E> map1 = new HashMap<>();
        map1.put((V) v2, (E) e1); 
        map1.put((V) v4, (E) e3); 

        HashMap<V, E> map4 = new HashMap<>();
        map4.put((V) v1, (E) e3); 
        map4.put((V) v5, (E) e4); 


        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        
        assertEquals(map1, g.getNeighbourhood(v1));
        assertEquals(map4, g.getNeighbourhood(v4));
        

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
        
        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

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
        
        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

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

    @Test
    public void testGetNeighbourhood(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");
        Vertex v7 = new Vertex(7, "G");
        Vertex v8 = new Vertex(8, "H");
        Vertex v9 = new Vertex(9, "I");
        Vertex v10 = new Vertex(10, "J");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 9);
        Edge<Vertex> e4 = new Edge<>(v1, v5, 10);
        Edge<Vertex> e5 = new Edge<>(v4, v6, 12);
        Edge<Vertex> e6 = new Edge<>(v3, v8, 6);
        Edge<Vertex> e7 = new Edge<>(v5, v7, 3);
        Edge<Vertex> e8 = new Edge<>(v6, v9, 3);
        Edge<Vertex> e9 = new Edge<>(v7, v10, 5);

//
        Set<E> incidents = new HashSet<>();
        Set<E> edges = new HashSet<>();


        HashMap<V, E> map1 = new HashMap<V, E>();
        map1.put((V) v2, (E) e1);
        map1.put((V) v3, (E) e3);
        map1.put((V) v4, (E) e2);
        map1.put((V) v5, (E) e4);
        map1.put((V) v6, (E) e5);
        map1.put((V) v8, (E) e6);
        map1.put((V) v7, (E) e7);

        IGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();


        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v9);
        g.addVertex(v10);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);

        assertEquals(map1, g.getNeighbourhood(v1, 2));

    }

}
