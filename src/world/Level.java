package world;

import exception.InvalidLevelException;

public class Level {
    
    public static final int START_LEVEL = 0; 
    public static final int MAX_LEVEL = 9; 

    private int currentLevel = START_LEVEL;

    public int getCurrentLevel(){
        return this.currentLevel;
    }

    private void increaseLevelGuard(){
        if (this.currentLevel < START_LEVEL)
            throw new InvalidLevelException("Level below " + START_LEVEL);
        if (this.currentLevel > MAX_LEVEL)
            throw new InvalidLevelException("Level above " + MAX_LEVEL);
    }

    public Boolean increaseLevel(){
        increaseLevelGuard();

        if (this.currentLevel < MAX_LEVEL){
            this.currentLevel++;
            return true;
        }

        return false;
    }

}
