package es.ucm.mutation;

import es.ucm.mutation.tree.TerminalMutate;
import org.junit.Test;


public class TerminalMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new TerminalMutate(1);
        this.mutateTest(mutator);
    }
}