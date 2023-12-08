import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;



class Labyrinthe extends JFrame implements ActionListener, KeyListener {
    static int SIZE ;
    static int CELL_SIZE ;
     Tresor tresor;
    Heros heros;
    Monstre monstre;
    Monstre2 monstre2;
    boolean[][] murs;
    Fantome fantome;
     Piege piege;
    private int niveau;
    private boolean herosABouge;
    private static final int SIZE_NIVEAU_1 = 15;
    private static final int SIZE_NIVEAU_2 = 20;
    private static final int SIZE_NIVEAU_3 = 23;
    private static final int CELL_SIZE_NIVEAU_1 =30;
    private static final int CELL_SIZE_NIVEAU_2 =35;
    private static final int CELL_SIZE_NIVEAU_3 =37;
    //private List<Monstre> monstres;
    //private List<Fantome> fantomes;
    private AdvancedPlayer player;
    private Timer timer;
    int nombreAttrapages;
    private Image herosImage;
    private Image monstreImage;
    private Image tresorImage;
    private Image murImage;
    private Image solImage;
    private Image fantomeImage;
    private Image piegeImage;
    private Image monstre2Image;
    Help help;
    Labyrinthe() {
        setTitle("Labyrinth Game");
        demanderNiveau(); // Demander le niveau en premier
        initialiserTailleFenetre(); // Initialiser la taille de la fenêtre en fonction du niveau
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initialiserJeu();
        jouerMusique("Musique/musi1.mp3");
        
        	// Timer

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
         piegeImage = new ImageIcon("Images/piege.png").getImage();
         monstre2Image = new ImageIcon("Images/monstre2.png").getImage();
         
    }
    
    private void initialiserTailleFenetre() {
        switch (niveau) {
            case 1:
                SIZE = SIZE_NIVEAU_1;
                CELL_SIZE = CELL_SIZE_NIVEAU_1;
                break;
            case 2:
                SIZE = SIZE_NIVEAU_2;
                CELL_SIZE = CELL_SIZE_NIVEAU_2;
                break;
            case 3:
                SIZE = SIZE_NIVEAU_3;
                CELL_SIZE = CELL_SIZE_NIVEAU_3;
                break;
        }
        setSize(SIZE * CELL_SIZE, SIZE * CELL_SIZE);
        
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
     // Add Monstre2 instance
        monstre2 = new Monstre2(1, SIZE - 2);
        
        placerPiege();
        help = Help.creerAideAleatoire(murs, heros);
        
    }

    
    private void placerPiege() {
        Random random = new Random();
        int nombrePieges = niveau * 2; // Niveau 1: 2 pièges, Niveau 2: 4 pièges, Niveau 3: 6 pièges
        for (int i = 0; i < nombrePieges; i++) {
            piege = new Piege(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
        }
        repaint();
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
    
    private void placerMonstre2() {
        Random random = new Random();
        do {
            monstre2 = new Monstre2(random.nextInt(SIZE - 2) + 1, random.nextInt(SIZE - 2) + 1);
        } while (monstre2.x == heros.x && monstre2.y == heros.y);
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

        int nombreVies = heros.getVies(); // Obtenir le nombre actuel de vies de l'héros

        for (int i = 0; i < (nombreVies -nombreAttrapages) ; i++) {
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
        while (mursPlaces < (SIZE * SIZE) / 9)  {
            int x = random.nextInt(SIZE - 2) + 1;
            int y = random.nextInt(SIZE - 2) + 1;

            if (!murs[x][y]) {
                murs[x][y] = true;
                mursPlaces++;
            }
        }
    }
    
    
    private void traitementCollisionPiege() {
        
    	 
    	
    	heros.perdreVie();
        
        // Vous pouvez retirer la ligne suivante pour ne pas réinitialiser le jeu à chaque collision
        // initialiserJeu();
        
        if ( nombreAttrapages < heros.getVies()) {
            JOptionPane.showMessageDialog(this, "You fell into a trap! You have " + (heros.getVies()-nombreAttrapages) + " lives left. Stay alert,you lost 2 lives!");
        } else {
            JOptionPane.showMessageDialog(this, "Game Over! No more lives remaining.");
            timer.stop();
            System.exit(0);
        }
        
        repaint();
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
        
     // Vérifier la collision avec le fantôme
        if (heros.x == piege.x && heros.y == piege.y) {
            traitementCollisionPiege();
        }
        
     // Vérifier la collision avec l'aide
         if (help != null && help.estRencontre(heros)) {
        	nombreAttrapages--;
            //help.incrementerVie(heros);
            help = null; // Supprimer l'aide après la rencontre
        }
        
         // Vérifier la collision avec le monstre2
         if (heros.x == monstre2.x && heros.y == monstre2.y) {
             traitementCollision();
         }
        
          }
    
    
   
   
   
    private void traitementCollision() {
        if ((heros.x == monstre.x && heros.y == monstre.y) ||
            (heros.x == fantome.x && heros.y == fantome.y) ||
            (heros.x == piege.x && heros.y == piege.y)||
            (heros.x == monstre2.x && heros.y == monstre2.y)){

            nombreAttrapages++;
            heros.perdreVie();

            initialiserJeu();

            if (nombreAttrapages < heros.getVies()) {
                JOptionPane.showMessageDialog(this, " You are caught! You have " + (heros.getVies() - nombreAttrapages) + " lives left. Stay alert !");
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Game Over! Caught 3 times.");
                timer.stop();
                System.exit(0);
            }
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
            // Déplacer les monstres et le fantôme
            fantome.deplacerAleatoirement(murs);
            monstre.deplacerIntelligemment(murs, heros);
            fantome.deplacerIntelligemment(murs, heros);
            monstre2.deplacerIntelligemment(murs, heros);
            //monstre2.estDeplacementValide(murs);
            // Vérifier si le déplacement du fantôme est valide
            fantome.estDeplacementValide(murs);

            // Vérifier la collision après chaque déplacement du héros
            verifierCollision();
            verifierVictoire();

            herosABouge = false;
        }

        // Redessiner l'interface
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
        
        // Dessine le piège
        g.drawImage(piegeImage, piege.x * CELL_SIZE, piege.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
        
     // Dessine le Monstre2
        g.drawImage(monstre2Image, monstre2.x * CELL_SIZE, monstre2.y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        
     /// Dessiner l'aide
        if (help != null) {
            help.dessiner(g, CELL_SIZE);
        }
    }
    
    private void jouerMusique(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            player = new AdvancedPlayer(fileInputStream, javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // You can add actions to perform after the music playback here
                }
            });
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Labyrinthe jeu = new Labyrinthe();
            jeu.setVisible(true);
        });
    }
}