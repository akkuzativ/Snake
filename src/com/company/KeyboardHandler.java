package com.company;

import java.awt.event.*;
import java.util.HashMap;


public abstract class KeyboardHandler implements KeyListener {
    enum KeyCommand {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ESC,
        NOTHING
    }
    private KeyCommand recentlyPressedKey;
    private HashMap<Integer, KeyCommand> keyCodeMapping = new HashMap<>(){{
        put(224, KeyCommand.UP);
        put(225, KeyCommand.DOWN);
        put(226, KeyCommand.LEFT);
        put(227, KeyCommand.RIGHT);
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
        if (keyCodeMapping.get(e.getKeyCode()) == recentlyPressedKey) {
            recentlyPressedKey = KeyCommand.NOTHING;
        }
    }
}
