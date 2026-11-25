package language;

public class EnglishLanguage implements ILanguage {
    
    @Override public String getTitleText() { return "TETRIS"; }
    @Override public String getPressStartText() { return "PRESS START"; }
    @Override public String[] getControlsTexts() {
        return new String[] {
            "LEFT / RIGHT : MOVE",
            "DOWN : SOFT DROP",
            "Z : ROTATE CLOCKWISE",
            "X : ROTATE COUNTERCLOCKWISE",
            "ENTER : START / PAUSE"
        };
    }

    @Override public String getNextText() { return "NEXT"; }
    @Override public String getScoreText() { return "SCORE"; }
    @Override public String getLevelText() { return "LEVEL"; }
    @Override public String getLinesText() { return "LINES"; }
    @Override public String getPauseText() { return "PAUSE"; }
    @Override public String getGameOverText() { return "GAME OVER"; }
    @Override public String getFinalScoreText() { return "FINAL SCORE"; }
}
