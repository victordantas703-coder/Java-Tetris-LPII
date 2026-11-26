package gameobject;

import java.awt.Color;

public class Shape_Bar extends Shape {

    // ▢▢▢▢ (ciano)
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {-1, 0}, {2, 0} };

    public Shape_Bar() {
        super(Color.cyan, SPAWN_OFFSETS, 2);
    }

}
