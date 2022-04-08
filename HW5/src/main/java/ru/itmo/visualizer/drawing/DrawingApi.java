package ru.itmo.visualizer.drawing;

/**
 * @author Madiyar Nurgazin
 */
public interface DrawingApi {
    int HEIGHT = 600;
    int WIDTH = 800;

    default int getDrawingAreaWidth() {
        return WIDTH;
    }

    default int getDrawingAreaHeight() {
        return HEIGHT;
    }

    void drawCircle(Point point, double size);

    void drawLine(Point a, Point b);

    void visualize();
}
