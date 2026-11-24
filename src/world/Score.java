package world;

import exception.InvalidScoreException;

public class Score {
    
    public static final int START_SCORE = 0; 
    public static final int MAX_SCORE = 999999; 

    public static final int SIGLE_SCORE = 40;
    public static final int DOUBLE_SCORE = 100;
    public static final int TRIPLE_SCORE = 300;
    public static final int TETRIS_SCORE = 1200;

    private int currentScore = START_SCORE;

    public int getCurrentScore(){
        return this.currentScore;
    }

    private void increaseScoreGuard() {
        if (this.currentScore < START_SCORE)
            throw new InvalidScoreException("Score below " + START_SCORE);
        if (this.currentScore > MAX_SCORE)
            throw new InvalidScoreException("Score above " + MAX_SCORE);
    }

    public void increaseScoreByLines(Level level, int completedLines){
        increaseScoreGuard();

        int baseScoreValue = 0;
        switch (completedLines) {
            case 1:
                baseScoreValue = SIGLE_SCORE;
                break;
            case 2:
                baseScoreValue = DOUBLE_SCORE;
                break;
            case 3:
                baseScoreValue = TRIPLE_SCORE;
                break;
            case 4:
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

        if (this.currentScore + skippedLinesQuantity > MAX_SCORE)
            this.currentScore = MAX_SCORE;
        else
            this.currentScore = this.currentScore + skippedLinesQuantity;
    }
    
}
