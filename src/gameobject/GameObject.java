package gameobject;

public class GameObject {

    private int x;  // coluna onde o objeto esta
    private int y;  // linha onde o objeto esta

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

}
