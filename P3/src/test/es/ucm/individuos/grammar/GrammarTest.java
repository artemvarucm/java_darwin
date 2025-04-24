package es.ucm.individuos.grammar;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class GrammarTest {
    @Test
    public void test1() {
        Grammar g = new Grammar(1);
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractGrammarElem el: g.decode(List.of(1,0,0,0,0,1,2,0,1,0,  1,1))) {
            stringBuilder.append(el.toString());
        }
        assertEquals(stringBuilder.toString(),
                """
                <if_food>
                forward
                left
                """);
    }
}