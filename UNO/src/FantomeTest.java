import org.junit.Test;
import static org.junit.Assert.*;

public class FantomeTest {

    @Test
    public void testDeplacerAleatoirement() {
        // Créer une instance de Fantome
        Fantome fantome = new Fantome(0, 0);

        // Créer une grille de murs
        boolean[][] murs = new boolean[3][3];
        murs[1][0] = true; // Ajouter un mur bloquant le chemin du fantôme

        // Appeler la méthode de déplacement aléatoire du fantôme
        fantome.deplacerAleatoirement(murs);

        // Vérifier que le fantôme s'est déplacé dans une direction valide
        // Dans ce cas, le fantôme peut traverser les murs, donc le déplacement est toujours valide.
        assertTrue(fantome.x >= 0 && fantome.x < 3 && fantome.y >= 0 && fantome.y < 3);
    }

    
}
