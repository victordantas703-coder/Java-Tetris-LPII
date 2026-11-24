package ui;

import java.awt.Component;
import world.Board;

public interface IRenderer {

    void open();

    void render(Board board);

    void close();

    Component getEventSource();
}

