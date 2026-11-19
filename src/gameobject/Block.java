package gameobject;

import java.awt.Color;

public class Block extends GameObject {
    
    public Color blockColor;
    public static final int BLOCK_SIZE = 30;

    public Block(Color blockColor){
        this.blockColor = blockColor;
    }
    
}