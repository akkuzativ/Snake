package com.company;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Objects;

/***
 * Used to read keyboard input and translate it to easier format
 */
public class KeyboardHandler implements KeyListener {
    /***
     * Used commands
     */
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

    /***
     * Returns the command associated with the most recently pressed key
     * @return recently pressed key command
     */
    public KeyCommand getRecentlyPressedKey() {
        return recentlyPressedKey;
    }

    /***
     * Fires when a key is pressed
     * @param e event called on key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        recentlyPressedKey = Objects.requireNonNullElse(keyCodeMapping.get(e.getKeyCode()), KeyCommand.NOTHING);
    }

    /***
     * Fires when a key is released
     * @param e event called on key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /***
     * Fires when a key is typed
     * @param e event called on key type
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
