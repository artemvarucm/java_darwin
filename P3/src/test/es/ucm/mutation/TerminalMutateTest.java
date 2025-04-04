package es.ucm.mutation;

import org.junit.Test;


public class TerminalMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new TerminalMutate(1);
        this.mutateTest(mutator);
    }
}