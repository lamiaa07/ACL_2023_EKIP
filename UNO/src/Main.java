import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int longueur = 12;
        int hauteur = 12;
        int perimetre;

        Tresor t = new Tresor(hauteur - 2, longueur - 2);
        Heros h = new Heros(1, 1, 2, 1);

        System.out.print("Choisir son niveau : ");
        Scanner sca = new Scanner(System.in);
        String cmde = sca.nextLine();
        int niveau = Integer.valueOf(cmde);

        if (niveau == 1) {
            perimetre = 5;

            Plateau p = new Plateau(longueur, hauteur, niveau);

            Etoile etoile = new Etoile(0, 0);
            etoile.positionAleatoire(longueur, hauteur, p.getPlateau());

            Objet teleportation = new Objet(0, 0);
            teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());

            Objet attaque = new Objet(0, 0);
            attaque.positionAleatoire(longueur, hauteur, p.getPlateau());

            while (h.getVie() != 0 && !(t.getCoord().equals(h.getCoord()))) {
                afficherCoord(p, h, t, etoile, teleportation);
                System.out.println("Attaque: \t" + attaque.getCoord());
                h.deplacementHero(p.getPlateau());

                if (etoile.getCoord().equals(h.getCoord())) {
                    h.setVie(h.getVie() + etoile.AugmenterVie(niveau));
                    System.out.println("Tu gagnes un bonus de vie ! Ton nombre de vie : \t" + h.getVie());
                    etoile.positionAleatoire(longueur, hauteur, p.getPlateau());
                }

                if (teleportation.getCoord().equals(h.getCoord())) {
                    h.positionAleatoire(longueur, hauteur, p.getPlateau());
                    System.out.println("Tu as été téléporté ! Ta nouvelle case : \t" + h.getCoord());
                    teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
                }

                if (attaque.getCoord().equals(h.getCoord())) {
                    System.out.println("Attaque : \t" + attaque.getCoord());
                    attaque.positionAleatoire(longueur, hauteur, p.getPlateau());
                }
                System.out.println();
            }

            afficherCoord(p, h, t, etoile, teleportation);
            System.out.println("Attaque: \t" + attaque.getCoord());

            if (h.getVie() == 0) {
                System.out.println("GAME OVER");
            }

            if (t.getCoord().equals(h.getCoord())) {
                System.out.println("WIN");
            }
        } else if (niveau == 2) {
            perimetre = 4;

            Plateau p = new Plateau(longueur, hauteur, niveau);

            Etoile etoile = new Etoile(0, 0);
            etoile.positionAleatoire(longueur, hauteur, p.getPlateau());

            Objet teleportation = new Objet(0, 0);
            teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());

            Objet attaque = new Objet(0, 0);
            attaque.positionAleatoire(longueur, hauteur, p.getPlateau());
            System.out.println("Attaque : \t" + attaque.getCoord());

            while (h.getVie() != 0 && !(t.getCoord().equals(h.getCoord()))) {
                afficherCoord(p, h, t, etoile, teleportation);
                System.out.println("Attaque: \t" + attaque.getCoord());
                h.deplacementHero(p.getPlateau());

                if (etoile.getCoord().equals(h.getCoord())) {
                    h.setVie(h.getVie() + etoile.AugmenterVie(niveau));
                    System.out.println("Tu gagnes un bonus de vie ! Ton nombre de vie : \t" + h.getVie());
                }

                if (teleportation.getCoord().equals(h.getCoord())) {
                    h.positionAleatoire(longueur, hauteur, p.getPlateau());
                    System.out.println("Tu as été téléporté ! Ta nouvelle case : \t" + h.getCoord());
                    teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
                }
                if (attaque.getCoord().equals(h.getCoord())) {
                    System.out.println("Attaque : \t" + attaque.getCoord());
                    attaque.positionAleatoire(longueur, hauteur, p.getPlateau());
                }
                System.out.println();
            }
            afficherCoord(p, h, t, etoile, teleportation);
            System.out.println("Attaque: \t" + attaque.getCoord());

            if (h.getVie() == 0) {
                System.out.println("GAME OVER");
            }

            if (t.getCoord().equals(h.getCoord())) {
                System.out.println("WIN");
            }
        } else if (niveau == 3) {
            perimetre = 3;

            Plateau p = new Plateau(longueur, hauteur, niveau);

            Etoile etoile = new Etoile(0, 0);
            etoile.positionAleatoire(longueur, hauteur, p.getPlateau());

            Objet teleportation = new Objet(0, 0);
            teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
            System.out.println("case teleportation : \t" + teleportation.getCoord());

            while (h.getVie() != 0 && !(t.getCoord().equals(h.getCoord()))) {
                afficherCoord(p, h, t, etoile, teleportation);
                h.deplacementHero(p.getPlateau());

                if (etoile.getCoord().equals(h.getCoord())) {
                    h.setVie(h.getVie() + etoile.AugmenterVie(niveau));
                    System.out.println("Tu gagnes un bonus de vie ! Ton nombre de vie : \t" + h.getVie());
                }

                if (teleportation.getCoord().equals(h.getCoord())) {
                    h.positionAleatoire(longueur, hauteur, p.getPlateau());
                    System.out.println("Tu as été téléporté ! Ta nouvelle case : \t" + h.getCoord());
                    teleportation.positionAleatoire(longueur, hauteur, p.getPlateau());
                }

                System.out.println();
            }

            afficherCoord(p, h, t, etoile, teleportation);

            if (h.getVie() == 0) {
                System.out.println("GAME OVER");
            }

            if (t.getCoord().equals(h.getCoord())) {
                System.out.println("WIN");
            }
        } else {
            System.out.println("Le niveau n'existe pas");
        }
    }

    static void afficherCoord(
            Plateau p, Heros h, Tresor t, Etoile etoile, Objet teleportation) {
        System.out.println("Obstacles : \t" + p.getObstacles());
        System.out.println("Tresor : \t" + t.getCoord());
        System.out.println("Heros : \t" + h.getCoord());
        System.out.println("Etoile : \t" + etoile.getCoord());
        System.out.println("Teleportation :\t" + teleportation.getCoord());
    }
}




