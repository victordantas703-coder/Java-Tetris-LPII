package ui;

import java.awt.Component;
import java.util.List;

import enums.LanguageEnum;
import gameobject.Shape;
import language.ILanguage;
import world.Board;
import world.Level;
import world.Lines;
import world.NextShape;
import world.Score;

public interface IRenderer {

    void open(Runnable frameCallback);
    void close();

    Component getEventSource(); // usado para registrar o ouvinte das entradas vindas do componente de exibição

    void showTestReport(List<String> messages);

    void showLanguageSelect(LanguageEnum selectedLanguage);

    void showMenu(ILanguage language);

    void showGame(Board board, Shape currentShape, NextShape nextShape,
                  Score score, Level level, Lines lines, ILanguage language);

    void showPause(ILanguage language, Score score, Level level, Lines lines);

    void showGameOver(ILanguage language, Score score);
}

