import java.util.ArrayList;

public class Objet extends Position {

	public Objet(int x, int y) {
		super(x, y);
	}
	public int PuissanceAtt(int niveau) {
		int Puiss=0;
		if (niveau==1) {
			Puiss=2;
		}
		else if (niveau==2) {
			Puiss=1;
		}
		
		return Puiss;
		

	}
}