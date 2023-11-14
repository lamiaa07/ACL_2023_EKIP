import java.util.Random;

class Monstre extends Personne {
    Monstre(int x, int y) {
        super(x, y);
    }

    void deplacerAleatoirement(boolean[][] murs) {
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

    private boolean estDeplacementValide(int direction, boolean[][] murs) {
        switch (direction) {
            case 0:
                return x > 0 && !murs[x - 1][y];
            case 1:
                return x < murs.length - 1 && !murs[x + 1][y];
            case 2:
                return y > 0 && !murs[x][y - 1];
            case 3:
                return y < murs[0].length - 1 && !murs[x][y + 1];
            default:
                return false;
        }
    }
}