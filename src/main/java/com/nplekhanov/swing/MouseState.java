package com.nplekhanov.swing;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public interface MouseState {

    Vector2D getPosition();

    boolean wasJustPressed(MouseButton button);

    boolean wasJustReleased(MouseButton button);

    boolean isPressed(MouseButton button);

    float getWheelDelta();
}
