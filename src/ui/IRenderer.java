package ui;

import java.awt.Component;
import world.Grid;

public interface IRenderer {

    void open();

    void render(Grid grid);

    void close();

    Component getEventSource();
}

