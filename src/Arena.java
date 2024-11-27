import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Arena extends JPanel {
    private Turtle turtle;
    private List<Line> lines = new ArrayList<>();
    private final int logicalSize = 1000; 
    private final int panelSize = 600;
    private double scaleFactor;

    public Arena() {
        turtle = new Turtle(0, 0);
        scaleFactor = (double) panelSize / logicalSize;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void reset() {
        lines.clear();
        turtle.resetPosition(this);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(panelSize / 2, panelSize / 2);
        g2d.scale(scaleFactor, scaleFactor);

        for (Line line : lines) {
            g2d.setColor(line.getColor());
            g2d.setStroke(new BasicStroke((float) line.get_thickness() / (float) scaleFactor));
            g2d.drawLine(line.x1, line.y1, line.x2, line.y2);
        }

        turtle.drawTurtle(g2d);
    }

    public int getWidthLimit() {
        return 500;
    }

    public int getHeightLimit() {
        return 500;
    }

    @Override
public Dimension getPreferredSize() {
    return new Dimension(logicalSize, logicalSize);
}

}
