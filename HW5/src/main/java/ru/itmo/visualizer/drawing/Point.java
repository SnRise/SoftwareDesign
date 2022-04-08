package ru.itmo.visualizer.drawing;

import java.util.Objects;

/**
 * @author Madiyar Nurgazin
 */
public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point point = (Point) other;
        return x == point.getX() && y == point.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
