package gameobject;

import java.awt.Color;

public class Shape_T extends Shape {

    public Shape_T() {
        createShape(Color.magenta);
    }

    public void setBlocksPosition(int x, int y) {
        // ▢▢▢ (magenta)
        //  ▢
        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() + Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX() - Block.BLOCK_SIZE, blocks[0].getY());
        blocks[3].setPosition(blocks[0].getX(), blocks[0].getY() + Block.BLOCK_SIZE);

    }

}
