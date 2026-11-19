package gameobject;

import java.awt.Color;

public class Shape_Bar extends Shape {

    public Shape_Bar() {
        createShape(Color.cyan);
    }

    public void setBlocksPosition(int x, int y) {

        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() + Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX() - Block.BLOCK_SIZE, blocks[0].getY());
        blocks[3].setPosition(blocks[1].getX() + Block.BLOCK_SIZE, blocks[1].getY());

    }

}
