package tests;

import javax.swing.SwingUtilities;

import gameobject.Shape;
import gameobject.Shape_Bar;
import gameobject.Shape_LD;
import gameobject.Shape_LE;
import gameobject.Shape_Square;
import gameobject.Shape_T;
import gameobject.Shape_ZD;
import gameobject.Shape_ZE;
import ui.GUIRenderer;

public class GUITest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIRenderer renderer = new GUIRenderer();

            Shape shape = new Shape_ZE();
            shape.setBlocksPosition(120, 0);   // x e y relativos ao canto da área do jogo
            renderer.setCurrentShape(shape);

            renderer.open();
        });
    }
}

/* javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp bin tests.TesteGUI */

