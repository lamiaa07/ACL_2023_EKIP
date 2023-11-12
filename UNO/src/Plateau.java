import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
	Random random = new Random();
	int nb1;
	int nb2;
	private int longueur; 	// definition de la longueur du plateau
	private int hauteur; 	// definition de la hauteur du plateau
	private int niveau; 	// definition du niveau 1 2 ou 3

	private ArrayList<List<Integer>> plateau = new ArrayList<List<Integer>>(); //initialisation du plateau
	private ArrayList<List<Integer>> obstacles = new ArrayList<List<Integer>>(); //initialisation des obstacles
	private ArrayList<List<Integer>> pasObstacles = new ArrayList<List<Integer>>();


	public Plateau(int longeur, int hauteur,int niveau) {
		this.hauteur=hauteur;
		this.longueur=longeur;
		this.niveau=niveau;
		ajoutObstacle();
	}
	//creation de la liste ou on ne veut pas d'obstacle
	public void pasDispo() { 
		//coin en bas a gauche
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(0).add(1);
		pasObstacles.get(0).add(1);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(1).add(1);
		pasObstacles.get(1).add(2);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(2).add(2);
		pasObstacles.get(2).add(1);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(3).add(2);
		pasObstacles.get(3).add(2);
		
		//coin en haut a droite
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(4).add(hauteur-2);
		pasObstacles.get(4).add(longueur-2);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(5).add(hauteur-2);
		pasObstacles.get(5).add(longueur-3);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(6).add(hauteur-3);
		pasObstacles.get(6).add(longueur-2);
		pasObstacles.add(new ArrayList<Integer>());
		pasObstacles.get(7).add(hauteur-3);
		pasObstacles.get(7).add(longueur-3 );
	}
	
	public void obstacle() {
		pasDispo();
		// determination du nombre d'obstacles en fonction du niveau
		int nbObstacles;
		if (niveau==1) {
			nbObstacles=7;
		}
		else if (niveau==2) {
			nbObstacles=15;
		}
		else {
			nbObstacles=20;
		}

		// creation de la liste d'obstacle
		ArrayList<Integer> test;
		for (int k=0;k<nbObstacles;k++) {
			// creation des coordonnees d'un obstacle hors des murs + test si il n'est pas deja dans la liste d'obstacle
			obstacles.add(new ArrayList<Integer>());
			// verificaation si test n'est pas deja dans obstacles
			do {
				test = new ArrayList<Integer>();
				nb1=random.nextInt(hauteur-2)+1;
				nb2=random.nextInt(longueur-2)+1;
				test.add(nb1);
				test.add(nb2);
			} while (obstacles.contains(test) || pasObstacles.contains(test));
			// on ajoute l'obstacle a la liste obstacles
			obstacles.get(k).add(nb1);
			obstacles.get(k).add(nb2);
		}		

		// tri de la liste dans l'ordre croissants des abscisses et ordonnees
		int taille = obstacles.size();  
		List<Integer> tmp = new ArrayList<Integer>();  
		for(int i1=0; i1 < taille; i1++) {
			for(int j1=1; j1 < (taille-i1); j1++) {  
				if((obstacles.get(j1-1).get(0) > obstacles.get(j1).get(0))||((obstacles.get(j1-1).get(0) == obstacles.get(j1).get(0))&&(obstacles.get(j1-1).get(1) > obstacles.get(j1).get(1)))) {
					//echanges des elements
					tmp = obstacles.get(j1-1);  
					obstacles.set(j1-1,obstacles.get(j1));  
					obstacles.set(j1,tmp);  
				} 
			}
		}
	}

	// creation de la liste plateau par default (le plateau est une liste des coordonnees sur lesquelles les personnages peuvent aller
	// Les murs sur la 0 et longueur colonnes et sur la 0 et largueur ligne
	public void creationPlateau(){ 	
		int z=0;
		for(int i=1; i<hauteur-1; i++) {			 				// creation du plateau avec toutes les valeurs sans obstacle et sans les murs
			for(int j=1; j<longueur-1; j++) {
				plateau.add(new ArrayList<Integer>());				// initialisation de nouvelles coordonnees
				plateau.get(z).add(i);								// ajout de la coordoonee x
				plateau.get(z).add(j);								// ajoute de la coordonnee y
				z+=1;												// comptage de la longeur de la liste plateau
			}
		}
	}

	// suppression des obstacles au plateau
	public void ajoutObstacle(){
		int compteur;
		int reduction=0;
		obstacle();
		creationPlateau();
		// enregistrement des positions dans la liste plateau des obstacles (index)
		List<Integer> enregistreur=new ArrayList<Integer>();
		for (List<Integer> coorObst:obstacles) {
			compteur=0;
			for(List<Integer> coorPlat:plateau){
				if ((coorObst.get(0)==coorPlat.get(0))&&(coorObst.get(1)==coorPlat.get(1))) {
					enregistreur.add(compteur);
				}
				compteur+=1;
			}
		}
		// suppression des positions obstacles sur le plateau en pensant qu'a chaque boucle la liste diminue sa taille de 1
		for (int element:enregistreur) {
			plateau.remove(element-reduction);
			reduction+=1;
		}
	}

	public ArrayList<List<Integer>> getPlateau() {
		return plateau;
	}

	public ArrayList<List<Integer>> getObstacles() {
		return obstacles;
	}

	public int getLongeur() {
		return longueur;
	}

	public int getHauteur() {
		return hauteur;
	}
}