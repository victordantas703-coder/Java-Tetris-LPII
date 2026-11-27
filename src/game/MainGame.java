package game;

import java.util.List;

import javax.swing.SwingUtilities;

import enums.CommandsEnum;
import enums.GameStateEnum;
import enums.InputsEnum;
import enums.LanguageEnum;
import gameobject.Shape;
import inputs.IInputs;
import inputs.InputFactory;
import language.ILanguage;
import language.LanguageFactory;
import ui.GUIRenderer;
import ui.IRenderer;
import world.Board;
import world.Level;
import world.Lines;
import world.NextShape;
import world.Score;

public class MainGame {
    
    public static final int LINES_PER_LEVEL = 10;
    public static final long LINE_CLEAR_DURATION_MS = 500;
    public static final long TEST_REPORT_DURATION_MS = 10000;

    private static final int SPAWN_COLUMN = 4;
    private static final int SPAWN_ROW = 1;

    private static final long SOFT_DROP_DELAY_MS = 50;
    private static final long AUTO_SHIFT_DELAY_MS = 267;
    private static final long AUTO_SHIFT_REPEAT_MS = 100;
    private static final long MAX_FRAME_DELTA_MS = 100;

    private final IRenderer renderer;
    private final IInputs inputs;
    private final LanguageFactory languageFactory = new LanguageFactory();

    private ILanguage language;
    private LanguageEnum selectedLanguage = LanguageEnum.from();
    private GameStateEnum state = GameStateEnum.LANGUAGE_SELECT;
    private List<String> testReport = List.of();

    private Board board = new Board();
    private Score score = new Score();
    private Level level = new Level();
    private Lines lines = new Lines();
    private NextShape nextShape = new NextShape();
    private Shape currentShape;

