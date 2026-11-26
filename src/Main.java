import javax.swing.SwingUtilities;

import enums.CommandsEnum;
import enums.GameState;
import enums.InputsEnum;
import enums.LanguageEnum;
import gameobject.Shape;
import inputs.IInputs;
import inputs.InputFactory;
import language.ILanguage;
import language.LanguageFactory;
import tests.*;
import ui.GUIRenderer;
import world.AutoDrop;
import world.Board;
import world.Level;
import world.Lines;
import world.NextShape;
import world.Score;

public class Maingame {

    private static final int SPAWN_X = 120;
    private static final int SPAWN_Y = 0;

    private static GUIRenderer renderer;
    private static AutoDrop autoDrop;
    private static Shape currentShape;
    private static GameState state = GameState.MENU;

    private static LanguageEnum languageMode = LanguageEnum.from();
    private static ILanguage language = new LanguageFactory().create(languageMode);

    private static final Board board = new Board();
    private static final NextShape nextShape = new NextShape();

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
            renderer.open();

            // Ainda não há peça: ela só aparece quando o jogador apertar ENTER
            autoDrop = new AutoDrop(null, board, Maingame::refresh, Maingame::onLanded);

            IInputs input = new InputFactory().create(InputsEnum.from());
            input.attach(renderer.getEventSource(), Maingame::handleCommand);

            refresh(); // mostra o menu
        });
    }

    // ---------- estados ----------

    private static void handleCommand(CommandsEnum command) {
        if (command == CommandsEnum.TOGGLE_LANGUAGE) {   // vale em qualquer estado
            toggleLanguage();
            return;
        }

        switch (state) {
            case MENU, GAME_OVER -> {
                if (command == CommandsEnum.START) startGame();
            }
            case PAUSED -> {
                if (command == CommandsEnum.PAUSE || command == CommandsEnum.START) resume();
            }
            case PLAYING -> play(command);
        }
    }

    private static void startGame() {
        board.reset();
        score.setCurrentScore(Score.START_SCORE);
        level.setCurrentLevel(Level.START_LEVEL);
        lines.setCurrentLines(Lines.START_LINES);

        currentShape = spawnShape();
        renderer.setCurrentShape(currentShape);
        autoDrop.setShape(currentShape);

        state = GameState.PLAYING;
        refresh();
        autoDrop.start();
    }

    private static void pause() {
        autoDrop.stop();
        state = GameState.PAUSED;
        refresh();
    }

    private static void resume() {
        state = GameState.PLAYING;
        refresh();
        autoDrop.start();
    }

    private static void toggleLanguage() {
        LanguageEnum[] all = LanguageEnum.values();
        languageMode = all[(languageMode.ordinal() + 1) % all.length];
        language = new LanguageFactory().create(languageMode);
        refresh();
    }

    // ---------- jogada ----------

    private static void play(CommandsEnum command) {
        switch (command) {
            case LEFT -> {
                if (board.canPlace(currentShape, -1, 0)) {
                    currentShape.updateBlocksPosition(CommandsEnum.LEFT);
                    refresh();
                }
            }
            case RIGHT -> {
                if (board.canPlace(currentShape, 1, 0)) {
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
            case PAUSE -> pause();
            default -> { } // ROTATE_L, ROTATE_R, START e TOGGLE_LANGUAGE não fazem nada aqui
        }
    }

    // ---------- peça pousou ----------

    private static void onLanded() {
        board.lock(currentShape);
        handleLines(board.clearFullLines());
        spawnNext();
    }

    private static void handleLines(int cleared) {
        if (cleared == 0) return;

        score.increaseScoreByLines(level, cleared);
        lines.increaseLines(cleared);
        level.setCurrentLevel(Math.min(lines.getCurrentLines() / 10, Level.MAX_LEVEL));
    }

    private static void spawnNext() {
        currentShape = spawnShape();
        autoDrop.setShape(currentShape);

        if (board.canPlace(currentShape, 0, 0)) {
            renderer.setCurrentShape(currentShape);
            refresh();
            autoDrop.start();
        } else {
            // A peça nova já nasce em cima de outras: acabou
            state = GameState.GAME_OVER;
            renderer.setCurrentShape(null);   // não desenha a peça sobreposta
            refresh();
        }
    }

    private static Shape spawnShape() {
        Shape shape = nextShape.take();
        shape.setBlocksPosition(SPAWN_X, SPAWN_Y);
        return shape;
    }

    // ---------- tela ----------

    // Entrega ao renderizador tudo o que mudou e manda redesenhar
    private static void refresh() {
        renderer.setLanguage(language);
        renderer.setNextShape(nextShape.peek());
        renderer.setStats(score.getCurrentScore(), level.getCurrentLevel(), lines.getCurrentLines());

        switch (state) {
            case MENU      -> renderer.setOverlay(language.gameTitle(), language.pressStartText());
            case PAUSED    -> renderer.setOverlay(language.pausedText(), language.resumeText());
            case GAME_OVER -> renderer.setOverlay(language.gameOverText(), language.restartText());
            case PLAYING   -> renderer.setOverlay(null, null);
        }

        renderer.render(board);
    }
}
