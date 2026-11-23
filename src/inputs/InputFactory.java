package inputs;

import enums.InputsEnum;
import utils.IFactory;

public class InputFactory implements IFactory<IInputs, InputsEnum> {
    
    @Override
    public IInputs create(InputsEnum mode) {
        return switch (mode) {
            case KEYBOARD -> new KeyboardInput();
        };
    }
    
}
