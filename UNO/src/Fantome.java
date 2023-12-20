import java.util.Stack;

class Fantome extends Monstre {
    Fantome(int x, int y) {
        super(x, y);
    }

    @Override
    void deplacerIntelligemment(boolean[][] murs, Heros heros) {
        Stack<Position> stack = new Stack<>();
        stack.push(new Position(x, y));

        boolean[][] visite = new boolean[murs.length][murs[0].length];
        visite[x][y] = true;

        while (!stack.isEmpty()) {
            Position current = stack.pop();

            if (current.x == heros.x && current.y == heros.y) {
                // Trouvé le héros, déplacer le monstre dans cette direction
                if (x < current.x) {
                    x++;
                } else if (x > current.x) {
                    x--;
                } else if (y < current.y) {
                    y++;
                } else if (y > current.y) {
                    y--;
                }
                return;
            }

            // Ajouter les positions voisines à la pile, même si elles sont des murs
            ajouterVoisins(current, stack, visite, murs);
        }
    }

    //@Override
    boolean estDeplacementValide(int direction, boolean[][] murs) {
        // Le fantôme peut traverser les murs
        return true;
    }
}