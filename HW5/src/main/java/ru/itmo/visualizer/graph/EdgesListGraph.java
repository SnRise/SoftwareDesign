package ru.itmo.visualizer.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.itmo.visualizer.drawing.DrawingApi;

/**
 * @author Madiyar Nurgazin
 */
public class EdgesListGraph extends Graph {
    private List<Edge> edges;

    public EdgesListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(BufferedReader reader) throws IOException {
        String input = reader.readLine();

        edges = new ArrayList<>();
        while (input != null && !input.isBlank()) {
            String[] inputEdge = input.trim().split(" ");
            if (inputEdge.length != 2) {
                throw new IllegalArgumentException("Edges must contain only 2 vertex");
            }

            int from = Integer.parseInt(inputEdge[0]);
            int to = Integer.parseInt(inputEdge[1]);

            edges.add(new Edge(new Vertex(from), new Vertex(to)));

            input = reader.readLine();
        }
    }

    @Override
    protected void drawGraph() {
        edges.forEach(this::drawEdge);
    }

    @Override
    protected int getGraphSize() {
        Set<Integer> ids = new HashSet<>();
        for (Edge edge : edges) {
            ids.add(edge.getFrom().getNumber());
            ids.add(edge.getTo().getNumber());
        }
        return ids.size();
    }

}
