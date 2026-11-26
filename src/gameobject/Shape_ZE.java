package gameobject;

import java.awt.Color;

public class Shape_ZE extends Shape {

    // ▢▢ (vermelho)
    //  ▢▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {-1, 0}, {0, 1}, {1, 1} };

    public Shape_ZE() {
        super(Color.red, SPAWN_OFFSETS, 2);
    }

}
