package world;

import exception.InvalidLevelException;

public class Level {
    
    public static final int START_LEVEL = 0; 
    public static final int MAX_LEVEL = 9; 

    // define o tempo de descida da peça de acordo com currentLevel
    private static final int[] FALL_DELAY_MS = { 887, 820, 753, 686, 619, 552, 469, 368, 285, 184 };

    private int currentLevel = START_LEVEL;

    public int getCurrentLevel(){
        return this.currentLevel;
    }

    public void setCurrentLevel(int value){
        this.currentLevel = value;
    }

    public int getFallDelayMs(){
        return FALL_DELAY_MS[Math.max(START_LEVEL, Math.min(MAX_LEVEL, this.currentLevel))];
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
