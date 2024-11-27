import java.awt.Color;
public class Line {
    public int x1, y1, x2, y2;
    private Color color;
    private double thickness;

    public Line(int x1, int y1, int x2, int y2, Color color, double thickness) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.thickness = 2.5;
    }

    public Color getColor() {
        return color;
    }
    public double get_thickness(){
        return thickness;
    }
}
