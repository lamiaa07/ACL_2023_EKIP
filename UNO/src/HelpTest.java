import static org.junit.Assert.*;

import org.junit.Test;

public class HelpTest {

    @Test
    public void testEstRencontre() {
        // Créer un héros avec des coordonnées spécifiques
        Heros heros = new Heros(1, 1);

        // Créer une aide à des positions différentes
        Help help = Help.creerAideAleatoire(new boolean[5][5], heros);

        // Assurer que le héros et l'aide ne se rencontrent pas initialement
        assertFalse(help.estRencontre(heros));

        // Modifier les coordonnées du héros pour simuler un déplacement
        heros = new Heros(2, 2);

       
    }

 

}
