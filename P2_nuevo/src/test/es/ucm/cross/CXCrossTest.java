package es.ucm.cross;

import es.ucm.factories.IndividuoAspiradoraFactory;
import es.ucm.factories.IndividuoFactory;
import es.ucm.mansion.MansionMap;
import org.junit.Test;

import static org.junit.Assert.*;
public class CXCrossTest extends AbstractCrossTest {
    @Test
    public void crossTest1() {
        IndividuoFactory factory = new IndividuoAspiradoraFactory(new MansionMap());
        AbstractCross crosser = new CXCross(factory);
        this.crossTest(crosser);
    }
}