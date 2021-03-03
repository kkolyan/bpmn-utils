package com.nplekhanov.swing;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.EnumSet;

public class MouseTracker extends MouseAdapter implements MouseState {
    private float x;
    private float y;
    private final EnumSet<MouseButton> wasJustPressed = EnumSet.noneOf(MouseButton.class);
    private final EnumSet<MouseButton> wasJustReleased = EnumSet.noneOf(MouseButton.class);
    private final EnumSet<MouseButton> isPressed = EnumSet.noneOf(MouseButton.class);
    private float wheelDelta;

    public void reset() {
        wasJustPressed.clear();
        wasJustReleased.clear();
        wheelDelta = 0;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean wasJustPressed(final MouseButton button) {
        return wasJustPressed.contains(button);
    }

    @Override
    public boolean wasJustReleased(final MouseButton button) {
        return wasJustReleased.contains(button);
    }

    @Override
    public boolean isPressed(final MouseButton button) {
        return isPressed.contains(button);
    }

    @Override
    public float getWheelDelta() {
        return wheelDelta;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        MouseButton button = MouseButton.ofAwt(e.getButton());
        wasJustPressed.add(button);
        isPressed.add(button);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        MouseButton button = MouseButton.ofAwt(e.getButton());
        wasJustReleased.add(button);
        isPressed.remove(button);
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        wheelDelta = e.getWheelRotation();
    }
}