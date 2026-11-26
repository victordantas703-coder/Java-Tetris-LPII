package world;

import gameobject.Block;
import gameobject.Shape;

public class Board {

    public static final int BOARD_HEIGHT = 20;
    public static final int BOARD_WIDTH = 10;

    private Block[][] cells = new Block[BOARD_HEIGHT][BOARD_WIDTH];
    private final boolean[] markedRows = new boolean[BOARD_HEIGHT];

    public boolean isInside(int column, int row) {
        return column >= 0 && column < BOARD_WIDTH && row >= 0 && row < BOARD_HEIGHT;
    }

    public Block getBlock(int column, int row) {
        return isInside(column, row) ? cells[row][column] : null;
    }

    public void setBlock(int column, int row, Block block) {
        if (!isInside(column, row))
            throw new IllegalArgumentException("Celula fora do tabuleiro: " + column + "," + row);
        cells[row][column] = block;
        if (block != null)
            block.setPosition(column, row);
    }

    public boolean isFree(int column, int row) {
        return isInside(column, row) && cells[row][column] == null;
    }

    public boolean canPlace(Shape shape) {
        for (Block block : shape.getBlocks()) {
            if (!isFree(block.getX(), block.getY()))
                return false;
        }
        return true;
    }

    public void lock(Shape shape) {
        for(Block block : shape.getBlocks()) {
            setBlock(block.getX(), block.getY(), block);
        }
    }

    public boolean isRowComplete(int row) {
        for (int column = 0; column < BOARD_WIDTH; column++) {
            if (cells[row][column] == null) 
                return false;
        }
        return true;
    }

    public int markCompletedRows() {
        int count = 0;
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            markedRows[row] = isRowComplete(row);
            if (markedRows[row])
                count++;
        }
        return count;
    }

    public boolean isRowMarked(int row) {
        return row >= 0 && row < BOARD_HEIGHT && markedRows[row];
    }

    public int removeMarkedRows() {
        Block[][] result = new Block[BOARD_HEIGHT][BOARD_WIDTH];
        int removed = 0;
        int targetRow = BOARD_HEIGHT - 1;

        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            if (markedRows[row]) {
                removed++;
                continue;
            }
            for (int column = 0; column < BOARD_WIDTH; column++) {
                Block block = cells[row][column];
                result[targetRow][column] = block;
                if (block != null)
                    block.setPosition(column, targetRow);
            }
            targetRow--;
        }

        cells = result;
        java.util.Arrays.fill(markedRows, false);
        return removed;
    }

    public void clear() {
        cells = new Block[BOARD_HEIGHT][BOARD_WIDTH];
        java.util.Arrays.fill(markedRows, false);
    }

}
