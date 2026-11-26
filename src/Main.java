import javax.swing.SwingUtilities;

import gameobject.Shape;
import gameobject.ShapeFactory;
import tests.*;
import ui.GUIRenderer;
import world.AutoDrop;
import world.Score;
import world.Level;
import world.Lines;

public class Maingame {

    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int TETRIS = 4;

    private static final int BOARD_HEIGHT = 600;
    private static final int SPAWN_X = 120;
    private static final int SPAWN_Y = 0;

    private static AutoDrop autoDrop;

    public static void main(String[] args) {

        LevelTest.increseLevelTest();
        LinesTest.increaseLinesTest();
        ScoreTest.increaseScoreByLinesTest();
        ScoreTest.increaseScoreBySoftDropTest();

        SwingUtilities.invokeLater(() -> {
            GUIRenderer renderer = new GUIRenderer();
            Score score = new Score();
            Level level = new Level();
            Lines lines = new Lines();

            Shape shape = spawnShape();
            renderer.setCurrentShape(shape);
            renderer.setStats(score.getCurrentScore(), level.getCurrentLevel(), lines.getCurrentLines());
            renderer.open();

            autoDrop = new AutoDrop(
                shape,
                BOARD_HEIGHT,
                () -> {
                    score.increaseScoreBySoftDrop(1); // TEMPORÁRIO, só para ver o painel mudar
                    renderer.setStats(score.getCurrentScore(), level.getCurrentLevel(), lines.getCurrentLines());
                    renderer.render(null);
                 },
                () -> {
                    Shape next = spawnShape();
                    renderer.setCurrentShape(next);
                    autoDrop.setShape(next);
                    renderer.render(null);
                    autoDrop.start();
                }
            );

            autoDrop.start();
        });
    }

  

    private static Shape spawnShape() {
        Shape shape = ShapeFactory.generateShape();
        shape.setBlocksPosition(SPAWN_X, SPAWN_Y);
        return shape;
    }
}

// javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
// java -cp bin MainGame
