package com.nplekhanov.bpmn.realtime;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

class Node {
    final String id;
    final String caption;
    Vector2D position = Vector2D.ZERO;
    Vector2D velocity = Vector2D.ZERO;
    Vector2D force = Vector2D.ZERO;

    public Node(final String id, final String caption) {
        this.id = id;
        this.caption = caption;
    }
}
