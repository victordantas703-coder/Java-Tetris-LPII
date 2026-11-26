package gameobject;

import java.awt.Color;

public class Block extends GameObject {
    
    public Color blockColor;

    public Block(Color blockColor){
        this.blockColor = blockColor;
    }

    public Color getBlockColor() {
        return blockColor;
    }
    
}