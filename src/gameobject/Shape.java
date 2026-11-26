package gameobject;

import enums.CommandsEnum;

import java.awt.Color;


public abstract class Shape extends GameObject {

    private static final int SHAPE_BLOCKS_QUANTITY = 4;

    private final Block[] blocks = new Block[SHAPE_BLOCKS_QUANTITY];
    private final int[][][] rotationStates; // 1° parametro: rotationIndex, 2° e 3° paramêtros: offsets do rotationIndex específico
    private int rotationIndex = 0; // estado de rotação do bloco, começa na forma que foi formado 

    protected Shape(Color blockColor, int[][] spawnOffsets, int rotationStatesQuantity) {
        
        for (int i = 0; i < SHAPE_BLOCKS_QUANTITY; i++) {
            this.blocks[i] = new Block(blockColor);
        }

        this.rotationStates = new int[rotationStatesQuantity][][];
        this.rotationStates[0] = spawnOffsets;
        for (int i = 1; i < rotationStatesQuantity; i++) {
            this.rotationStates[i] = rotateClockwise(this.rotationStates[i - 1]);
        }

        refreshBlocksPosition();
    }

    private static int[][] rotateClockwise(int[][] offsets) {
        
        int[][] rotated = new int[offsets.length][];
        for (int i = 0; i < offsets.length; i++) {
            rotated[i] = new int[] { -offsets[i][1], offsets[i][0] };
        }

        return rotated;
    }

    public Block[] getBlocks() {
        return blocks.clone();
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        refreshBlocksPosition();
    }

    public void move(int deltaColumns, int deltaRows) {
        setPosition(getX() + deltaColumns, getY() + deltaRows);
    }

    public void updateBlocksPosition(CommandsEnum command) {
        switch (command) {
            case LEFT -> move(-1, 0);
            case RIGHT -> move(1, 0);
            case ROTATE_CLOCKWISE -> rotate(1);
            case ROTATE_COUNTERCLOCKWISE -> rotate(-1);
            default -> throw new IllegalArgumentException("Command does not move the shape: " + command);
        }
    }

    public void updateFall() {
        move(0, 1);
    }

    private void rotate(int step) {
        rotationIndex = Math.floorMod(rotationIndex + step, rotationStates.length);
        refreshBlocksPosition();
    }

    private void refreshBlocksPosition() {
        int[][] offsets = rotationStates[rotationIndex];
        for (int i = 0; i < SHAPE_BLOCKS_QUANTITY; i++) {
            blocks[i].setPosition(getX() + offsets[i][0], getY() + offsets[i][1]);
        }
    }

}
