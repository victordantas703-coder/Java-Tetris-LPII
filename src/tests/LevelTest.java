package tests;

import world.Level;

public class LevelTest {
    
    public static void increseLevelTest() {

        Level level = new Level();

        assert level.getCurrentLevel() == Level.START_LEVEL : "Level deveria começar por " + Level.START_LEVEL;

        for (int i = 1; i <= Level.MAX_LEVEL; i++) {
            assert level.increaseLevel() : "Deveria retonar true ao aumentar o level";
            assert level.getCurrentLevel() == i : "O level deveria ser " + i + " depois que aumentou";
        }

        assert level.getCurrentLevel() == Level.MAX_LEVEL : "O level deveria ser " + Level.MAX_LEVEL;
        assert !(level.increaseLevel()) : "Deveria retonar false ao tentar aumentar o level quando level é " + Level.MAX_LEVEL;

    }

}
