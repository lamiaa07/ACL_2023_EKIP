
import java.util.ArrayList;
import java.util.Random;

public class Monstre extends Personnage {

	public Monstre(int x, int y, int vie, int attaque) {
		super(x, y, vie, attaque);
	}

	Random rand = new Random();

	public ArrayList<Integer> positionMonstre() {
		int r = rand.nextInt(5);
		String cmd = "";
		if (r == 0) {
			cmd = "z";			// Le monstre veut aller en haut
		}
		else if (r == 1) {
			cmd = "q";			// Le monstre veut aller a gauche
		}
		else if (r == 2) {
			cmd = "s";			// Le monstre veut aller en bas
		}
		else if (r == 3) {
			cmd = "d";			// Le monstre veut aller a droite
		}
		else {					// Le monstre ne bouge pas
		}
		return position(cmd);
	}
}