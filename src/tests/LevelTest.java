package tests;

import exception.InvalidLevelException;
import world.Level;

public class LevelTest {
    
    public static void increseLevelTest() {

        Level level = new Level();

        assert level.getCurrentLevel() == Level.START_LEVEL : "Level deveria começar por " + Level.START_LEVEL;

        try {
            level.setCurrentLevel(-Level.MAX_LEVEL); // set de valor inválido
            level.increaseLevel(); // ao aumentar o level, chama o método de guarda
            assert false : "Deveria lançar InvalidLevelException";
        } catch (InvalidLevelException e) {
            // Lançou InvalidLevelException, como esperado
        }

        try {
            level.setCurrentLevel(Level.MAX_LEVEL + 1); // set de valor inválido
            level.increaseLevel(); // ao aumentar o level, chama o método de guarda
            assert false : "Deveria lançar InvalidLevelException";
        } catch (InvalidLevelException e) {
            // Lançou InvalidLevelException, como esperado
        }

        level.setCurrentLevel(0);
        for (int i = 1; i <= Level.MAX_LEVEL; i++) {
            assert level.increaseLevel() : "Deveria retonar true ao aumentar o level";
            assert level.getCurrentLevel() == i : "O level deveria ser " + i + " depois que aumentou";
        }

        assert level.getCurrentLevel() == Level.MAX_LEVEL : "O level deveria ser " + Level.MAX_LEVEL;
        assert !(level.increaseLevel()) : "Deveria retonar false ao tentar aumentar o level quando level é " + Level.MAX_LEVEL;

    }

}
