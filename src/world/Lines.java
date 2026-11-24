package world;

import exception.InvalidLinesException;

public class Lines {
    
    public static final int START_LINES = 0;
    public static final int MAX_LINES = 999999;

    private int currentLines = START_LINES;

    public int getCurrentLines(){
        return this.currentLines;
    }

    private void increaseLinesGuard(){
        if (this.currentLines < START_LINES)
            throw new InvalidLinesException("Lines below " + START_LINES);
        if (this.currentLines > MAX_LINES)
            throw new InvalidLinesException("Lines above " + MAX_LINES);
    }

    public void increaseLines(int completedLines){
        increaseLinesGuard();

        if(this.currentLines + completedLines > MAX_LINES)
            this.currentLines = MAX_LINES;
        else
            this.currentLines = this.currentLines + completedLines;
    }

}
