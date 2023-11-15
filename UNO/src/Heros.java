


class Heros extends Personne {
    private int vies;

    Heros(int x, int y) {
        super(x, y);
        this.vies = 3; // Initialise le nombre de vies à 4
    }

    int getVies() {
        return vies;
    }

    void perdreVie() {
        vies--;
    }

	@Override
	void deplacerAleatoirement(boolean[][] murs) {
		// TODO Auto-generated method stub
		
	}
}
