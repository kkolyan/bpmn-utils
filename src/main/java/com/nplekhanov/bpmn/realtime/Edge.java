package com.nplekhanov.bpmn.realtime;

class Edge {
    final Node a;
    final Node b;
    final double value;

    public Edge(final Node a, final Node b, final double value) {
        this.a = a;
        this.b = b;
        this.value = value;
    }
}
