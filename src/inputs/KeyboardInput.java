package inputs;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import enums.CommandsEnum;

public class KeyboardInput extends KeyAdapter implements IInputs {
    
    private static final long RELEASE_GRACE_NANOS = 20_000_000L; // tolerância para prevenir sequencias de pressionar e soltar ao segurar um botão em alguns SOs

    private final Map<Integer, CommandsEnum> keyMap = new java.util.HashMap<>();
    private final Map<CommandsEnum, Boolean> held = new EnumMap<>(CommandsEnum.class);
    private final Map<CommandsEnum, Long> releasedAt = new EnumMap<>(CommandsEnum.class);
    private final Queue<CommandsEnum> pressed = new ConcurrentLinkedQueue<>();

    public KeyboardInput() {
        keyMap.put(KeyEvent.VK_LEFT, CommandsEnum.LEFT);
        keyMap.put(KeyEvent.VK_RIGHT, CommandsEnum.RIGHT);
        keyMap.put(KeyEvent.VK_DOWN, CommandsEnum.DOWN);
        keyMap.put(KeyEvent.VK_ENTER, CommandsEnum.START);
        keyMap.put(KeyEvent.VK_Z, CommandsEnum.ROTATE_CLOCKWISE);
        keyMap.put(KeyEvent.VK_X, CommandsEnum.ROTATE_COUNTERCLOCKWISE);
    }

    @Override
    public void bindInput(Component eventSource) {
        eventSource.addKeyListener(this);
    }

    @Override
    public CommandsEnum pollCommand() {
        return pressed.poll();
    }

    @Override
    public boolean isHeld(CommandsEnum command) {
        if (held.getOrDefault(command, false))
            return true;

        long released = releasedAt.getOrDefault(command, Long.MIN_VALUE / 2);   //
        return System.nanoTime() - released < RELEASE_GRACE_NANOS;              // filtro necessário para prevenir sequencias de pressionar e soltar em alguns SOs
    }

    @Override
    public void keyPressed(KeyEvent event) {
        CommandsEnum command = keyMap.get(event.getKeyCode());
        if (command == null)
            return;

        boolean wasHeld = isHeld(command);
        held.put(command, true);
        if (!wasHeld)                  // previne auto-repeat do SO
            pressed.add(command);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        CommandsEnum command = keyMap.get(event.getKeyCode());
        if (command == null)
            return;

        held.put(command, false);
        releasedAt.put(command, System.nanoTime());
    }

}
