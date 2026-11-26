package gameobject;

import java.awt.Color;

public class Shape_LD extends Shape {
    
    // ▢▢▢ (laranja)
    // ▢
    private static final int[][] SPAWN_OFFSETS = { {0, 0}, {1, 0}, {-1, 0}, {-1, 1} };

    public Shape_LD() {
        super(Color.ORANGE, SPAWN_OFFSETS, 4);
    }

}
