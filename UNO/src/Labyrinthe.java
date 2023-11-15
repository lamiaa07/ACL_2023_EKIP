import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;






class Labyrinthe extends JFrame implements ActionListener, KeyListener {
    static final int SIZE = 15;
    private static final int CELL_SIZE = 35;
    private Tresor tresor;
    private Heros heros;
    private Monstre monstre;
    private boolean[][] murs;
    private Fantome fantome;
    private int niveau;
    private boolean herosABouge;
    
    
    //private List<Monstre> monstres;
    //private List<Fantome> fantomes;

    private Timer timer;
    private int nombreAttrapages;
    private Image herosImage;
    private Image monstreImage;
    private Image tresorImage;
    private Image murImage;
    private Image solImage;
    private Image fantomeImage;

    Labyrinthe() {
        setTitle("Labyrinth Game");
        setSize(SIZE * CELL_SIZE, SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        demanderNiveau();
        initialiserJeu();

        timer = new Timer(500, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
         nombreAttrapages = 0;
         herosImage = new ImageIcon("Images/Heros.png").getImage(); // Remplacez "heros.png" par le chemin de votre image de héros
         monstreImage = new ImageIcon("Images/Monstre.png").getImage(); // Remplacez "monstre.png" par le chemin de votre image de monstre
         tresorImage = new ImageIcon("Images/tresor.png").getImage(); // Remplacez "tresor.png" par le chemin de votre image de trésor
         murImage = new ImageIcon("Images/mur.png").getImage(); // Remplacez "mur.png" par le chemin de votre image de mur
         solImage = new ImageIcon("Images/sol.png").getImage(); // Remplacez "sol.png" par le chemin de votre image de sol
         fantomeImage = new ImageIcon("Images/Fantome.png").getImage();
    }
    private void demanderNiveau() {
    	 String niveauStr = JOptionPane.showInputDialog(this, "Choisissez le niveau (1, 2, ou 3):");

         // Ajouter une boucle pour s'assurer que le joueur entre un niveau valide
         while (true) {
             try {
                 niveau = Integer.parseInt(niveauStr);
                 if (niveau >= 1 && niveau <= 3) {
                     break; // Sortir de la boucle si le niveau est valide
                 } else {
                     niveauStr = JOptionPane.showInputDialog(this, "Veuillez entrer un niveau valide (1, 2, ou 3):");
                 }
             } catch (NumberFormatException e) {
                 niveauStr = JOptionPane.showInputDialog(this, "Veuillez entrer un nombre valide (1, 2, ou 3):");
             }
         }
     }
    private void initialiserJeu() {
    	
        heros = new Heros(1, 1);
        initialiserMurs();
        placerTresorAleatoirement();
        herosABouge = false;
        
        //monstres = new ArrayList<>();
        //fantomes = new ArrayList<>();
        
        int nombreMonstres = niveau;
        int nombreFantomes = niveau;

        for (int i = 0; i < nombreMonstres; i++) {
            placerMonstre();
        }

        for (int i = 0; i < nombreFantomes; i++) {
            placerFantome();
        }
    }

    

    private void placerFantome() {
        Random random = new Random();
        fantome = new Fantome(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
    }

    private void placerMonstre() {
        Random random = new Random();
        do {
            monstre = new Monstre(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
        } while (monstre.x == heros.x && monstre.y == heros.y);
    }
    private void dessinerTresor(Graphics g) {
        g.setColor(Color.YELLOW); // Vous pouvez changer la couleur du trésor selon vos préférences
        g.fillRect(tresor.x * CELL_SIZE, tresor.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
    private void placerTresorAleatoirement() {
        Random random = new Random();
        do {
            tresor = new Tresor(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
        } while (tresor.x == heros.x && tresor.y == heros.y);
    }
    private void dessinerCoeurs(Graphics g) {
        g.setColor(Color.RED); // Couleur des cœurs

        int x = 10; // Position x des cœurs dans la fenêtre
        int y = 10; // Position y des cœurs dans la fenêtre

        for (int i = 0; i < heros.getVies(); i++) {
            // Dessiner un cœur sous forme de deux cercles et un triangle
            g.fillOval(x, y, 20, 20);
            g.fillOval(x + 10, y, 20, 20);
            int[] triangleX = {x - 5, x + 25, x + 10};
            int[] triangleY = {y + 10, y + 10, y + 30};
            g.fillPolygon(triangleX, triangleY, 3);

            x += 30; // Espacement entre les cœurs
        }
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

        Random random = new Random();
        int mursPlaces = 0;

        // Générer des murs aléatoires à l'intérieur
        while (mursPlaces < 20) {
            int x = random.nextInt(SIZE - 2) + 1;
            int y = random.nextInt(SIZE - 2) + 1;

            if (!murs[x][y]) {
                murs[x][y] = true;
                mursPlaces++;
            }
        }
    }
   






    private void verifierCollision() {
        // Vérifier la collision avec le monstre
        if (heros.x == monstre.x && heros.y == monstre.y) {
            traitementCollision();
        }

        // Vérifier la collision avec le fantôme
        if (heros.x == fantome.x && heros.y == fantome.y) {
            traitementCollision();
        }
    }

    private void traitementCollision() {
        nombreAttrapages++;

        if (nombreAttrapages < 3) {
            JOptionPane.showMessageDialog(this, " You are arrested by the police! You have " + (3 - nombreAttrapages) + " lives left. Stay alert !");
            heros.perdreVie(); // Décrémenter le nombre de vies du héros
            initialiserJeu();

            // Si le héros a perdu une vie, redessinez l'interface
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Game Over! Caught 3 times.");
            timer.stop();
            System.exit(0); // Terminer le programme
        }
    }

    private void verifierVictoire() {
        if (tresor.estTrouve(heros)) {
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez trouvé le trésor. Victoire !");
            timer.stop();
            System.exit(0);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (herosABouge) {
            monstre.deplacerAleatoirement(murs);
            fantome.deplacerAleatoirement(murs);
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
                    g.drawImage(murImage, i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                } else {
                    g.drawImage(solImage, i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }

        // Dessine le trésor
        g.drawImage(tresorImage, tresor.x * CELL_SIZE, tresor.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        // Dessine le héros
        g.drawImage(herosImage, heros.x * CELL_SIZE, heros.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        
        // Dessine le monstre
        g.drawImage(monstreImage, monstre.x * CELL_SIZE, monstre.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        // Dessine le fantôme
        g.drawImage(fantomeImage, fantome.x * CELL_SIZE, fantome.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        // Dessine les cœurs
        dessinerCoeurs(g);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Labyrinthe jeu = new Labyrinthe();
            jeu.setVisible(true);
        });
    }
}