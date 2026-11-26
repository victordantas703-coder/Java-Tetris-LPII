package gameobject;

import java.awt.Color;

public class Shape_ZD extends Shape {

    //  ▢▢ (verde)
    // ▢▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {0, 1}, {-1, 1} };

    public Shape_ZD() {
        super(Color.green, SPAWN_OFFSETS, 2);
    }

}
