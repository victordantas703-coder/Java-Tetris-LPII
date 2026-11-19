package gameobject;

public class GameObject {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

}
