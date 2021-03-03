package com.nplekhanov.swing;

import java.awt.Graphics2D;

public interface Application {
    String getTitle();

    void update(Graphics2D canvas, ApplicationEnv env);

    void destroy();
}
