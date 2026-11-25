package language;

/**
 * Interface que define os métodos que uma classe de idioma deve implementar.
 * Cada idioma deve fornecer traduções para os textos exibidos no jogo.
 */
public interface ILanguage {

    String getTitleText();
    String getPressStartText();

    /** Linhas com a explicação das teclas, exibidas no menu. */
    String[] getControlsTexts();

    String getNextText();
    String getScoreText();
    String getLevelText();
    String getLinesText();
    String getPauseText();
    String getGameOverText();
    String getFinalScoreText();
}
