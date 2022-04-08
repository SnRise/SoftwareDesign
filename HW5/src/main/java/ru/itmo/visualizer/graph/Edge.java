package ru.itmo.visualizer.graph;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * @author Madiyar Nurgazin
 */
public class Edge {
    @NotNull
    private final Vertex from;
    @NotNull
    private final Vertex to;

    public Edge(@NotNull Vertex from, @NotNull Vertex to) {
        this.from = from;
        this.to = to;
    }

    @NotNull
    public Vertex getFrom() {
        return from;
    }

    @NotNull
    public Vertex getTo() {
        return to;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) other;
        return from.equals(edge.getFrom()) && to.equals(edge.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

}
