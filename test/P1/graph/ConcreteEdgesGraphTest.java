/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //    test if return String true or false
    //    produced by set()
    //    Exhaustive Cartesian coverage of partition
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void toStringEdgesGraphTest(){
        Graph<String> graph=emptyInstance();
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("c","a",3);
        assertEquals("ConcreteEdgesGraph{"+
                "vertices=" + "[a, b, c]" +
                ", edges=" + "[Edge{source='a', target='b', weight=1}, Edge{source='b', target='c', weight=2}, Edge{source='c', target='a', weight=3}]"+
                '}',graph.toString());
    }

    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //    new an edge to see if the method of get true
    //    produced by constructor
    //    Exhaustive Cartesian coverage of partition
    
    // TODO tests for operations of Edge
    @Test
    public void EdgeTest(){
        Edge edge=new Edge("a","b",1);
        assertEquals("a",edge.getSource());
        assertEquals("b",edge.getTarget());
        assertEquals(1,edge.getWeight());
    }
    @Test
    public void toStringEdgeTest(){
        Edge edge=new Edge("a","b",1);
        assertEquals("Edge{source='a', target='b', weight=1}",edge.toString());
    }
    //Test strategy
    //edge: 空，引用相同，不是一个类的对象，值相同，值不同
    //produce by constructor
    //Exhaustive Cartesian coverage of partition
    @Test
    public void equalsEdgeTest() {
        Edge edge=new Edge("a","b",1);
        Edge edge2=new Edge("a","b",1);
        Edge edge3=new Edge("a","c",1);
        Edge edge1=edge;
        assertEquals(false,edge.equals(null));
        assertEquals(true,edge.equals(edge1));
        Graph<String> graph=emptyInstance();
        assertEquals(false,edge.equals(graph));
        assertEquals(true,edge.equals(edge2));
        assertEquals(false,edge.equals(edge3));
    }
}
