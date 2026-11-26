package inputs;

import enums.CommandsEnum;
import java.awt.Component;

public interface IInputs {
    
    void bindInput(Component eventSource); // registra o input como ouvinte de entradas vindas do componete de exibição

    CommandsEnum pollCommand();

    boolean isHeld(CommandsEnum command);
}
