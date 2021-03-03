package com.nplekhanov.swing;

public class ApplicationEnv {
    public final int width;
    public final int height;
    public final MouseState mouse;
    public final KeyboardState keyboard;

    public ApplicationEnv(final int width, final int height, final MouseState mouse, final KeyboardState keyboard) {
        this.width = width;
        this.height = height;
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

}
