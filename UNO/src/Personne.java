import java.util.Random;

abstract class Personne {
    int x;
    int y;

    Personne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    abstract void deplacerAleatoirement(boolean[][] murs);
}

