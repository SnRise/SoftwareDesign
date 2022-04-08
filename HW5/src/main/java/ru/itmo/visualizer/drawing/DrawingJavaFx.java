package ru.itmo.visualizer.drawing;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * @author Madiyar Nurgazin
 */
public class DrawingJavaFx implements DrawingApi {
    private static final List<Shape> graph = new ArrayList<>();

    @Override
    public void drawCircle(Point center, double size) {
        graph.add(new Circle(center.getX(), center.getY(), size * 0.5));
    }

    @Override
    public void drawLine(Point a, Point b) {
        graph.add(new Line(a.getX(), a.getY(), b.getX(), b.getY()));
    }

    @Override
    public void visualize() {
        Application.launch(DrawingGraphApplication.class);
    }

    public static class DrawingGraphApplication extends Application {

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Graph visualization via JavaFx");
            Group root = new Group();

            root.getChildren().addAll(graph);

            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.show();
        }
    }
}
