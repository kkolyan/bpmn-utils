package com.nplekhanov.swing;

import java.awt.event.MouseEvent;

public enum MouseButton {
    LEFT,
    MIDDLE,
    RIGHT,
    UNKNOWN,
    ;

    public static MouseButton ofAwt(final int button) {
        switch (button) {
            case MouseEvent.BUTTON1: return LEFT;
            case MouseEvent.BUTTON2: return MIDDLE;
            case MouseEvent.BUTTON3: return RIGHT;
        }
        return UNKNOWN;
    }
}
