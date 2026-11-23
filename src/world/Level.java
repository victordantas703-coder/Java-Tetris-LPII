package world;

import exception.InvalidLevelException;

public class Level {
    
    public static final int START_LEVEL = 0; 
    public static final int MAX_LEVEL = 9; 

    private int currentLevel = START_LEVEL;

    public int getCurrentLevel(){
        return this.currentLevel;
    }

    public Boolean increaseLevel(){
        if (this.currentLevel < START_LEVEL)
            throw new InvalidLevelException("Level abaixo de " + START_LEVEL);
        if (this.currentLevel > MAX_LEVEL)
            throw new InvalidLevelException("Level acima de " + MAX_LEVEL);
        if (this.currentLevel == MAX_LEVEL)
            return false;

        this.currentLevel++;
        return true;
    }

}
