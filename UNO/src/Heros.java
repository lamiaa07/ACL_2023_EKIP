import java.util.Random;


public class Heros {
	
	    private int x; // Position en X
	    private int y; // Position en Y
	    private int health; // Points de vie

	    public Hero(int x, int y) {
	        this.x = x;
	        this.y = y;
	        this.health = 10; // Points de vie initiaux
	    }

	    public void move(int newX, int newY) {
	        // Méthode pour déplacer le héros
	        x = newX;
	        y = newY;
	    }

	    public void attack(Monster monster) {
	        // Méthode pour que le héros attaque un monstre
	        int heroDamage = new Random().nextInt(20) + 10; // Dégâts aléatoires entre 10 et 30
	        int monsterDamage = new Random().nextInt(15) + 5; // Dégâts aléatoires entre 5 et 20

	        // Réduire les points de vie du héros et du monstre
	        health -= monsterDamage;
	        monster.takeDamage(heroDamage);
	    }

	    public void takeDamage(int damage) {
	        // Méthode pour réduire les points de vie du héros
	        health -= damage;
	    }

	    public boolean isAlive() {
	        // Vérifier si le héros est en vie
	        return health > 0;
	    }

	    // Getters et setters pour les coordonnées du héros
	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }
	}



