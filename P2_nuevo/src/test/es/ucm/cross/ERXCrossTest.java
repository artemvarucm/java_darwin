package es.ucm.cross;

import es.ucm.factories.IndividuoAspiradoraFactory;
import es.ucm.factories.IndividuoFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class ERXCrossTest extends AbstractCrossTest {
    @Test
    public void crossTest1() {
        IndividuoFactory factory = new IndividuoAspiradoraFactory();
        AbstractCross crosser = new ERXCross(factory);
        this.crossTest(crosser);
    }
}