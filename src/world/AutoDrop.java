package world;

import javax.swing.Timer;

import gameobject.Block;
import gameobject.Shape;

// Faz a peça atual cair uma célula a cada intervalo de tempo.
// Usa javax.swing.Timer, que dispara na thread da interface (EDT),
public class AutoDrop {

    // Intervalo padrao entre quedas
    public static final int DROP_INTERVAL_MS = 800;

    private final int floorY;
    private final Runnable onFall;
    private final Runnable onLanded;
    private final Timer timer;
    private Shape shape;

    // floorY: y do chão do tabuleiro (altura em pixels)
    // onFall: chamado depois de cada queda
    // onLanded: chamado quando a peça não consegue mais descer
    public AutoDrop(Shape shape, int floorY, Runnable onFall, Runnable onLanded) {
        this(shape, floorY, DROP_INTERVAL_MS, onFall, onLanded);
    }

    public AutoDrop(Shape shape, int floorY, int intervalMs, Runnable onFall, Runnable onLanded) {
        this.shape = shape;
        this.floorY = floorY;
        this.onFall = onFall;
        this.onLanded = onLanded;
        this.timer = new Timer(intervalMs, event -> step());
    }

    // Troca a peça que atingiu o chao, fazendo com que a nova peça comece a cair
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    // Um passo da queda
    // Retorna true se a peca desceu e false se ela pousou
    public boolean step() {
        if (!canFall()) {
            stop();
            onLanded.run();
            return false;
        }

        shape.updateBlockFall();
        onFall.run();
        return true;
    }

    private boolean canFall() {
        return shape.getBottomY() + Block.BLOCK_SIZE <= floorY;
    }

}
