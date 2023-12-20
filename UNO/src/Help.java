import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

class Help {
    private int x;
    private int y;

    private static final Image helpImage = new ImageIcon("Images/Help.png").getImage();

    Help(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Help creerAideAleatoire(boolean[][] murs, Heros heros) {
        Random random = new Random();
        int x;
        int y;

        do {
            x = random.nextInt(murs.length - 2) + 1;
            y = random.nextInt(murs[0].length - 2) + 1;
        } while (murs[x][y] || (x == heros.x && y == heros.y));

        return new Help(x, y);
    }

    public boolean estRencontre(Heros heros) {
        return this.x == heros.x && this.y == heros.y;
    }

    public void dessiner(Graphics g, int CELL_SIZE) {
        g.drawImage(helpImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
    }
    
    // Ajoutez la méthode pour incrémenter la vie du héros
    public void incrementerVie(Heros heros) {
        heros.incrementeVie();
    }
    
}