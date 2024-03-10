/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import P1.graph.Graph;
import org.junit.Test;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //    GraphPoet's input :one word,one line, several lines, null,lowerCase,higherCase
    //    corpus :file of one word,file of one line,file of several lines,file of null
    //    bridgeWord: one or several or null
    //    toString: null file ,not null file
    //    produced by constructor,poem()
    //    Exhaustive Cartesian coverage of partition
    @Test
    public void PoetInputTest() throws IOException {
        GraphPoet graphPoet=new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
        assertEquals("This",graphPoet.poem("This"));
        assertEquals("This is a test of the Mugar Omni Theater sound system.",graphPoet.poem("This is a of the Mugar Omni Theater sound system."));
        assertEquals("This is a test of the of the mugar Omni Theater sound",graphPoet.poem("This is a of the"+"\n"+" of the  Omni Theater sound"));
        assertEquals("",graphPoet.poem(""));
        assertEquals("this is a test of the",graphPoet.poem("this is a of the"));
        assertEquals("THIS IS A test OF THE",graphPoet.poem("THIS IS A OF THE"));
    }
    @Test
    public void PoetCorpusTest() throws IOException{
        GraphPoet graphPoet1=new GraphPoet(new File("src/P1/poet/null.txt"));
        assertEquals("I like apples",graphPoet1.poem("I like apples"));
        GraphPoet graphPoet2=new GraphPoet(new File("src/P1/poet/oneLine.txt"));
        assertEquals("I like eating apples",graphPoet2.poem("I like apples"));
        GraphPoet graphPoet3=new GraphPoet(new File("src/P1/poet/oneWord.txt"));
        assertEquals("I like apples",graphPoet3.poem("I like apples"));
        GraphPoet graphPoet4=new GraphPoet(new File("src/P1/poet/severalLines.txt"));
        assertEquals("I like eating apples",graphPoet4.poem("I like apples"));
    }
    @Test
    public void PoetBridgeWordTest() throws IOException {
        GraphPoet graphPoet=new GraphPoet(new File("src/P1/poet/severalBridgeWord.txt"));
        assertEquals("say hello world haha",graphPoet.poem("say world haha"));
    }
    @Test
    public void PoetToStringTest() throws IOException{
        GraphPoet graphPoet=new GraphPoet(new File("src/P1/poet/null.txt"));
        assertEquals("GraphPoet{graph=ConcreteEdgesGraph{vertices=[], edges=[]}}",graphPoet.toString());
        GraphPoet graphPoet2=new GraphPoet(new File("src/P1/poet/oneLine.txt"));
        assertEquals("GraphPoet{graph=ConcreteEdgesGraph{vertices=[very, like, much., i, eating, apples], edges=[Edge{source='i', target='like', weight=1}, Edge{source='like', target='eating', weight=1}, Edge{source='eating', target='apples', weight=1}, Edge{source='apples', target='very', weight=1}, Edge{source='very', target='much.', weight=1}]}}",graphPoet2.toString());
    }
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    // TODO tests
    
}
