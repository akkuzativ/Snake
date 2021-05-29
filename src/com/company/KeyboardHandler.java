package com.company;

import java.awt.event.*;
import java.util.HashMap;


public class KeyboardHandler implements KeyListener {
    enum KeyCommand {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ESC,
        NOTHING
    }
    private KeyCommand recentlyPressedKey;
    private final HashMap<Integer, KeyCommand> keyCodeMapping = new HashMap<>(){{
        put(38, KeyCommand.UP);
        put(40, KeyCommand.DOWN);
        put(37, KeyCommand.LEFT);
        put(39, KeyCommand.RIGHT);
        put(27, KeyCommand.ESC);
    }};

    KeyboardHandler() {
        this.recentlyPressedKey = KeyCommand.NOTHING;
    }

    public KeyCommand getRecentlyPressedKey() {
        return recentlyPressedKey;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        recentlyPressedKey = keyCodeMapping.get(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (recentlyPressedKey == keyCodeMapping.get(e.getKeyCode())) {
            recentlyPressedKey = KeyCommand.NOTHING;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
