package world;

import exception.InvalidLinesException;

public class Lines {

    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int TETRIS = 4;
    
    public static final int START_LINES = 0;
    public static final int MAX_LINES = 999999;

    private int currentLines = START_LINES;

    public int getCurrentLines(){
        return this.currentLines;
    }

    public void setCurrentLines(int value){
        this.currentLines = value;
    }

    private void increaseLinesGuard(int completedLines){
        if (this.currentLines < START_LINES)
            throw new InvalidLinesException("Lines below " + START_LINES);
        if (this.currentLines > MAX_LINES)
            throw new InvalidLinesException("Lines above " + MAX_LINES);
        if (completedLines < SINGLE || completedLines > TETRIS)
            throw new InvalidLinesException("Invalid completed lines quantity: " + completedLines);
    }

    public void increaseLines(int completedLines){
        increaseLinesGuard(completedLines);

        if(this.currentLines + completedLines > MAX_LINES)
            this.currentLines = MAX_LINES; // ignora as linhas adicionais
        else
            this.currentLines = this.currentLines + completedLines;
    }

}
