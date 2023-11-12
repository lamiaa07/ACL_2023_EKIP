
import java.util.ArrayList;

public class Personnage extends Position {


	private int vie;
	private int attaque;

	public Personnage (int x,int y, int vie,int attaque) {
		super(x, y);
		this.vie=vie;
		this.attaque=attaque;
	}

	public ArrayList<Integer> position(String cmd) {
		ArrayList<Integer> coord = new ArrayList<Integer>(getCoord());
		if(cmd.equals("z")) {
			coord.set(1,coord.get(1)+1);
		}
		if(cmd.equals("q")) {
			coord.set(0,coord.get(0)-1);
		}
		if(cmd.equals("s")) {
			coord.set(1,coord.get(1)-1);
		}
		if(cmd.equals("d")) {
			coord.set(0,coord.get(0)+1);
		}
		return coord;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

}
