package inputs;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import enums.CommandsEnum;

public class KeyboardInput implements IInputs {

    @Override
    public void attach(Component source, Consumer<CommandsEnum> onCommand) {
        source.setFocusable(true);
        source.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                CommandsEnum command = switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT  -> CommandsEnum.LEFT;
                    case KeyEvent.VK_RIGHT -> CommandsEnum.RIGHT;
                    case KeyEvent.VK_DOWN  -> CommandsEnum.DOWN;
                    case KeyEvent.VK_Z     -> CommandsEnum.ROTATE_L;
                    case KeyEvent.VK_X, KeyEvent.VK_UP -> CommandsEnum.ROTATE_R;
                    case KeyEvent.VK_ENTER -> CommandsEnum.START;
                    default -> null; // tecla que o jogo não usa
                };

                if (command != null) {
                    onCommand.accept(command);
                }
            }
        });
        source.requestFocusInWindow(); // sem foco, o painel não recebe teclas
    }

}
