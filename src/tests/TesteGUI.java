package tests;

import javax.swing.SwingUtilities;

import ui.GUIRenderer;

public class TesteGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIRenderer renderer = new GUIRenderer();
            renderer.open();
        });
    }
}

/* javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp bin tests.TesteGUI */

