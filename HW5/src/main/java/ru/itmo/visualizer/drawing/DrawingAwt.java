package ru.itmo.visualizer.drawing;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Madiyar Nurgazin
 */
public class DrawingAwt implements DrawingApi {
    private final static Color EDGE_COLOR = Color.ORANGE;
    private final static Color VERTEX_COLOR = Color.RED;

    private final List<Ellipse2D> circles = new ArrayList<>();
    private final List<Line2D> lines = new ArrayList<>();

    @Override
    public void drawCircle(Point point, double size) {
        circles.add(new Ellipse2D.Double(point.getX() - size * 0.5, point.getY() - size * 0.5, size, size));
    }

    @Override
    public void drawLine(Point a, Point b) {
        lines.add(new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY()));
    }

    @Override
    public void visualize() {
        Frame frame = new DrawingGraphFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setSize(getDrawingAreaWidth(), getDrawingAreaHeight());
        frame.setVisible(true);
    }

    private class DrawingGraphFrame extends Frame {
        public DrawingGraphFrame() {
            super("Graph visualization via Awt");
        }

        @Override
        public void paint(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics;

            graphics2D.setColor(EDGE_COLOR);
            lines.forEach(graphics2D::draw);

            graphics2D.setColor(VERTEX_COLOR);
            circles.forEach(graphics2D::fill);

        }

    }

}
