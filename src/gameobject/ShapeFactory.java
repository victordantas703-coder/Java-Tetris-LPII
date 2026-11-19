package gameobject;

import java.util.Random;

public class ShapeFactory {
    
    private static final int SHAPES_QUANTITY = 7;

    public static Shape generateShape() {
        
        Shape shape = null;
        
        int randomNumber = new Random().nextInt(SHAPES_QUANTITY);
        switch (randomNumber) {
            case 0:
                shape = new Shape_LD();
                break;
            case 1:
                shape = new Shape_LE();
                break;
            case 2:
                shape = new Shape_Square();
                break;
            case 3:
                shape = new Shape_T();
                break;
            case 4:
                shape = new Shape_ZD();
                break;
            case 5:
                shape = new Shape_ZE();
                break;
            case 6:
                shape = new Shape_Bar();
                break;
            default:
                break;
        }

        return shape;

    }

}
