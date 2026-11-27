package tests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import enums.CommandsEnum;
import enums.GameStateEnum;
import game.MainGame;
import gameobject.Block;
import gameobject.Shape;
import gameobject.Shape_Bar;
import gameobject.Shape_Square;
import gameobject.Shape_T;
import world.Board;
import world.Score;

public class MainGameTest {

    private static final String OK = "[OK] ";
    private static final String FAILED = "[FALHOU] ";

    private static final int LAST_ROW = Board.BOARD_HEIGHT - 1;

    public static boolean assertsEnabled() {
        boolean enabled = false;
        assert enabled = true; 
        return enabled;
    }

    public static List<String> runAll() {
        List<String> report = new ArrayList<>();
        if (!assertsEnabled())
            return report;

        run(report, "LevelTest.increseLevelTest", LevelTest::increseLevelTest);
        run(report, "LinesTest.increaseLinesTest", LinesTest::increaseLinesTest);
        run(report, "ScoreTest.increaseScoreByLinesTest", ScoreTest::increaseScoreByLinesTest);
        run(report, "ScoreTest.increaseScoreBySoftDropTest", ScoreTest::increaseScoreBySoftDropTest);
        run(report, "MainGameTest.lineDisappearsWhenCompletedTest", MainGameTest::lineDisappearsWhenCompletedTest);
        run(report, "MainGameTest.levelIncreasesAfterTenLinesTest", MainGameTest::levelIncreasesAfterTenLinesTest);
        run(report, "MainGameTest.softDropGivesOnePointTest", MainGameTest::softDropGivesOnePointTest);
        run(report, "MainGameTest.shapeStopsAtWallTest", MainGameTest::shapeStopsAtWallTest);
        run(report, "MainGameTest.rotationBothDirectionsTest", MainGameTest::rotationBothDirectionsTest);
        run(report, "MainGameTest.rotationBlockedByWallTest", MainGameTest::rotationBlockedByWallTest);
        run(report, "MainGameTest.gameOverAtMaxScoreTest", MainGameTest::gameOverAtMaxScoreTest);

        return report;
    }

    private static void run(List<String> report, String name, Runnable test) {
        try {
            test.run();
            report.add(OK + name);
        } catch (AssertionError error) {
            report.add(FAILED + name + ": " + error.getMessage());
        } catch (RuntimeException exception) {
            report.add(FAILED + name + ": " + exception);
        }
    }

    public static void lineDisappearsWhenCompletedTest() {
        MainGame game = newGame();
        prepareSingleLineClear(game);

        dropCurrentShape(game);
        assert game.getState() == GameStateEnum.LINE_CLEAR : "Ao completar a linha, o jogo deveria ir para LINE_CLEAR";
        assert game.getBoard().isRowMarked(LAST_ROW) : "A linha completa deveria estar marcada para piscar";

        finishLineClear(game);
        assert game.getState() == GameStateEnum.GAME_PLAYING : "Depois do efeito, o jogo deveria voltar para GAME_PLAYING";
        assert game.getCurrentShape() != null : "Uma nova peça deveria ser adquirida depois que a linha some";
        for (int column = 0; column < Board.BOARD_WIDTH; column++) {
            assert game.getBoard().getBlock(column, LAST_ROW) == null : "A linha completa deveria ter desaparecido";
        }
        assert game.getLines().getCurrentLines() == 1 : "Deveria ter 1 linha completada";
    }

    public static void levelIncreasesAfterTenLinesTest() {
        MainGame game = newGame();
        game.getLines().setCurrentLines(9);
        prepareSingleLineClear(game);

        dropCurrentShape(game);
        finishLineClear(game);

        assert game.getLines().getCurrentLines() == 10 : "Deveria ter 10 linhas completadas";
        assert game.getLevel().getCurrentLevel() == 1 : "O level deveria ser 1 ao completar 10 linhas";
    }

    public static void softDropGivesOnePointTest() {
        MainGame game = newGame();
        Shape shape = new Shape_T();
        shape.setPosition(4, 5);
        game.setCurrentShape(shape);

        game.handleCommand(CommandsEnum.DOWN);

        assert shape.getY() == 6 : "A peça deveria descer uma linha";
        assert game.getScore().getCurrentScore() == 1 : "Descer segurando baixo deveria valer 1 ponto";
    }

