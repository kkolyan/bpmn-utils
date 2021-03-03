package com.nplekhanov.bpmn.realtime;

import com.nplekhanov.swing.Application;
import com.nplekhanov.swing.ApplicationEnv;
import com.nplekhanov.swing.InteractiveCanvas;
import com.nplekhanov.swing.Key;
import com.nplekhanov.swing.MouseButton;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class BrowseDiagram implements Application {
    public static void main(String[] args) {

        String sourceFile = args[0];

        BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(() -> {
            try {
                return new FileInputStream(sourceFile);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }, true, false);

        InteractiveCanvas.start(1000, new Dimension(1200, 800), new BrowseDiagram(model));
    }

    private Map<String, Node> nodes;
    private List<Edge> edges;
    private Vector2D cameraOffset;
    private Vector2D cameraDragPivot;
    private Node draggedNode;
    private float wheel;

    private StringBuilder search = new StringBuilder();

    public BrowseDiagram(final BpmnModel model) {
        nodes = new TreeMap<>();
        edges = new ArrayList<>();
        cameraOffset = Vector2D.ZERO;
        cameraDragPivot = Vector2D.ZERO;
        Random random = new Random(123);
        if (model.getProcesses().size() != 1) {
            throw new IllegalStateException();
        }
        Process process = model.getProcesses().get(0);
        for (final FlowElement flowElement : process.getFlowElements()) {
            Node node = new Node(flowElement.getId(), flowElement.getId() + " : " + flowElement.getClass().getSimpleName());
            GraphicInfo graphicInfo = model.getLocationMap().get(flowElement.getId());
            if (graphicInfo != null) {
                node.position = new Vector2D(
                    graphicInfo.getX(),
                    graphicInfo.getY()
                );
            } else {
                node.position = new Vector2D(
                    random.nextDouble(),
                    random.nextDouble()
                ).scalarMultiply(100);
            }
            nodes.put(flowElement.getId(), node);
        }
        Map<String, Set<String>> neighbours = new HashMap<>();
        for (final FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof SequenceFlow) {
                final Node self = nodes.get(flowElement.getId());
                final Node source = nodes.get(((SequenceFlow) flowElement).getSourceRef());
                final Node target = nodes.get(((SequenceFlow) flowElement).getTargetRef());
                final double value = 1f;
                edges.add(new Edge(source, self, value));
                edges.add(new Edge(self, target, value));
                neighbours.computeIfAbsent(self.id, s -> new HashSet<>()).add(source.id);
                neighbours.computeIfAbsent(self.id, s -> new HashSet<>()).add(target.id);
                neighbours.computeIfAbsent(source.id, s -> new HashSet<>()).add(self.id);
                neighbours.computeIfAbsent(target.id, s -> new HashSet<>()).add(self.id);
            }
        }
        for (final Node node : nodes.values()) {
            if (node.position.equals(Vector2D.ZERO)) {
                Vector2D sum = Vector2D.ZERO;
                int total = 0;
                for (final String s : neighbours.get(node.id)) {
                    Node neighbour = nodes.get(s);
                    if (!neighbour.position.equals(Vector2D.ZERO)) {
                        sum = sum.add(neighbour.position);
                        total++;
                    }
                }
                node.position = sum.scalarMultiply(1d / total);
            }
        }

    }

    @Override
    public void update(final Graphics2D canvas, final ApplicationEnv env) {
        wheel += env.mouse.getWheelDelta();

        double scale = Math.pow(1.2, -wheel);

        Vector2D mouseAtScreen = env.mouse.getPosition();
        if (env.mouse.wasJustPressed(MouseButton.RIGHT)) {
            this.cameraDragPivot = mouseAtScreen;
        }
        if (env.mouse.wasJustReleased(MouseButton.RIGHT)) {
            cameraOffset = cameraOffset.subtract(cameraDragPivot.subtract(mouseAtScreen).scalarMultiply(1d / scale));
        }

        Vector2D halfWindow = new Vector2D(env.width / 2d, env.height / 2d);
        if (env.mouse.wasJustPressed(MouseButton.LEFT)) {
            draggedNode = nodes.values().stream()
                .min(Comparator.comparing(node -> node.position
                    .add(cameraOffset)
                    .scalarMultiply(scale)
                    .add(halfWindow)
                    .distance(mouseAtScreen)))
                .orElse(null);
        }
        if (env.mouse.wasJustReleased(MouseButton.LEFT)) {
            draggedNode = null;
        }

        if (env.keyboard.wasJustPressed(Key.ESCAPE)) {
            search.delete(0, search.length());
        }
        if (env.keyboard.wasJustPressed(Key.BACK_SPACE) && search.length() > 0) {
            search.delete(search.length() - 1, search.length());
        }


        if (draggedNode != null && env.keyboard.isPressed(Key.CONTROL) && env.keyboard.wasJustPressed(Key.C)) {
            StringSelection content = new StringSelection(draggedNode.id);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(content, content);
        } else {
            for (final Key key : Key.values()) {
                if (key.isTypable()) {
                    if (env.keyboard.wasJustPressed(key)) {
                        search.append(key.character);
                    }
                }
            }
        }

        if (env.keyboard.wasJustPressed(Key.ENTER) && search.length() > 0) {
            String searchString = search.toString().toLowerCase();
            Vector2D sum = Vector2D.ZERO;
            int matchedNodes = 0;
            for (final Node node : nodes.values()) {
                if (node.caption.toLowerCase().startsWith(searchString)) {
                    sum = sum.add(node.position);
                    matchedNodes++;
                }
            }
            if (matchedNodes > 0) {
                cameraOffset = sum.scalarMultiply(-1d / matchedNodes);
            }
        }

        if (draggedNode != null) {
            draggedNode.position = mouseAtScreen
                .subtract(halfWindow)
                .scalarMultiply(1d / scale)
                .subtract(cameraOffset);
        }

        for (int i = 0; i < 20; i++) {

            double attraction = 1d / 5;
            double nearAttraction = 1d / 10;
            double repulsion = 1d / 10000d;

            double movement = 1;

            double nearRepulsion = 1d / 10d;
            double maxForce = 1000;

            double edgeLength = 100;

            double squareDist = 1000;
            double nearDist = 100;
            double powerRepulsion = 50000;


            for (final Node node : nodes.values()) {
                node.force = Vector2D.ZERO;
                for (final Node another : nodes.values()) {
                    if (node == another) {
                        continue;
                    }
                    Vector2D vector = node.position.subtract(another.position);
                    double distance = vector.getNorm();
                    Vector2D forceNormal = vector.normalize();
                    if (distance < squareDist) {
                        double force = repulsion * Math.sqrt((squareDist * squareDist - distance * distance));
                        node.force = node.force.add(forceNormal.scalarMultiply(force));
                    }
                    if (distance < nearDist) {
                        double nearForce = nearRepulsion * Math.sqrt((nearDist * nearDist - distance * distance));
                        node.force = node.force.add(forceNormal.scalarMultiply(nearForce));
                    }
                    double powerForce = powerRepulsion * Math.pow(distance, -2);
                    node.force = node.force.add(forceNormal.scalarMultiply(powerForce));
                }
            }
            for (final Edge edge : edges) {
                Vector2D vector = edge.b.position.subtract(edge.a.position);
                double distance = vector.getNorm();
                double force;
                if (distance > edgeLength) {
                    force = attraction * (distance - edgeLength);
                } else {
                    force = nearAttraction * (distance);
                }
                edge.a.force = edge.a.force.add(vector.normalize().scalarMultiply(force / 2));
                edge.b.force = edge.b.force.subtract(vector.normalize().scalarMultiply(force / 2));
            }
            for (final Node node : nodes.values()) {
                if (node == draggedNode) {
                    continue;
                }
                if (node.force.getNorm() > maxForce) {
                    node.force = node.force.normalize().scalarMultiply(maxForce);
                }
                node.position = node.position.add(node.force.scalarMultiply(movement));
            }
        }


        Vector2D tempCameraOffset = halfWindow.scalarMultiply(1d / scale);

        tempCameraOffset = tempCameraOffset.add(cameraOffset);

        if (env.mouse.isPressed(MouseButton.RIGHT)) {
            tempCameraOffset = tempCameraOffset.subtract(cameraDragPivot.subtract(mouseAtScreen).scalarMultiply(1d / scale));
        }

        canvas.setBackground(Color.black);
        canvas.fillRect(0, 0, env.width, env.height);

        String searchAsString = search.toString().toLowerCase();

        List<Node> matched = new ArrayList<>();
        List<Node> nonMatched = new ArrayList<>();
        for (final Node node : nodes.values()) {
            if (!searchAsString.isEmpty() && node.caption.toLowerCase().contains(searchAsString)) {
                matched.add(node);
            } else {
                nonMatched.add(node);
            }
        }

        NodeList[] nodeLists = {new NodeList(nonMatched, false), new NodeList(matched, true)};

        for (final NodeList nodeList : nodeLists) {
            for (final Node node : nodeList.nodes) {
                float x = (float) (scale * tempCameraOffset.getX() + scale * node.position.getX());
                float y = (float) (scale * tempCameraOffset.getY() + scale * node.position.getY());

                if (nodeList.matched) {
                    canvas.setColor(Color.white);
                } else {
                    canvas.setColor(Color.gray);
                }
                canvas.drawString(
                    node.caption,
                    x,
                    y
                );
            }
        }

        for (final Edge edge : edges) {
            canvas.setColor(Color.green);
            drawArrowLine(canvas,
                (int) (scale * tempCameraOffset.getX() + scale * edge.a.position.getX()),
                (int) (scale * tempCameraOffset.getY() + scale * edge.a.position.getY()),
                (int) (scale * tempCameraOffset.getX() + scale * edge.b.position.getX()),
                (int) (scale * tempCameraOffset.getY() + scale * edge.b.position.getY()),
                16,
                4
            );
        }

        canvas.setColor(Color.white);
        canvas.drawString(searchAsString, 0, 20);
    }

    /**
     * Draw an arrow line between two points.
     *
     * @param g  the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void destroy() {
    }


    private static class NodeList {
        final List<Node> nodes;
        final boolean matched;

        public NodeList(final List<Node> nodes, final boolean matched) {
            this.nodes = nodes;
            this.matched = matched;
        }
    }
}
