

class Tresor {
    int x, y;

    Tresor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean estTrouve(Heros heros) {
        return heros.x == this.x && heros.y == this.y;
    }
}
