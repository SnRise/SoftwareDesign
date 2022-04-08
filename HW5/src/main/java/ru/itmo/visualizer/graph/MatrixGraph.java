package ru.itmo.visualizer.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.itmo.visualizer.drawing.DrawingApi;

/**
 * @author Madiyar Nurgazin
 */
public class MatrixGraph extends Graph {
    private List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(BufferedReader reader) throws IOException {
        String input = reader.readLine();

        matrix = new ArrayList<>();
        while (input != null && !input.isBlank()) {
            String[] inputRow = input.trim().split(" ");
            List<Boolean> row = new ArrayList<>();

            for (String element : inputRow) {
                if (!(element.equals("0") || element.equals("1"))) {
                    throw new IllegalArgumentException("Matrix must contain only 0 or 1 values");
                }
                row.add(element.equals("1"));
            }
            matrix.add(row);

            input = reader.readLine();
        }

        int matrixSize = matrix.size();
        for (List<Boolean> row : matrix) {
            if (row.size() != matrixSize) {
                throw new IllegalArgumentException(
                        "The number of rows and the number of columns of each row must be the same"
                );
            }
        }
    }

    @Override
    protected void drawGraph() {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j)) {
                    drawEdge(new Edge(new Vertex(i), new Vertex(j)));
                }
            }
        }
    }

    @Override
    protected int getGraphSize() {
        return matrix.size();
    }
}
