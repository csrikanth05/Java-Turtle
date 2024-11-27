import java.awt.*;
import javax.swing.*;
public class Parser {
    public static void execute(String command, Turtle turtle, Arena a, JTextArea commandHistory) {
        String[] tokens = command.trim().split("\\s+");
        int i = 0;

        while (i < tokens.length) {
            String token = tokens[i].toUpperCase();
            try {
                switch (token) {
                    case "FD":
                        i = handleMovement(tokens, i, turtle, a, 1, "FD");
                        break;

                    case "BK":
                        i = handleMovement(tokens, i, turtle, a, -1, "BK");
                        break;

                    case "LT":
                        i = handleRotation(tokens, i, turtle, a, 1, "LT");
                        break;

                    case "RT":
                        i = handleRotation(tokens, i, turtle, a, -1, "RT");
                        break;

                    case "PU":
                        turtle.pen_up();
                        i++;
                        break;

                    case "PD":
                        turtle.pen_down();
                        i++;
                        break;

                    case "PC":
                        i = handleColor(tokens, i, turtle, "PC");
                        break;

                    case "RESET":
                        a.reset();
                        turtle.resetPosition(a);
                        a.repaint();
                        i++;
                        break;

                    case "RETURN":
                        turtle.resetPosition(a);
                        a.repaint();
                        i++;
                        break;

                    case "SQUARE":
                        i = handleShape(tokens, i, turtle, a, "SQUARE", 1);
                        break;

                    case "RECT":
                        i = handleShape(tokens, i, turtle, a, "RECT", 2);
                        break;

                    case "CIRCLE":
                        i = handleShape(tokens, i, turtle, a, "CIRCLE", 1);
                        break;

                    case "REPEAT":
                        i = handleRepeat(tokens, i, turtle, a, commandHistory);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown command: " + token);
                }
            } catch (Exception e) {
                commandHistory.append("Error: " + e.getMessage() + "\n");
                break;
            }
        }
    }

    private static int handleMovement(String[] tokens, int index, Turtle turtle, Arena a, int direction, String command) {
        if (index + 1 >= tokens.length || !isNumeric(tokens[index + 1])) {
            throw new IllegalArgumentException(command + " requires 1 numeric argument.");
        }
        double distance = Double.parseDouble(tokens[index + 1]);
        turtle.move(direction * distance, a);
        a.repaint();
        return index + 2;
    }

    private static int handleRotation(String[] tokens, int index, Turtle turtle, Arena a, int direction, String command) {
        if (index + 1 >= tokens.length || !isNumeric(tokens[index + 1])) {
            throw new IllegalArgumentException(command + " requires 1 numeric argument.");
        }
        double angle = Double.parseDouble(tokens[index + 1]);
        turtle.rotate(direction * angle);
        a.repaint();
        return index + 2;
    }

    private static int handleColor(String[] tokens, int index, Turtle turtle, String command) {
        if (index + 1 >= tokens.length || !isNumeric(tokens[index + 1])) {
            throw new IllegalArgumentException(command + " requires a numeric color code.");
        }
        int colorCode = Integer.parseInt(tokens[index + 1]);
        Color color = getColorByCode(colorCode);
        if (color == null) {
            throw new IllegalArgumentException("Invalid color code: " + colorCode);
        }
        turtle.setPenColor(color);
        return index + 2;
    }

    private static Color getColorByCode(int code) {
        switch (code) {
        case 0: return Color.BLACK;	
        case 1: return Color.BLUE;
        case 2: return Color.RED;
        case 3: return Color.GREEN;
        case 4: return Color.YELLOW;
        case 5: return Color.ORANGE;
        case 6: return new Color(139, 69, 19);
        case 7: return Color.PINK;
        case 8: return Color.CYAN;
        case 9: return Color.MAGENTA;
        case 10: return new Color(128, 0, 128); // Purple
        case 11: return new Color(128, 128, 128); // Gray
        case 12: return new Color(192, 192, 192); // Light Gray
        case 13: return new Color(255, 215, 0); // Gold
        case 14: return new Color(255, 20, 147); // Deep Pink
        case 15: return new Color(0, 128, 128); // Teal
        default: return null;
        }
    }
    private static int handleShape(String[] tokens, int index, Turtle turtle, Arena a, String command, int numArgs) {
        if (index + numArgs >= tokens.length) {
            throw new IllegalArgumentException(command + " requires " + numArgs + " numeric argument(s).");
        }
        double[] args = new double[numArgs];
        for (int i = 0; i < numArgs; i++) {
            if (!isNumeric(tokens[index + 1 + i])) {
                throw new IllegalArgumentException(command + " requires numeric arguments.");
            }
            args[i] = Double.parseDouble(tokens[index + 1 + i]);
        }

        switch (command) {
            case "SQUARE":
                drawSquare(args[0], turtle, a);
                break;
            case "RECT":
                drawRectangle(args[0], args[1], turtle, a);
                break;
            case "CIRCLE":
                drawCircle(args[0], turtle, a);
                break;
        }
        return index + 1 + numArgs;
    }

    private static int handleRepeat(String[] tokens, int index, Turtle turtle, Arena a, JTextArea commandHistory) {
        if (index + 2 >= tokens.length || !tokens[index + 2].startsWith("[") || !tokens[tokens.length - 1].endsWith("]")) {
            throw new IllegalArgumentException("Malformed REPEAT syntax.");
        }
        int repeatCount = Integer.parseInt(tokens[index + 1]);
        String repeatCommands = String.join(" ", tokens).substring(
                String.join(" ", tokens).indexOf("[") + 1,
                String.join(" ", tokens).lastIndexOf("]")
        );
        for (int i = 0; i < repeatCount; i++) {
            execute(repeatCommands, turtle, a, commandHistory);
        }
        return tokens.length; // Skip past the entire repeat block
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void drawSquare(double side, Turtle turtle, Arena a) {
        for (int i = 0; i < 4; i++) {
            turtle.move(side, a);
            turtle.rotate(-90);
        }
        a.repaint();
    }

    private static void drawRectangle(double length, double width, Turtle turtle, Arena a) {
        for (int i = 0; i < 2; i++) {
            turtle.move(length, a);
            turtle.rotate(-90);
            turtle.move(width, a);
            turtle.rotate(-90);
        }
        a.repaint();
    }

    private static void drawCircle(double radius, Turtle turtle, Arena a) {
        double circumference = 2 * Math.PI * radius;
        int steps = 36;
        double stepLength = circumference / steps;
        double stepAngle = 360.0 / steps;

        for (int i = 0; i < steps; i++) {
            turtle.move(stepLength, a);
            turtle.rotate(-stepAngle);
        }
        a.repaint();
    }
}