    public static void shapeStopsAtWallTest() {
        MainGame game = newGame();
        Shape shape = new Shape_Square();
        shape.setPosition(0, 5);
        game.setCurrentShape(shape);

        game.handleCommand(CommandsEnum.LEFT);
        assert shape.getX() == 0 : "A peça não deveria atravessar a parede esquerda";

        shape.setPosition(Board.BOARD_WIDTH - 2, 5); // ocupa as duas últimas colunas
        game.handleCommand(CommandsEnum.RIGHT);
        assert shape.getX() == Board.BOARD_WIDTH - 2 : "A peça não deveria atravessar a parede direita";
    }

    public static void rotationBothDirectionsTest() {
        MainGame game = newGame();
        Shape shape = new Shape_T();
        shape.setPosition(4, 5);
        game.setCurrentShape(shape);
        int[][] original = positionsOf(shape);

        game.handleCommand(CommandsEnum.ROTATE_CLOCKWISE);
        assert !java.util.Arrays.deepEquals(original, positionsOf(shape)) : "Girar deveria mudar a posição dos blocos";
        game.handleCommand(CommandsEnum.ROTATE_COUNTERCLOCKWISE);
        assert java.util.Arrays.deepEquals(original, positionsOf(shape)) : "Girar nos dois sentidos deveria voltar à posição original";

        for (int i = 0; i < 4; i++) {
            game.handleCommand(CommandsEnum.ROTATE_CLOCKWISE);
        }
        assert java.util.Arrays.deepEquals(original, positionsOf(shape)) : "4 giros horários deveriam voltar à posição original";
    }

    public static void rotationBlockedByWallTest() {
        MainGame game = newGame();
        Shape bar = new Shape_Bar();
        bar.updateBlocksPosition(CommandsEnum.ROTATE_CLOCKWISE); // vertical
        bar.setPosition(0, 5); // encostada na parede esquerda
        game.setCurrentShape(bar);
        int[][] before = positionsOf(bar);

        game.handleCommand(CommandsEnum.ROTATE_CLOCKWISE); // na horizontal precisaria da coluna -1
        assert java.util.Arrays.deepEquals(before, positionsOf(bar)) : "A rotação deveria ser bloqueada pela parede";
    }

    public static void gameOverAtMaxScoreTest() {
        MainGame game = newGame();
        game.getScore().setCurrentScore(Score.MAX_SCORE - 10);
        prepareSingleLineClear(game);

        dropCurrentShape(game);
        finishLineClear(game);

        assert game.getScore().getCurrentScore() == Score.MAX_SCORE : "O score deveria parar em " + Score.MAX_SCORE;
        assert game.getState() == GameStateEnum.GAME_OVER : "Deveria ser Game Over ao atingir " + Score.MAX_SCORE;
    }

    private static MainGame newGame() {
        MainGame game = new MainGame();
        game.startNewGame();
        return game;
    }

    private static void prepareSingleLineClear(MainGame game) {
        fillRowExcept(game.getBoard(), LAST_ROW, 3, 6);

        Shape bar = new Shape_Bar();
        bar.setPosition(4, 1);
        game.setCurrentShape(bar);
    }

    private static void fillRowExcept(Board board, int row, int gapFirstColumn, int gapLastColumn) {
        for (int column = 0; column < Board.BOARD_WIDTH; column++) {
            if (column < gapFirstColumn || column > gapLastColumn)
                board.setBlock(column, row, new Block(Color.GRAY));
        }
    }

    private static void dropCurrentShape(MainGame game) {
        Shape shape = game.getCurrentShape();
        for (int i = 0; i < 100 && game.getCurrentShape() == shape; i++) {
            game.update(game.getLevel().getFallDelayMs());
        }
        assert game.getCurrentShape() != shape : "A peça deveria ter sido fixada";
    }

    private static void finishLineClear(MainGame game) {
        game.update(MainGame.LINE_CLEAR_DURATION_MS);
    }

    private static int[][] positionsOf(Shape shape) {
        Block[] blocks = shape.getBlocks();
        int[][] positions = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++) {
            positions[i] = new int[] { blocks[i].getX(), blocks[i].getY() };
        }
        return positions;
    }

}
