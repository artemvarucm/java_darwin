package es.ucm.mutation;

import org.junit.Test;

import static org.junit.Assert.*;

public class InsertionMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new InsertionMutate(1);
        this.mutateTest(mutator);
    }
}