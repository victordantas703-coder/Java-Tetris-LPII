package tests;

import exception.InvalidLinesException;
import world.Lines;

public class LinesTest {
    
    public static void increaseLinesTest() {

        Lines lines = new Lines();

        assert lines.getCurrentLines() == Lines.START_LINES : "O contador de linhas deveria começar por " + Lines.START_LINES;

        try {
            lines.setCurrentLines(-Lines.MAX_LINES); // set de valor inválido
            lines.increaseLines(Lines.SINGLE); // ao aumentar o level, chama o método de guarda
            assert false : "Deveria lançar InvalidLinesException";
        } catch (InvalidLinesException e) {
            // Lançou InvalidLinesException, como esperado
        }

        try {
            lines.setCurrentLines(Lines.MAX_LINES + 1); // set de valor inválido
            lines.increaseLines(Lines.SINGLE); // ao aumentar o level, chama o método de guarda
            assert false : "Deveria lançar InvalidLinesException";
        } catch (InvalidLinesException e) {
            // Lançou InvalidLinesException, como esperado
        }

        try {
            lines.setCurrentLines(0);
            lines.increaseLines(-1);
            assert false : "Deveria lançar InvalidLinesException";
        } catch (InvalidLinesException e) {
            // Lançou InvalidLinesException, como esperado
        }

        try {
            lines.setCurrentLines(0);
            lines.increaseLines(6);
            assert false : "Deveria lançar InvalidLinesException";
        } catch (InvalidLinesException e) {
            // Lançou InvalidLinesException, como esperado
        }

        lines.setCurrentLines(0);
        lines.increaseLines(Lines.SINGLE);
        assert lines.getCurrentLines() == 1 : "A quantidade de linhas deveria ser 1";

        lines.setCurrentLines(0);
        lines.increaseLines(Lines.DOUBLE);
        assert lines.getCurrentLines() == 2 : "A quantidade de linhas deveria ser 2";

        lines.setCurrentLines(0);
        lines.increaseLines(Lines.TRIPLE);
        assert lines.getCurrentLines() == 3 : "A quantidade de linhas deveria ser 3";

        lines.setCurrentLines(0);
        lines.increaseLines(Lines.TETRIS);
        assert lines.getCurrentLines() == 4 : "A quantidade de linhas deveria ser 4";

        lines.setCurrentLines(Lines.MAX_LINES);
        lines.increaseLines(Lines.SINGLE);
        assert lines.getCurrentLines() == Lines.MAX_LINES : "A quantidade de linhas deveria ser " + Lines.MAX_LINES;


    }

}
