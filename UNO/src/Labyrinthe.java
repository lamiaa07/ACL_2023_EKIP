
import java.util.Random;




public class Labyrinthe {
    private int width;
    private int height;
    char[][] grid;

    public Labyrinthe(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        initializeLabyrinth();
    }

    private void initializeLabyrinth() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == (height - 1))
                    grid[i][j] = 'X';
                else
                    grid[i][j] = ' ';
            }
        }
    }

    // 
    
    public static void main(String[] args) {
        // Point d'entrée du programme
        int width = 10; // Largeur du labyrinthe
        int height = 10; // Hauteur du labyrinthe

        Labyrinthe labyrinthe = new Labyrinthe(width, height);

        // Affichez le labyrinthe ou effectuez d'autres opérations si nécessaire
    }
}
