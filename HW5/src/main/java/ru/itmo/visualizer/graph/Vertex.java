package ru.itmo.visualizer.graph;

import java.util.Objects;

/**
 * @author Madiyar Nurgazin
 */
public class Vertex {
    private final int number;

    public Vertex(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vertex)) {
            return false;
        }
        Vertex vertex = (Vertex) other;
        return number == vertex.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
