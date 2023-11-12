import java.util.ArrayList;
import java.util.List;

public class Position {

	int x;
	int y;

	public Position(int x,int y) {
		this.x=x;
		this.y=y;
	}

	public boolean mouvPossible(ArrayList<List<Integer>> plateau, ArrayList<Integer> coord) {
		boolean boule=false;
		for (List<Integer> element : plateau) {
			if (element.equals(coord))
				boule=true;
		}
		return boule;
	}

	public boolean Perimetre(ArrayList<Integer> coord1, ArrayList<Integer> coord2,int perimetre) {
		boolean boule=false;

		for(int i =-perimetre;i<=perimetre;i++) {
			if (coord1.get(0)==coord2.get(0)+i && coord1.get(1)==coord2.get(1))
				boule=false;
			if  (coord1.get(0)==coord2.get(0) && coord1.get(1)==coord2.get(1)+i)
				boule=false;
			if  (coord1.get(0)==coord2.get(0)+i && coord1.get(1)==coord2.get(1)+i)
				boule=false;
			if  (coord1.get(0)==coord2.get(0)+i && coord1.get(1)==coord2.get(1)-i)
				boule=false;
			else boule=true;
		}
		return boule;
	}

	public void positionAleatoire(int longueur, int hauteur, ArrayList<List<Integer>> plateau){
		// le mur se positionne sur [0,1:hauteur]; [0:longueur,0], [longueur,0:hauteur], [0:longueur,hauteur]
		ArrayList<Integer> coordAlea;
		do {
			coordAlea = new ArrayList<Integer>();
			coordAlea.add((int)(Math.random()*(longueur-1))+1);			 
			coordAlea.add((int)(Math.random()*(hauteur-1))+1);
		}
		while(mouvPossible(plateau, coordAlea)==false);
		setCoord(coordAlea);
	}
	
	public boolean perimetre(ArrayList<Integer> coordHero,int perimetre) {
		boolean boule=true;
		for (int i=coordHero.get(0)-perimetre; i<=coordHero.get(0)+perimetre; i++) {
			for (int j=coordHero.get(1)-perimetre; j<=coordHero.get(1)+perimetre; j++) {
				if (x==i && y==j) boule=false;
			}
		}
		return boule;
	}
	
	public void testPositionPerimetre(ArrayList<Integer> coordHero, ArrayList<List<Integer>> plateau, int perimetre, int longueur, int hauteur) {
		if (perimetre<=7) {
			do {
				positionAleatoire(longueur, hauteur, plateau);
			} while (!(perimetre(coordHero,perimetre)));	
		} else
			System.out.println("Mauvaise taille de perimetre");
			//System.exit(0);
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public ArrayList<Integer> getCoord(){
		ArrayList<Integer> coord = new ArrayList<Integer>();
		coord.add(x);
		coord.add(y);
		return coord;
	}

	public void setX(int x){
		this.x=x;
	}

	public void setY(int y){
		this.y=y;
	}

	public void setCoord(ArrayList<Integer> coord){
		setX(coord.get(0));
		setY(coord.get(1));
	}
}
