import java.util.Random;

class Monstre2 extends Monstre {
    Monstre2(int x, int y) {
        super(x, y);
    }

    @Override
    public void deplacerIntelligemment(boolean[][] murs, Heros heros) {
        int deltaX = heros.x - x;
        int deltaY = heros.y - y;

        int newX = x;
        int newY = y;

        // Déplacement horizontal
        if (Math.abs(deltaX) >= 2) {
            newX += (deltaX > 0) ? 2 : -2;
        } else {
            newX += (deltaX > 0) ? 1 : -1;
        }

        // Déplacement vertical
        if (Math.abs(deltaY) >= 2) {
            newY += (deltaY > 0) ? 2 : -2;
        } else {
            newY += (deltaY > 0) ? 1 : -1;
        }

        // Vérifier si le nouveau déplacement est valide
        if (estDeplacementValide(newX, newY, murs)) {
            x = newX;
            y = newY;
        } else {
            deplacerAleatoirement(murs);
        }
    }

    // Ajouter la méthode estDeplacementValide
    private boolean estDeplacementValide(int x, int y, boolean[][] murs) {
        return x >= 0 && x < murs.length && y >= 0 && y < murs[0].length && !murs[x][y];
    }
}
