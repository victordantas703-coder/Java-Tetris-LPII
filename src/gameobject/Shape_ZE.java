package gameobject;

import java.awt.Color;

public class Shape_ZE extends Shape {

    public Shape_ZE() {
        createShape(Color.red);
    }

    public void setBlocksPosition(int x, int y) {
        // ▢▢ (vermelho)
        //  ▢▢
        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() - Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX(), blocks[0].getY() + Block.BLOCK_SIZE);
        blocks[3].setPosition(blocks[2].getX() + Block.BLOCK_SIZE, blocks[2].getY());

    }

}
