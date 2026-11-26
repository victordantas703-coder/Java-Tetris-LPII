package language;

public interface ILanguage {

    String getTitleText();
    String getPressStartText();
    
    String[] getControlsTexts();

    String getNextText();
    String getScoreText();
    String getLevelText();
    String getLinesText();
    String getPauseText();
    String getGameOverText();
    String getFinalScoreText();
}
