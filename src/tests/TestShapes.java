package tests;

import gameobject.*;

public class TestShapes {

    public static void main(String[] args) {
        Shape[] shape = {
            new Shape_Bar(), new Shape_Square(), new Shape_T(),
            new Shape_LE(), new Shape_LD(), new Shape_ZE(), new Shape_ZD()
        };

        for (Shape p : shape) {
            p.setBlocksPosition(90, 0);   // pivô na coluna 3, linha 0
            System.out.println(p.getClass().getSimpleName());
            draw(p);
            System.out.println();
        }
    }

    private static void draw(Shape p) {
        char[][] grid = new char[2][7];
        for (char[] linha : grid) java.util.Arrays.fill(linha, '.');

        for (Block b : p.blocks) {
            int x = b.getX() / Block.BLOCK_SIZE;
            int y = b.getY() / Block.BLOCK_SIZE;
            grid[y][x] = '#';
        }
        for (char[] linha : grid) System.out.println(new String(linha));
    }
}

/* javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp bin tests.TestShapes */