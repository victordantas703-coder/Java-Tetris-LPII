package ui;

import javax.swing.SwingUtilities;

public class TesteGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIRenderer renderer = new GUIRenderer();
            renderer.open();
        });
    }
}

// javac -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }) compila
// java -cp bin ui.TesteGUI executa

