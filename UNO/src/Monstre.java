import java.util.Stack;

class Monstre extends Personne {
    Monstre(int x, int y) {
        super(x, y);
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    void deplacerIntelligemment(boolean[][] murs, Heros heros) {
        Stack<Position> stack = new Stack<>();
        stack.push(new Position(x, y));

        boolean[][] visite = new boolean[murs.length][murs[0].length];
        visite[x][y] = true;

        while (!stack.isEmpty()) {
            Position current = stack.pop();

            if (current.x == heros.x && current.y == heros.y) {
                // Trouvé le héros, déplacer le monstre dans cette direction
                if (x < current.x && !murs[x + 1][y]) {
                    x++;
                } else if (x > current.x && !murs[x - 1][y]) {
                    x--;
                } else if (y < current.y && !murs[x][y + 1]) {
                    y++;
                } else if (y > current.y && !murs[x][y - 1]) {
                    y--;
                }
                return;
            }

            // Ajouter les positions voisines à la pile
            ajouterVoisins(current, stack, visite, murs);
        }
    }

    	void ajouterVoisins(Position current, Stack<Position> stack, boolean[][] visite, boolean[][] murs) {
        int x = current.x;
        int y = current.y;

        ajouterSiValide(stack, visite, x - 1, y, murs);
        ajouterSiValide(stack, visite, x + 1, y, murs);
        ajouterSiValide(stack, visite, x, y - 1, murs);
        ajouterSiValide(stack, visite, x, y + 1, murs);
    }

     void ajouterSiValide(Stack<Position> stack, boolean[][] visite, int x, int y, boolean[][] murs) {
        if (x >= 0 && x < visite.length && y >= 0 && y < visite[0].length && !visite[x][y] && !murs[x][y]) {
            stack.push(new Position(x, y));
            visite[x][y] = true;
        }
    }

    static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

	@Override
	void deplacerAleatoirement(boolean[][] murs) {
		// TODO Auto-generated method stub
		return ;
		
	}

	boolean estDeplacementValide( boolean[][] murs) {
		// TODO Auto-generated method stub
		return false;
	}


}
