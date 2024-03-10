/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //    test if return String true or false
    //    produced by set()
    //    Exhaustive Cartesian coverage of partition
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void toStringVerticesGraphTest(){
        Graph<String> graph=emptyInstance();
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("c","a",3);
        assertEquals("ConcreteVerticesGraph{vertices=[Vertex{vertexName=a, vertexSource={c=3}, vertexTarget={b=1}}, Vertex{vertexName=b, vertexSource={a=1}, vertexTarget={c=2}}, Vertex{vertexName=c, vertexSource={b=2}, vertexTarget={a=3}}]}",graph.toString());
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
        assertEquals(1,vertex.addTarget("b",1));
        assertEquals(2,vertex.addSource("c",2));
        assertEquals("a",vertex.getVertexName());
        assertEquals(2,vertex.getSourceWeight("c"));
        assertEquals(1,vertex.getTargetWeight("b"));
        Map<String,Integer> map1=new HashMap<>();
        map1.clear();
        map1.put("c",2);
        Map<String,Integer> map2=new HashMap<>();
        map2.clear();
        map2.put("b",1);
        assertEquals(map1,vertex.getVertexSource());
        assertEquals(map2,vertex.getVertexTarget());
        assertEquals(2,vertex.removeSource("c"));
        assertEquals(1,vertex.removeTarget("b"));

    }
    
}
