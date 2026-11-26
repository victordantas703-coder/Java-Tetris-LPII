package world;

import gameobject.Shape;
import gameobject.ShapeFactory;

public class NextShape {
    
    private Shape shape = ShapeFactory.generateShape();

    public Shape getShape() {
        return shape;
    }

    public Shape take() {
        Shape taken = shape;
        shape = ShapeFactory.generateShape();
        // Se o sorteio gerar a mesma peça que a anterior, sorteia novamente, como no tetris de gameboy
        if(shape.getClass().equals(taken.getClass())) {
            shape = ShapeFactory.generateShape();
        }
        return taken;
    }
}
