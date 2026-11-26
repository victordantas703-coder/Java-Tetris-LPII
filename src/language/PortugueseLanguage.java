package language;

public class PortugueseLanguage implements ILanguage {
    
    @Override public String getTitleText() { return "TETRIS"; }
    @Override public String getPressStartText() { return "APERTE START"; }
    @Override public String[] getControlsTexts() {
        return new String[] {
            "ESQUERDA / DIREITA : MOVER",
            "BAIXO : QUEDA RÁPIDA",
            "Z : GIRAR NO SENTIDO HORÁRIO",
            "X : GIRAR NO SENTIDO ANTI-HORÁRIO",
            "ENTER : START / PAUSA"
        };
    }

    @Override public String getNextText() { return "PRÓXIMA"; }
    @Override public String getScoreText() { return "PONTOS"; }
    @Override public String getLevelText() { return "NÍVEL"; }
    @Override public String getLinesText() { return "LINHAS"; }
    @Override public String getPauseText() { return "PAUSA"; }
    @Override public String getGameOverText() { return "FIM DE JOGO"; }
    @Override public String getFinalScoreText() { return "PONTUAÇÃO FINAL"; }
}
