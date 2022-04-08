package ru.itmo.visualizer.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.itmo.visualizer.drawing.DrawingApi;
import ru.itmo.visualizer.drawing.Point;

/**
 * @author Madiyar Nurgazin
 */
public abstract class Graph {
    private final static int VERTEX_SIZE = 10;
    private final static int MARGIN = 50;

    private final DrawingApi drawingApi;
    private final Point center;
    private final double radius;
    private final Map<Vertex, Point> usedVertexes = new HashMap<>();
    private final Set<Edge> usedEdges = new HashSet<>();

    private int nextVertex;
    private double angle;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        this.center = new Point(drawingApi.getDrawingAreaWidth() / 2.0, drawingApi.getDrawingAreaHeight() / 2.0);
        this.radius = Math.min(drawingApi.getDrawingAreaWidth(), drawingApi.getDrawingAreaHeight()) * 0.5 - MARGIN;
    }

    public void visualize() {
        nextVertex = 0;
        angle = 2 * Math.PI / getGraphSize();

        drawGraph();
        drawingApi.visualize();
    }

    protected Point drawVertex(Vertex vertex) {
        if (usedVertexes.containsKey(vertex)) {
            return usedVertexes.get(vertex);
        }

        Point point = getNextPoint();
        usedVertexes.put(vertex, point);

        drawingApi.drawCircle(point, VERTEX_SIZE);
        return point;
    }

    protected void drawEdge(Edge edge) {
        if (usedEdges.contains(edge)) {
            return;
        }
        usedEdges.add(edge);

        Point a = drawVertex(edge.getFrom());
        Point b = drawVertex(edge.getTo());
        drawingApi.drawLine(a, b);
    }

    private Point getNextPoint() {
        double nextAngle = nextVertex * angle;
        double x = radius * Math.cos(nextAngle) + center.getX();
        double y = radius * Math.sin(nextAngle) + center.getY();

        nextVertex++;
        return new Point(x, y);
    }

    public abstract void readGraph(BufferedReader reader) throws IOException;

    protected abstract void drawGraph();

    protected abstract int getGraphSize();
}
