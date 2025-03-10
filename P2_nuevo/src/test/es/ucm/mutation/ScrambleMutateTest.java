package es.ucm.mutation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScrambleMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new ScrambleMutate(0.05);
        this.mutateTest(mutator);
    }
}