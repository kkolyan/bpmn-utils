package com.nplekhanov.swing;

public interface KeyboardState {
    boolean isPressed(Key key);
    boolean wasJustPressed(Key key);
    boolean wasJustReleased(Key key);
}
