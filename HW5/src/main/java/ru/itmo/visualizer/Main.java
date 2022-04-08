package ru.itmo.visualizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ru.itmo.visualizer.drawing.DrawingApi;
import ru.itmo.visualizer.drawing.DrawingAwt;
import ru.itmo.visualizer.drawing.DrawingJavaFx;
import ru.itmo.visualizer.graph.EdgesListGraph;
import ru.itmo.visualizer.graph.Graph;
import ru.itmo.visualizer.graph.MatrixGraph;

/**
 * @author Madiyar Nurgazin
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length != 3) {
            System.out.println("Invalid arguments, expected: {edgeList|matrix} {awt|javafx} <fileName>");
            return;
        }

        DrawingApi drawingApi = switch (args[1]) {
            case "awt" -> new DrawingAwt();
            case "javafx" -> new DrawingJavaFx();
            default -> throw new IllegalArgumentException("Unsupported drawing api: " + args[1]);
        };

        Graph graph = switch (args[0]) {
            case "edgeList" -> new EdgesListGraph(drawingApi);
            case "matrix" -> new MatrixGraph(drawingApi);
            default -> throw new IllegalArgumentException("Unknown graph: " + args[0]);
        };

        Path filePath = Paths.get(Main.class.getResource(args[2]).toURI());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toFile())));
        graph.readGraph(reader);
        graph.visualize();
    }
}
