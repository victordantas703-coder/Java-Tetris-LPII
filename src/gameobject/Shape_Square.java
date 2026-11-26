package gameobject;

import java.awt.Color;

public class Shape_Square extends Shape {

    // ▢▢ (amarelo)
    // ▢▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {0, 1}, {1, 1} };

    public Shape_Square() {
        super(Color.yellow, SPAWN_OFFSETS, 1);
    }

}