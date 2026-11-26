package world;

import exception.InvalidScoreException;

public class Score {

    public static final int START_SCORE = 0; 
    public static final int MAX_SCORE = 999999; 

    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int TETRIS = 4;

    public static final int SIGLE_SCORE = 40;
    public static final int DOUBLE_SCORE = 100;
    public static final int TRIPLE_SCORE = 300;
    public static final int TETRIS_SCORE = 1200;

    private int currentScore = START_SCORE;

    public int getCurrentScore(){
        return this.currentScore;
    }

    public boolean hasReachedMax(){
        return this.currentScore >= MAX_SCORE;
    }

    public void setCurrentScore(int value){
        this.currentScore = value;
    }

    private void increaseScoreGuard() {
        if (this.currentScore < START_SCORE)
            throw new InvalidScoreException("Score below " + START_SCORE);
        if (this.currentScore > MAX_SCORE)
            throw new InvalidScoreException("Score above " + MAX_SCORE);
    }

    public void increaseScoreByLines(Level level, int completedLines){
        increaseScoreGuard();
        if (completedLines < Score.SINGLE || completedLines > Score.TETRIS)
            throw new InvalidScoreException("Invalid completed lines quantity: " + completedLines);

        int baseScoreValue = 0;
        switch (completedLines) {
            case SINGLE:
                baseScoreValue = SIGLE_SCORE;
                break;
            case DOUBLE:
                baseScoreValue = DOUBLE_SCORE;
                break;
            case TRIPLE:
                baseScoreValue = TRIPLE_SCORE;
                break;
            case TETRIS:
                baseScoreValue = TETRIS_SCORE;
                break;
            default:
                break;
        }

        if ((this.currentScore + (baseScoreValue * (level.getCurrentLevel() + 1))) > MAX_SCORE)
            this.currentScore = MAX_SCORE;
        else
            this.currentScore = this.currentScore + (baseScoreValue * (level.getCurrentLevel() + 1));
    }

    public void increaseScoreBySoftDrop(int skippedLinesQuantity) {
        increaseScoreGuard();
        if (skippedLinesQuantity < 0 || skippedLinesQuantity > Board.BOARD_HEIGHT)
            throw new InvalidScoreException("Invalid skipped lines quantity: " + skippedLinesQuantity);

        if (this.currentScore + skippedLinesQuantity > MAX_SCORE)
            this.currentScore = MAX_SCORE; // ignora a pontuação extra
        else
            this.currentScore = this.currentScore + skippedLinesQuantity;
    }
    
}
