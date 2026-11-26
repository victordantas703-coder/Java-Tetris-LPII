import javax.swing.SwingUtilities;

import enums.CommandsEnum;
import enums.InputsEnum;
import gameobject.Block;
import gameobject.Shape;
import gameobject.ShapeFactory;
import inputs.IInputs;
import inputs.InputFactory;
import tests.*;
import ui.GUIRenderer;
import world.AutoDrop;
import world.Level;
import world.Lines;
import world.Score;

public class Maingame {

    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int TETRIS = 4;

    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 600;
    private static final int SPAWN_X = 120;
    private static final int SPAWN_Y = 0;

    private static GUIRenderer renderer;
    private static AutoDrop autoDrop;
    private static Shape currentShape;

    private static final Score score = new Score();
    private static final Level level = new Level();
    private static final Lines lines = new Lines();

    public static void main(String[] args) {

        LevelTest.increseLevelTest();
        LinesTest.increaseLinesTest();
        ScoreTest.increaseScoreByLinesTest();
        ScoreTest.increaseScoreBySoftDropTest();

        SwingUtilities.invokeLater(() -> {
            renderer = new GUIRenderer();

            currentShape = spawnShape();
            renderer.setCurrentShape(currentShape);
            renderer.open();

            autoDrop = new AutoDrop(
                currentShape,
                BOARD_HEIGHT,
                Maingame::refresh,
                () -> {
                    currentShape = spawnShape();
                    renderer.setCurrentShape(currentShape);
                    autoDrop.setShape(currentShape);
                    refresh();
                    autoDrop.start();
                }
            );

            IInputs input = new InputFactory().create(InputsEnum.from());
            input.attach(renderer.getEventSource(), Maingame::handleCommand);

            refresh();
            autoDrop.start();
        });
    }

    private static void handleCommand(CommandsEnum command) {
        switch (command) {
            case LEFT -> {
                if (currentShape.getLeftX() - Block.BLOCK_SIZE >= 0) {
                    currentShape.updateBlocksPosition(CommandsEnum.LEFT);
                    refresh();
                }
            }
            case RIGHT -> {
                if (currentShape.getRightX() + Block.BLOCK_SIZE <= BOARD_WIDTH) {
                    currentShape.updateBlocksPosition(CommandsEnum.RIGHT);
                    refresh();
                }
            }
            case DOWN -> {
                if (autoDrop.step()) {            // true = a peça desceu
                    score.increaseScoreBySoftDrop(1);
                    refresh();
                }
            }
            default -> { } // ROTATE_L, ROTATE_R e START ainda não existem
        }
    }

    private static void refresh() {
        renderer.setStats(score.getCurrentScore(), level.getCurrentLevel(), lines.getCurrentLines());
        renderer.render(null);
    }

    private static Shape spawnShape() {
        Shape shape = ShapeFactory.generateShape();
        shape.setBlocksPosition(SPAWN_X, SPAWN_Y);
        return shape;
    }
}
