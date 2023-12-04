import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeroTest {

    @Test
    public void testPerdreVie() {
        Heros heros = new Heros(2, 2);
        assertEquals(3, heros.getVies()); 

        heros.perdreVie();
        assertEquals(2, heros.getVies()); 
    }

    @Test
    public void testIncrementeVie() {
        Heros heros = new Heros(2, 2);
        assertEquals(3, heros.getVies()); 

        heros.incrementeVie();
        assertEquals(4, heros.getVies()); 
    }

    
}
