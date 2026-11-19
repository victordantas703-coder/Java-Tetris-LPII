package gameobject;

import java.awt.Color;

public class Shape_Square extends Shape {

    public Shape_Square() {
        createShape(Color.yellow);
    }

    public void setBlocksPosition(int x, int y) {

        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() + Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX(), blocks[0].getY() + Block.BLOCK_SIZE);
        blocks[3].setPosition(blocks[1].getX(), blocks[2].getY());

    }

}