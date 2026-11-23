package gameobject;

import java.awt.Color;

public class Shape_LE extends Shape {

    public Shape_LE() {
        createShape(Color.blue);
    }

    public void setBlocksPosition(int x, int y) {
        // ▢▢▢ (azul)
        //    ▢
        blocks[0].setPosition(x, y);
        blocks[1].setPosition(blocks[0].getX() + Block.BLOCK_SIZE, blocks[0].getY());
        blocks[2].setPosition(blocks[0].getX() - Block.BLOCK_SIZE, blocks[0].getY());
        blocks[3].setPosition(blocks[1].getX(), blocks[1].getY() + Block.BLOCK_SIZE);

    }

}
