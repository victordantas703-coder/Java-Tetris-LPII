package gameobject;

import enums.CommandsEnum;

import java.awt.Color;


public abstract class Shape extends GameObject {

    public Block blocks[] = new Block[4];
    private static final int SHAPE_BLOCKS_QUANTITY = 4;

    public void createShape(Color blockColor){

        this.blocks[0] = new Block(blockColor);
        this.blocks[1] = new Block(blockColor);
        this.blocks[2] = new Block(blockColor);
        this.blocks[3] = new Block(blockColor);

    }

    public abstract void setBlocksPosition(int x, int y);
    public void updateBlocksPosition(CommandsEnum direction) {
        if(direction == CommandsEnum.LEFT) {
            for (int i = 0; i < SHAPE_BLOCKS_QUANTITY; i++) {
                this.blocks[i].setPosition(blocks[i].getX() - Block.BLOCK_SIZE, blocks[i].getY());
            }
        }
        else if(direction == CommandsEnum.RIGHT) {
            for (int i = 0; i < SHAPE_BLOCKS_QUANTITY; i++) {
                this.blocks[i].setPosition(blocks[i].getX() + Block.BLOCK_SIZE, blocks[i].getY());
            }
        }
    }

    public void updateBlockFall(){
        for (int i = 0; i < SHAPE_BLOCKS_QUANTITY; i++) {
            this.blocks[i].setPosition(blocks[i].getX(), blocks[i].getY() + Block.BLOCK_SIZE);
        }
    }

    // y do bloco mais baixo + o tamanho de um bloco, saber quando a peça chegou ao final da tela
    public int getBottomY() {
        int lowestY = blocks[0].getY();
        for (int i = 1; i < SHAPE_BLOCKS_QUANTITY; i++) {
            if (blocks[i].getY() > lowestY) {
                lowestY = blocks[i].getY();
            }
        }
        return lowestY + Block.BLOCK_SIZE;
    }

}
