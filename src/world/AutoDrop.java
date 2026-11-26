package world;

import javax.swing.Timer;

import gameobject.Block;
import gameobject.Shape;

public class AutoDrop {

    public static final int DROP_INTERVAL_MS = 800;

    private final int floorY;
    private final Runnable onFall;
    private final Runnable onLanded;
    private final Timer timer;
    private Shape shape;

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
