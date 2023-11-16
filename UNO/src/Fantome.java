import java.util.Random;

class Fantome extends Monstre {
    Fantome(int x, int y) {
        super(x, y);
    }

   /* @Override
    void deplacerIntelligemment(boolean[][] murs, Heros heros) {
        Random random = new Random();
        int direction;
        do {
            direction = random.nextInt(4);
        } while (!estDeplacementValide(direction, murs));

        switch (direction) {
            case 0:
                x--;
                break;
            case 1:
                x++;
                break;
            case 2:
                y--;
                break;
            case 3:
                y++;
                break;
        }
    }
*/
    //@Override
    boolean estDeplacementValide(int direction, boolean[][] murs) {
        // Le fantôme peut traverser les murs
        return true;
    }
}
