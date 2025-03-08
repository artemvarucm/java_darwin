package es.ucm.cross;

import es.ucm.factories.IndividuoAspiradoraFactory;
import es.ucm.factories.IndividuoFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class PMXCrossTest extends AbstractCrossTest {
    @Test
    public void crossTest1() {
        IndividuoFactory factory = new IndividuoAspiradoraFactory();
        AbstractCross crosser = new PMXCross(factory);
        this.crossTest(crosser);
    }
}