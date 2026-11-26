package gameobject;

import java.awt.Color;

public class Shape_T extends Shape {

    // ▢▢▢ (magenta)
    //  ▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {-1, 0}, {0, 1} };

    public Shape_T() {
        super(Color.magenta, SPAWN_OFFSETS, 4);
    }

}
