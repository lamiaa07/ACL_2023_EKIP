import org.junit.Test;

import static org.junit.Assert.*;

public class TestAll {

	@Test
	public void testHerosLosesLifeOnCollision() {
	    Labyrinthe labyrinthe = new Labyrinthe();
	    Heros heros = labyrinthe.heros;
	    Monstre monstre = labyrinthe.monstre;
	    heros.setX(monstre.getX());
	    heros.setY(monstre.getY());
	    int initialVies = heros.getVies();
	    System.out.println("Avant collision - Vies initiales : " + initialVies);
	    labyrinthe.traitementCollision();
	    System.out.println("Après collision - Vies restantes : " + heros.getVies());
	    assertEquals(initialVies - 1, heros.getVies());
	}


    

    @Test
    public void testMonstreMovesTowardsHeros() {
        Heros heros = new Heros(1, 1);
        Monstre monstre = new Monstre(3, 3);

        monstre.deplacerIntelligemment(new boolean[5][5], heros);

        assertTrue((monstre.getX() == 2 && monstre.getY() == 3) ||
                   (monstre.getX() == 3 && monstre.getY() == 2) ||
                   (monstre.getX() == 4 && monstre.getY() == 3) ||
                   (monstre.getX() == 3 && monstre.getY() == 4));
    }
    @Test
    public void testHelpIncrementLife() {
        Heros heros = new Heros(1, 1);
        Help help = new Help(1, 2);

        int initialVies = heros.getVies();
        help.incrementerVie(heros);
        assertEquals(initialVies + 1, heros.getVies());
    }
}
