package es.ucm.individuos.grammar;

import org.junit.Test;

import java.util.List;


public class GrammarTest {
    @Test
    public void testForwardNode() {
        Grammar g = new Grammar();
        System.out.println(g.decode(List.of(1,0,1,0)));
    }

}