package es.ucm.mutation;

import org.junit.Test;

import static org.junit.Assert.*;

public class InversionMutateTest extends AbstractMutateTest{
    @Test
    public void mutate() {
        AbstractMutate mutator = new InversionMutate(0.05);
        this.mutateTest(mutator);
    }
}