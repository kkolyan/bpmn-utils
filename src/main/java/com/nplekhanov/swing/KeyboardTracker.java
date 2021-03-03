package com.nplekhanov.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EnumSet;

public class KeyboardTracker extends KeyAdapter implements KeyboardState {
    public KeyboardTracker() {
        super();
    }

    private final EnumSet<Key> isPressed = EnumSet.noneOf(Key.class);
    private final EnumSet<Key> justPressed = EnumSet.noneOf(Key.class);
    private final EnumSet<Key> justReleased = EnumSet.noneOf(Key.class);

    public void reset() {
        justPressed.clear();
        justReleased.clear();
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        isPressed.add(Key.ofAwt(e.getKeyCode()));
        justPressed.add(Key.ofAwt(e.getKeyCode()));
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        isPressed.remove(Key.ofAwt(e.getKeyCode()));
        justReleased.add(Key.ofAwt(e.getKeyCode()));
    }

    @Override
    public boolean isPressed(final Key key) {
        return isPressed.contains(key);
    }

    @Override
    public boolean wasJustPressed(final Key key) {
        return justPressed.contains(key);
    }

    @Override
    public boolean wasJustReleased(final Key key) {
        return justReleased.contains(key);
    }
}
