import org.junit.Test;
import static org.junit.Assert.*;

public class MonstreTest {

    @Test
    public void testDeplacerIntelligemment() {
        // Créons une instance de Heros et de Monstre
        Heros heros = new Heros(1, 1);
        Monstre monstre = new Monstre(0, 0);

        // Créons une grille de murs
        boolean[][] murs = new boolean[3][3];
        murs[1][0] = true; // Ajouter un mur bloquant le chemin du monstre

        // Appelons  la méthode de déplacement intelligent du monstre
        monstre.deplacerIntelligemment(murs, heros);

        // Vérifions que le monstre s'est déplacé vers le héros de manière intelligente
        assertTrue((monstre.x == 0 && monstre.y == 1) || (monstre.x == 1 && monstre.y == 0));
    }

    
}
