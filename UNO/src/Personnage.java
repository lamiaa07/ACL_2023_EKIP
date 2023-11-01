
public class Personnage {

	    private int pointsDeVie;
	    private int positionX;
	    private int positionY;
	    private int vitesseDeplacement;

	    public Personnage(int pointsDeVie, int positionX, int positionY, int vitesseDeplacement) {
	        this.pointsDeVie = pointsDeVie;
	        this.positionX = positionX;
	        this.positionY = positionY;
	        this.vitesseDeplacement = vitesseDeplacement;
	    }

	    public void deplacerHaut() {
	        positionY -= vitesseDeplacement;
	    }

	    public void deplacerBas() {
	        positionY += vitesseDeplacement;
	    }

	    public void deplacerGauche() {
	        positionX -= vitesseDeplacement;
	    }

	    public void deplacerDroite() {
	        positionX += vitesseDeplacement;
	    }

	    public void attaquer(Personnage cible) {
	        int degats = 10; // Vous pouvez ajuster les dégâts selon vos besoins
	        cible.subirDegats(degats);
	    }

	    public void subirDegats(int degats) {
	        pointsDeVie -= degats;
	        if (pointsDeVie < 0) {
	            pointsDeVie = 0;
	        }
	    }

	    public boolean estVivant() {
	        return pointsDeVie > 0;
	    }

	    // Ajoutez d'autres méthodes ou attributs spécifiques au personnage si nécessaire
	}


