package inputs;

import java.awt.Component;
import java.util.function.Consumer;

import enums.CommandsEnum;

public interface IInputs {

    // Liga a entrada (teclado, por exemplo) a um componente da tela.
    // Sempre que o jogador pedir algo, onCommand é chamado com o comando.
    void attach(Component source, Consumer<CommandsEnum> onCommand);

}
