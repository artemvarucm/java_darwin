package es.ucm.mutation;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeuristicMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new HeuristicMutate(0.05);
        this.mutateTest(mutator);
    }
}