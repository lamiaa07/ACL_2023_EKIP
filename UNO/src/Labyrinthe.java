import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;






class Labyrinthe extends JFrame implements ActionListener, KeyListener {
    static final int SIZE = 10;
    private static final int CELL_SIZE = 30;

    private Heros heros;
    private Monstre monstre;
    private boolean[][] murs;

    private boolean herosABouge;

    private Timer timer;
    private int nombreAttrapages;

    Labyrinthe() {
        setTitle("Labyrinth Game");
        setSize(SIZE * CELL_SIZE, SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initialiserJeu();

        timer = new Timer(500, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
         nombreAttrapages = 0;
    }

    private void initialiserJeu() {
        heros = new Heros(1, 1);
        placerMonstre();
        initialiserMurs();
        herosABouge = false;
    }

    private void placerMonstre() {
        Random random = new Random();
        do {
            monstre = new Monstre(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
        } while (monstre.x == heros.x && monstre.y == heros.y);
    }

    private void initialiserMurs() {
        murs = new boolean[SIZE][SIZE];

        // Murs le long des bords
        for (int i = 0; i < SIZE; i++) {
            murs[i][0] = true;           // Haut
            murs[i][SIZE - 1] = true;    // Bas
            murs[0][i] = true;           // Gauche
            murs[SIZE - 1][i] = true;    // Droite
        }

        // Murs à l'intérieur (ajoutez vos murs ici)
        murs[3][4] = true;
        murs[5][6] = true;
        murs[7][8] = true;
    }

    private void verifierCollision() {
        if (heros.x == monstre.x && heros.y == monstre.y) {
            nombreAttrapages++;

            if (nombreAttrapages < 3) {
                JOptionPane.showMessageDialog(this, " Monster caught you! You have " + (3-nombreAttrapages) + " lives left.Stay alert !");
                initialiserJeu();
            } else {
                JOptionPane.showMessageDialog(this, "Game Over! Caught 3 times.");
                timer.stop();
                System.exit(0); // Terminer le programme
            }
        }
    }

    private void verifierVictoire() {
        // Ajoutez ici des conditions pour la victoire si nécessaire
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (herosABouge) {
            monstre.deplacerAleatoirement(murs);
            herosABouge = false;
        }
        verifierCollision();
        verifierVictoire();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (heros.y > 0 && !murs[heros.x][heros.y - 1]) {
                    heros.y--;
                    herosABouge = true;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (heros.y < SIZE - 1 && !murs[heros.x][heros.y + 1]) {
                    heros.y++;
                    herosABouge = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (heros.x > 0 && !murs[heros.x - 1][heros.y]) {
                    heros.x--;
                    herosABouge = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (heros.x < SIZE - 1 && !murs[heros.x + 1][heros.y]) {
                    heros.x++;
                    herosABouge = true;
                }
                break;
        }

        verifierCollision();
        verifierVictoire();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Dessine le labyrinthe
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (murs[i][j]) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Dessine le héros en rose
        g.setColor(Color.PINK);
        g.fillRect(heros.x * CELL_SIZE, heros.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Dessine le monstre
        g.setColor(Color.RED);
        g.fillRect(monstre.x * CELL_SIZE, monstre.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Labyrinthe jeu = new Labyrinthe();
            jeu.setVisible(true);
        });
    }
}
