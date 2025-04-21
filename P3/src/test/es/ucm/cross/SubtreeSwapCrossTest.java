package es.ucm.cross;

import es.ucm.cross.tree.SubtreeSwapCross;
import es.ucm.factories.IndividuoFactory;
import es.ucm.factories.IndividuoHormigaFactory;
import es.ucm.initializer.FULLInitializer;
import es.ucm.mapa.SantaFeMap;
import org.junit.Test;


public class SubtreeSwapCrossTest extends AbstractCrossTest {
    @Test
    public void crossTest1() {
        IndividuoFactory factory = new IndividuoHormigaFactory(new SantaFeMap(), 400, 0., new FULLInitializer(3));
        AbstractCross crosser = new SubtreeSwapCross(factory, 0.5);
        this.crossTest(crosser, factory);
    }
}