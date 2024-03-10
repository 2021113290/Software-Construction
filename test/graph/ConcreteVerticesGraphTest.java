/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests for graph.ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against graph.ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the graph.Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a graph.ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing graph.ConcreteVerticesGraph...
     */
    
    // Testing strategy for graph.ConcreteVerticesGraph.toString()
    //    test if return String true or false
    //    produced by set()
    //    Exhaustive Cartesian coverage of partition
    
    // TODO tests for graph.ConcreteVerticesGraph.toString()
    @Test
    public void toStringVerticesGraphTest(){
        Graph<String> graph=emptyInstance();
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("c","a",3);
        assertEquals("graph.ConcreteVerticesGraph{vertices=[graph.Vertex{vertexName=a, vertexSource={c=3.0}, vertexTarget={b=1.0}}, graph.Vertex{vertexName=b, vertexSource={a=1.0}, vertexTarget={c=2.0}}, graph.Vertex{vertexName=c, vertexSource={b=2.0}, vertexTarget={a=3.0}}]}",graph.toString());
    }
    /*
     * Testing Vertex...
     */
    // Testing strategy for Vertex
    //    Test strategy
    //    new a vertex to see if the Method of get true
    //    produced by constructor,new Vertex and new HashMap<>()
    //    Exhaustive Cartesian coverage of partition
    // TODO tests for operations of Vertex
    @Test
    public void VertexGetTest(){
        Vertex vertex=new Vertex("a");
        assertEquals(1.0,vertex.addTarget("b",1.0),0);
        assertEquals(2.0,vertex.addSource("c",2.0),0);
        assertEquals("a",vertex.getVertexName());
        assertEquals(2.0,vertex.getSourceWeight("c"),0);
        assertEquals(1.0,vertex.getTargetWeight("b"),0);
        Map<String,Double> map1=new HashMap<>();
        map1.clear();
        map1.put("c",2.0);
        Map<String,Double> map2=new HashMap<>();
        map2.clear();
        map2.put("b",1.0);
        assertEquals(map1,vertex.getVertexSource());
        assertEquals(map2,vertex.getVertexTarget());
        assertEquals(2.0,vertex.removeSource("c"),0);
        assertEquals(1.0,vertex.removeTarget("b"),0);

    }
    
}
