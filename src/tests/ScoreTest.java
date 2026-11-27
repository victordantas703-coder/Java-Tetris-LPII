package tests;

import exception.InvalidScoreException;
import world.Score;
import world.Level;

public class ScoreTest {
    
    public static void increaseScoreByLinesTest() {

        Score score = new Score();
        Level level = new Level();

        assert score.getCurrentScore() == Score.START_SCORE : "O score deveria começar por " + Score.START_SCORE;

        try {
            score.setCurrentScore(-Score.MAX_SCORE); // set de valor inválido
            score.increaseScoreByLines(level, Score.SINGLE); // ao aumentar o score, chama o método de guarda
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        try {
            score.setCurrentScore(Score.MAX_SCORE + 1); // set de valor inválido
            score.increaseScoreByLines(level, Score.SINGLE); // ao aumentar o score, chama o método de guarda
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        try {
            score.setCurrentScore(0);
            score.increaseScoreByLines(level, -1);
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        try {
            score.setCurrentScore(0);
            score.increaseScoreByLines(level, 6);
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        score.setCurrentScore(0);
        int lastScore = score.getCurrentScore();
        score.increaseScoreByLines(level, Score.SINGLE);
        assert score.getCurrentScore() == Score.SIGLE_SCORE : "O score deveria ser " + Score.SIGLE_SCORE;

        lastScore = score.getCurrentScore();
        score.increaseScoreByLines(level, Score.TRIPLE);
        assert score.getCurrentScore() == lastScore + Score.TRIPLE_SCORE : "O score deveria ser " + lastScore + Score.TRIPLE_SCORE;

        level.setCurrentLevel(4);
        score.setCurrentScore(0);
        score.increaseScoreByLines(level, Score.TETRIS);
        assert score.getCurrentScore() == 6000 : "O score deveria ser 6000";

        score.setCurrentScore(Score.MAX_SCORE);
        assert score.hasReachedMax() : "O score deveria ter atingido o máximo";

    }

    public static void increaseScoreBySoftDropTest() {

        Score score = new Score();

        try {
            score.increaseScoreBySoftDrop(30);
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        try {
            score.increaseScoreBySoftDrop(-30);
            assert false : "Deveria lançar InvalidScoreException";
        } catch (InvalidScoreException e) {
            // Lançou InvalidScoreException, como esperado
        }

        score.setCurrentScore(300);
        score.increaseScoreBySoftDrop(4);
        assert score.getCurrentScore() == 304 : "O score deveria ser 304";

    }

}