    private long stateTimerMs; // duração atual em um estado do jogo
    private long fallTimerMs; // duração atual da peça em movimento de caida
    private CommandsEnum autoShiftCommand; // comando q irá ficar se repetindo ao segurar o botão de um comando
    private long autoShiftTimerMs; // usado para adotar um deley e repetir os comandos atrelado ao botão segurado
    private boolean downRequiresRelease;
    private long lastFrameNanos;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGame(new GUIRenderer()).run());
    }

    public MainGame(IRenderer renderer) {
        this.renderer = renderer;
        this.inputs = new InputFactory().create(InputsEnum.from());
    }

    // Jogo sem interface e input, usado por MainGameTest
    public MainGame() {
        this.renderer = null;
        this.inputs = null;
    }

    public void run() {
        stateTimerMs = 0;

        renderer.open(this::onFrame);
        inputs.bindInput(renderer.getEventSource());
        lastFrameNanos = System.nanoTime();
    }

    private void onFrame() {
        long now = System.nanoTime();
        long deltaMs = (now - lastFrameNanos) / 1_000_000;
        if (deltaMs > MAX_FRAME_DELTA_MS) {
            deltaMs = MAX_FRAME_DELTA_MS;
            lastFrameNanos = now;
        } else {
            lastFrameNanos += deltaMs * 1_000_000;
        }
        update(deltaMs);
    }

    // Avança o jogo deltaMs milissegundos
    public void update(long deltaMs) {
        if (inputs != null) {
            CommandsEnum command;
            while ((command = inputs.pollCommand()) != null) {
                handleCommand(command);
            }
        }

        switch (state) {
            case UNIT_TESTS -> {
                stateTimerMs += deltaMs;
                if (stateTimerMs >= TEST_REPORT_DURATION_MS)
                    state = GameStateEnum.LANGUAGE_SELECT;
            }
            case GAME_PLAYING -> updatePlaying(deltaMs);
            case LINE_CLEAR -> {
                stateTimerMs += deltaMs;
                if (stateTimerMs >= LINE_CLEAR_DURATION_MS)
                    finishLineClear();
            }
            default -> { }
        }

        render();
    }

    private void render() {
        if (renderer == null)
            return;

        switch (state) {
            case UNIT_TESTS -> renderer.showTestReport(testReport);
            case LANGUAGE_SELECT -> renderer.showLanguageSelect(selectedLanguage);
            case GAME_MENU -> renderer.showMenu(language);
            case GAME_PLAYING, LINE_CLEAR ->
                renderer.showGame(board, currentShape, nextShape, score, level, lines, language);
            case GAME_PAUSED -> renderer.showPause(language, score, level, lines);
            case GAME_OVER -> renderer.showGameOver(language, score);
        }
    }

    public void handleCommand(CommandsEnum command) {
        switch (state) {
            case LANGUAGE_SELECT -> handleLanguageSelectCommand(command);
            case GAME_MENU -> {
                if (command == CommandsEnum.START)
                    startNewGame();
            }
            case GAME_PLAYING -> handlePlayingCommand(command);
            case GAME_PAUSED -> {
                if (command == CommandsEnum.START)
                    state = GameStateEnum.GAME_PLAYING;
            }
            case GAME_OVER -> {
                if (command == CommandsEnum.START)
                    state = GameStateEnum.GAME_MENU;
            }
            default -> { } // TESTS e LINE_CLEAR n usam comandos
        }
    }

    private void handleLanguageSelectCommand(CommandsEnum command) {
        LanguageEnum[] languages = LanguageEnum.values();
        int index = selectedLanguage.ordinal();

        switch (command) {
            case LEFT -> selectedLanguage = languages[Math.floorMod(index - 1, languages.length)];
            case RIGHT -> selectedLanguage = languages[Math.floorMod(index + 1, languages.length)];
            case START -> {
                language = languageFactory.create(selectedLanguage);
                state = GameStateEnum.GAME_MENU;
            }
            default -> { }
        }
    }

    private void handlePlayingCommand(CommandsEnum command) {
        switch (command) {
            case LEFT, RIGHT -> {
                tryShift(command);
                autoShiftCommand = command;
                autoShiftTimerMs = AUTO_SHIFT_DELAY_MS;
            }
            case ROTATE_CLOCKWISE, ROTATE_COUNTERCLOCKWISE -> tryRotate(command);
            case DOWN -> {
                downRequiresRelease = false;
                fallTimerMs = 0;
                moveDown(true);
            }
            case START -> {
                autoShiftCommand = null;
                state = GameStateEnum.GAME_PAUSED;
            }
        }
    }

    public void startNewGame() {
        board = new Board();
        score = new Score();
        level = new Level();
        lines = new Lines();
        nextShape = new NextShape();
        autoShiftCommand = null;
        state = GameStateEnum.GAME_PLAYING;
        spawnShape();
    }

    public void spawnShape() {
        currentShape = nextShape.take();
        currentShape.setPosition(SPAWN_COLUMN, SPAWN_ROW);
        fallTimerMs = 0;

        downRequiresRelease = inputs != null && inputs.isHeld(CommandsEnum.DOWN); // evita o soft drop usado na peça anterior da nova peça adquirida 

        if (!board.canPlace(currentShape))
            gameOver();
    }

    private void updatePlaying(long deltaMs) {
        boolean softDrop = isSoftDropHeld();
        applyAutoShift(deltaMs);

        long delay = level.getFallDelayMs();
        if (softDrop)
            delay = Math.min(delay, SOFT_DROP_DELAY_MS);

        fallTimerMs += deltaMs;
        while (state == GameStateEnum.GAME_PLAYING && fallTimerMs >= delay) {
            fallTimerMs -= delay;
            if (!moveDown(softDrop))
                break;
        }
    }

    private boolean isSoftDropHeld() {
        if (inputs == null)
            return false;
        boolean held = inputs.isHeld(CommandsEnum.DOWN);
        if (!held)
            downRequiresRelease = false;
        return held && !downRequiresRelease;
    }

    private void applyAutoShift(long deltaMs) {
        if (autoShiftCommand == null || inputs == null)
            return;
        if (!inputs.isHeld(autoShiftCommand)) {
            autoShiftCommand = null;
            return;
        }

        autoShiftTimerMs -= deltaMs; // igual a AUTO_SHIFT_DELAY_MS inicialmente
        while (autoShiftTimerMs <= 0) {
            tryShift(autoShiftCommand);
            autoShiftTimerMs += AUTO_SHIFT_REPEAT_MS;
        }
    }

    private void tryShift(CommandsEnum command) {
        currentShape.updateBlocksPosition(command);
        if (!board.canPlace(currentShape))
            currentShape.updateBlocksPosition(opposite(command)); // evita da peça sair da área do tabuleiro
    }

    private void tryRotate(CommandsEnum command) {
        currentShape.updateBlocksPosition(command);
        if (!board.canPlace(currentShape))
            currentShape.updateBlocksPosition(opposite(command)); // evita da peça sair da área do tabuleiro
    }

    private static CommandsEnum opposite(CommandsEnum command) { 
        return switch (command) {
            case LEFT -> CommandsEnum.RIGHT;
            case RIGHT -> CommandsEnum.LEFT;
            case ROTATE_CLOCKWISE -> CommandsEnum.ROTATE_COUNTERCLOCKWISE;
            case ROTATE_COUNTERCLOCKWISE -> CommandsEnum.ROTATE_CLOCKWISE;
            default -> throw new IllegalArgumentException("Command has no opposite: " + command);
        };
    }

    private boolean moveDown(boolean softDrop) {
        currentShape.updateFall();
        if (board.canPlace(currentShape)) {
            if (softDrop) {
                score.increaseScoreBySoftDrop(1);
                if (score.hasReachedMax())
                    gameOver();
            }
            return true;
        }

        currentShape.move(0, -1);
        lockCurrentShape();
        return false;
    }

    private void lockCurrentShape() {
        board.lock(currentShape);
        currentShape = null;

        if (board.markCompletedRows() > 0) {
            state = GameStateEnum.LINE_CLEAR; // completar linhas é um estado diferente, pois interrompe o fluxo normal do jogo
            stateTimerMs = 0;
        } else {
            spawnShape();
        }
    }

    private void finishLineClear() {
        int completedLines = board.removeMarkedRows();

        score.increaseScoreByLines(level, completedLines);
        lines.increaseLines(completedLines);

        int levelByLines = Math.min(Level.MAX_LEVEL, lines.getCurrentLines() / LINES_PER_LEVEL);
        while (level.getCurrentLevel() < levelByLines) {
            level.increaseLevel();
        }

        state = GameStateEnum.GAME_PLAYING;
        if (score.hasReachedMax())
            gameOver();
        else
            spawnShape();
    }

    private void gameOver() {
        currentShape = null;
        autoShiftCommand = null;
        state = GameStateEnum.GAME_OVER;
    }

}
