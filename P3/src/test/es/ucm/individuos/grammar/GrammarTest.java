package es.ucm.individuos.grammar;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class GrammarTest {
    @Test
    public void test1() {
        Grammar g = new Grammar(1);
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractGrammarElem el: g.decode(List.of(0,0,0,1,2,0,1,0,  1,1))) {
            stringBuilder.append(el.toString());
        }
        System.out.println(stringBuilder.toString());
        /*
        <if_food>
            forward
        else
            left
        */
    }

    @Test
    public void test2() {
        Grammar g = new Grammar(1);
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractGrammarElem el: g.decode(List.of(0, 1))) { // unwraps to 0, 1, 0
            stringBuilder.append(el.toString());
        }
        System.out.println("WRAP");
        System.out.println(stringBuilder.toString());
    }
}