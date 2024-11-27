import java.awt.*;

public class Turtle {
    private double x, y;
    private double angle;
    private boolean pen = true;
    private Color pencolor = Color.BLACK;
    private double thickness;

    public Turtle(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.angle = 90;
        this.thickness = 1.5;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPenColor(Color color) {
        this.pencolor = color;
    }

    public void move(double dist, Arena arena) {
        double nx = x + dist * Math.cos(Math.toRadians(angle));
        double ny = y - dist * Math.sin(Math.toRadians(angle));

        // Ensure the Turtle stays within bounds (-500 to 500 in both directions)
        if (nx >= -arena.getWidthLimit() && nx <= arena.getWidthLimit()
                && ny >= -arena.getHeightLimit() && ny <= arena.getHeightLimit()) {
            if (pen) {
                arena.addLine(new Line((int) x, (int) y, (int) nx, (int) ny, pencolor, thickness));
            }
            x = nx;
            y = ny;
        }
    }

    public void rotate(double degrees) {
        angle = (angle + degrees) % 360;
    }

    public void pen_up() {
        pen = false;
    }

    public void pen_down() {
        pen = true;
    }

    public void drawTurtle(Graphics g) {
        int baseLength = 42;
        int height = 27;

        double tipX = x + height * Math.cos(Math.toRadians(angle));
        double tipY = y - height * Math.sin(Math.toRadians(angle));
        double leftBaseX = x - baseLength / 2 * Math.sin(Math.toRadians(angle));
        double leftBaseY = y - baseLength / 2 * Math.cos(Math.toRadians(angle));
        double rightBaseX = x + baseLength / 2 * Math.sin(Math.toRadians(angle));
        double rightBaseY = y + baseLength / 2 * Math.cos(Math.toRadians(angle));

        int[] xpoints = {(int) tipX, (int) leftBaseX, (int) rightBaseX};
        int[] ypoints = {(int) tipY, (int) leftBaseY, (int) rightBaseY};

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillPolygon(xpoints, ypoints, 3);
    }

    public void resetPosition(Arena arena) {
        this.x = 0;
        this.y = 0;
        this.angle = 90;
        this.pen = true;
        this.pencolor = Color.BLACK;
    }
}
