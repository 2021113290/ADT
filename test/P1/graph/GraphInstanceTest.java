/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

//     Testing strategy
//    Partition the inputs of public boolean add(L vertex) as follows:
//    vertex: vertex exists in the graph, vertex doesn't exist in the graph
//    produced by add()
//    Exhaustive Cartesian coverage of partition
//    covers vertex exists in the graph ;vertex doesn't exist in the graph
    @Test
    public void addTest(){
            Graph <String> graph=emptyInstance();
            graph.add("a");
            graph.add("b");

            assertEquals(true,graph.vertices().contains("b"));
            assertEquals(false,graph.add("a"));
    }
//    Test strategy
//    vertex:vertex has included to the graph, vertex has not included to the graph
//    weight:>0,=0
//    produced by add() ,produced by set()
//    Exhaustive Cartesian coverage of partition
//    covers change : weight>0 && vertex exists in the graph && has an edge between two vertexes
//           add edge : weight>0 && vertex exists in the graph && has no edge between two vertexes
//           add edge and vertex : weight>0 && vertex doesn't exist in the graph
//           remove : weight=0 && has an edge between two vertexes
    @Test
    public void setTest(){
        Graph <String> graph=emptyInstance();
        assertEquals(1,graph.set("a","b",1)); //返回的是边的权重
        assertEquals(true,graph.vertices().contains("a"));
        assertEquals(true,graph.vertices().contains("b"));
        graph.add("c");
        assertEquals(2,graph.set("a","c",2));
        assertEquals(3,graph.set("a","b",3));
        assertEquals(0,graph.set("a","c",0));
        assertEquals(true,graph.vertices().contains("c"));
    }
//    Test strategy
//    by set ;test if vertex or edge can be removed
//    vertex:vertex has included to the graph, vertex has not included to the graph
//    produced by set(), produced by remove()
//    Exhaustive Cartesian coverage of partition
//    covers vertex exists in the graph ;vertex doesn't exist in the graph
    @Test
    public void removeTest(){
        Graph <String> graph=emptyInstance();
        graph.set("a","b",1);
        assertEquals(true,graph.sources("b").containsKey("a"));
        graph.set("a","c",2);
        graph.set("b","c",3);
        assertEquals(true,graph.remove("a"));
        assertEquals(false,graph.sources("b").containsKey("a"));
        assertEquals(false,graph.remove("a"));
        assertEquals(false,graph.sources("b").containsKey("a"));//检验边有没有被删除
    }
//    Test strategy
//    by add vertex;test if vertex exist in the graph
//    vertex:vertex has included to the graph, vertex has not included to the graph
//    produced by add()
//    Exhaustive Cartesian coverage of partition
//    covers vertex exists in the graph ;vertex doesn't exist in the graph
    @Test
    public void verticesTest(){
        Graph <String> graph=emptyInstance();
        graph.add("a");
        assertEquals(true,graph.vertices().contains("a"));
        assertEquals(false,graph.vertices().contains("c"));
    }
//    Test strategy
//    by add vertex;test if vertex exist in the graph
//    vertex:vertex has sources, vertex has not sources
//    produced by set()
//    Exhaustive Cartesian coverage of partition
//    covers vertex has sources, vertex has not sources
    @Test
    public void sourcesTest(){
        Graph <String> graph=emptyInstance();
        graph.set("b","a",1);
        graph.set("c","a",2);
        assertEquals(true,graph.sources("a").containsKey("b"));
        assertEquals(true,graph.sources("a").containsKey("c"));
        assertEquals(false,graph.sources("b").containsKey("c"));
    }
    //    Test strategy
//    by add vertex;test if vertex exist in the graph
//    vertex:vertex has targets, vertex has not targets
//    produced by set()
//    Exhaustive Cartesian coverage of partition
//    covers vertex has targets, vertex has not targets
    @Test
    public void targetsTest(){
        Graph <String> graph=emptyInstance();
        graph.set("a","b",1);
        graph.set("a","c",2);
        assertEquals(true,graph.targets("a").containsKey("b"));
        assertEquals(true,graph.targets("a").containsKey("c"));
        assertEquals(false,graph.targets("b").containsKey("c"));
    }
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    
}
