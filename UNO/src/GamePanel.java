import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    int perimetre;
    int nb_obstacles;
    int niveau;

    Plateau p;
    ArrayList<List<Integer>> plateau;
    ArrayList<List<Integer>> obstacles;

    Tresor t = new Tresor(hauteur - 2, hauteur - 2);
    Heros h = new Heros(1, 1, 2, 1);
    Fantome f = new Fantome(0, 0, 3, 1);

    Etoile etoile = new Etoile(0, 0);
    Boolean etoile_v = true;

    Objet teleportation = new Objet(0, 0);
    Objet attaque = new Objet(0, 0);

    static final int UNIT_SIZE = 50;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 50;
    static final int temps_depl_f = 400;
    static final int longueur = SCREEN_WIDTH / UNIT_SIZE;
    static final int hauteur = SCREEN_HEIGHT / UNIT_SIZE;
    int time = -DELAY;
    boolean running = false;
    int win = 0;
    Timer timer;

    GamePanel(int niveau) {
        this.niveau = niveau;
        if (niveau == 1) {
            perimetre = 5;
            nb_obstacles = 7;
        } else if (niveau == 2) {
            perimetre = 4;
            nb_obstacles = 15;
        } else if (niveau == 3) {
            perimetre = 3;
            nb_obstacles = 20;
        }
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        p = new Plateau(longueur, hauteur, niveau);
        plateau = p.getPlateau();
        obstacles = p.getObstacles();

        f.testPositionPerimetre(h.getCoord(), p.getPlateau(), perimetre, longueur, hauteur);
        etoile.positionAleatoire(longueur, hauteur, p.getPlateau());
        teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
        attaque.positionAleatoire(longueur, hauteur, p.getPlateau());
        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            checkTresor();
            checkMort();
            checkTeleporteur();
            checkAttaque();
            if (time % temps_depl_f == 0) {
                f.deplacementFantome(p);
                //checkTouche_f();
            }
            if (etoile_v) {
                checkEtoile();
            }
            time += DELAY;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            draw_lines(g);
            draw_obst(g);
            draw_vie(g);
            draw_t(g);
            draw_f(g);
            if (etoile_v) {
                draw_etoile(g);
            }
            draw_tel(g);
            draw_h(g);
            draw_att(g);
        } else {
            gameOver(g);
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            String cmd = "";
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Q:
                    cmd = "q";
                    break;
                case KeyEvent.VK_D:
                    cmd = "d";
                    break;
                case KeyEvent.VK_Z:
                    cmd = "s";
                    break;
                case KeyEvent.VK_S:
                    cmd = "z";
                    break;
            }
            deplacementHero(plateau, cmd);
        }
    }

    public void gameOver(Graphics g) {
        // Le joueur gagne
        if (win == 1) {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("C'est gagné", (SCREEN_WIDTH - metrics1.stringWidth("C'est gagné")) / 2,
                    SCREEN_HEIGHT / 2);
        }
        // Le joueur a perdu
        else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("C'est perdu", (SCREEN_WIDTH - metrics2.stringWidth("C'est perdu")) / 2,
                    SCREEN_HEIGHT / 2);
        }
    }

    // dessine les lignes
    public void draw_lines(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);
        }
    }

    // dessine les coeurs du héros
    public void draw_vie(Graphics g) {
        g.setColor(Color.red);
        for (int i = 0; i < h.getVie(); i++) {
            g.fillOval(UNIT_SIZE * (longueur - 1 - i), 0, 30, 30);
            g.fillOval(UNIT_SIZE * (longueur - 1 - i) + 20, 0, 30, 30);
            int[] x = { UNIT_SIZE * (longueur - 1 - i) + UNIT_SIZE / 2,
                    UNIT_SIZE * (longueur - 1 - i) + UNIT_SIZE - 5, UNIT_SIZE * (longueur - 1 - i) + UNIT_SIZE / 2,
                    UNIT_SIZE * (longueur - 1 - i) + 5 };
            int[] y = { 5, UNIT_SIZE / 2, UNIT_SIZE - 5, UNIT_SIZE / 2 };
            g.fillPolygon(x, y, 4);
        }
    }

    // dessine les obstacles (et bords)
    public void draw_obst(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/mur.jpg"));
        } catch (IOException e) {
        }
        for (int i = 0; i < nb_obstacles; i++) {
            g.drawImage(image, UNIT_SIZE * obstacles.get(i).get(0), UNIT_SIZE * obstacles.get(i).get(1),
                    UNIT_SIZE, UNIT_SIZE, null);
        }
        for (int i = 0; i < longueur; i++) {
            g.drawImage(image, UNIT_SIZE * i, 0, UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(image, UNIT_SIZE * i, UNIT_SIZE * (hauteur - 1), UNIT_SIZE, UNIT_SIZE, null);
        }
        for (int i = 0; i < hauteur; i++) {
            g.drawImage(image, 0, UNIT_SIZE * i, UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(image, UNIT_SIZE * (longueur - 1), UNIT_SIZE * i, UNIT_SIZE, UNIT_SIZE, null);
        }
    }

    // dessine le fantôme
    public void draw_f(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/fantome.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * f.getX(), UNIT_SIZE * f.getY(), UNIT_SIZE, UNIT_SIZE, null);
    }

    // dessine le trésor
    public void draw_t(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/tresor.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * t.getX(), UNIT_SIZE * t.getY(), UNIT_SIZE, UNIT_SIZE, null);
    }

    // dessine le héros
    public void draw_h(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/hero.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * h.getX(), UNIT_SIZE * h.getY(), UNIT_SIZE, UNIT_SIZE, null);
    }

    // dessine l'etoile
    public void draw_etoile(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/etoile.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * etoile.getX(), UNIT_SIZE * etoile.getY(), UNIT_SIZE, UNIT_SIZE, null);
    }

    // dessine le portail
    public void draw_tel(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/portail.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * teleportation.getX(), UNIT_SIZE * teleportation.getY() + 5, UNIT_SIZE,
                UNIT_SIZE - 10, null);
    }

    // dessine Attaque
    public void draw_att(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/attaque.png"));
        } catch (IOException e) {
        }
        g.drawImage(image, UNIT_SIZE * attaque.getX(), UNIT_SIZE * attaque.getY(), UNIT_SIZE, UNIT_SIZE, null);
    }

    public void deplacementHero(ArrayList<List<Integer>> plateau, String cmd) {
        ArrayList<Integer> tesCoord;
        tesCoord = h.position(cmd);
        if (h.mouvPossible(plateau, tesCoord)) {
            h.setCoord(tesCoord);
        }
    }

    // vérifie si le héros est sur le trésor
    public void checkTresor() {
        if (t.getCoord().equals(h.getCoord())) {
            running = false;
            win = 1;
        }
    }

    // vérifie si le heros n'a plus de vies
    public void checkMort() {
        if (h.getVie() <= 0) {
            running = false;
        }
    }

    // vérifie si le heros prend l'étoile
    public void checkEtoile() {
        if (etoile.getCoord().equals(h.getCoord())) {
            h.setVie(h.getVie() + etoile.AugmenterVie(niveau));
            etoile_v = false;
        }
    }

    // vérifie si le héros est sur le teleporteur
    public void checkTeleporteur() {
        if (teleportation.getCoord().equals(h.getCoord())) {
            h.positionAleatoire(longueur, hauteur, p.getPlateau());
            teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
        }
    }

    // vérifie si le héros est sur une attaque
    public void checkAttaque() {
        if (attaque.getCoord().equals(h.getCoord())) {
            f.setVie(f.getVie() - attaque.PuissanceAtt(niveau));
            if (f.getVie() <= 0) {
                f.setX(t.getX());
                f.setY(t.getY());
                f.setVie(1);
            }
            attaque.positionAleatoire(longueur, hauteur, p.getPlateau());
        }
    }

    public void drawLabyrinth(Graphics g) {
        draw_lines(g);
        draw_obst(g);
    }

    public void paintComponent1(Graphics g) {
        super.paintComponent(g);
        drawLabyrinth(g);
        draw(g);
    }
}
