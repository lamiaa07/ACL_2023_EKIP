


class Heros extends Personne {
    private int vies;

    Heros(int x, int y) {
        super(x, y);
        this.vies = 3; // Initialise le nombre de vies à 3
    }

    int getVies() {
        return vies;
    }

    void perdreVie() {
        vies--;
    }
    
    public void incrementeVie() {
        vies++;
    }
	@Override
	void deplacerAleatoirement(boolean[][] murs) {
		// TODO Auto-generated method stub
		
	}
}
