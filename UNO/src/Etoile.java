
public class Etoile extends Objet{
	
	public Etoile(int x, int y) {
		super(x, y);
	}

	public int AugmenterVie(int niveau) {
		int nbVie;
		if (niveau==1) {
			nbVie=3;
		}
		else if (niveau==2) {
			nbVie=2;
		}
		else {
			nbVie=1;
		}
		return nbVie;
		

	}

}