package gameobject;

import java.awt.Color;

public class Shape_ZD extends Shape {

    public Shape_ZD() {
        createShape(Color.green);
    }

    public void setBlocksPosition(int x, int y) {
        //  ▢▢ (verde)
        // ▢▢
        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() + Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX(), blocks[0].getY() + Block.BLOCK_SIZE);
        blocks[3].setPosition(blocks[2].getX() - Block.BLOCK_SIZE, blocks[2].getY());

    }

}
