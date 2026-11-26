package gameobject;

import java.awt.Color;

public class Shape_LE extends Shape {

    // ▢▢▢ (azul)
    //    ▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {-1, 0}, {1, 1} };

    public Shape_LE() {
        super(Color.blue, SPAWN_OFFSETS, 4);
    }

}
