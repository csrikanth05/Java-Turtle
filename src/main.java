import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen(() -> showMainFrame()).setVisible(true);
        });
    }

    private static void showMainFrame() {
        JFrame arenaFrame = new JFrame("Turtle");
        arenaFrame.setSize(850, 600);
        arenaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        arenaFrame.setLocationRelativeTo(null);

        Arena canvas = new Arena();
        canvas.setPreferredSize(new Dimension(1000, 1000));
        canvas.setBackground(Color.WHITE);
         JScrollPane scrollPane = new JScrollPane(canvas);
    scrollPane.setPreferredSize(new Dimension(600, 600));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel codePanel = new JPanel();
        codePanel.setLayout(new BorderLayout());

        JTextArea commandHistory = new JTextArea(15, 30);
        commandHistory.setEditable(false);
        commandHistory.setBackground(new Color(230, 247, 255));
        JScrollPane commandHistoryScroll = new JScrollPane(commandHistory);

        JTextField commandInput = new JTextField(30);
        commandInput.setBackground(Color.WHITE);

        JButton compileButton = new JButton("Compile");
        compileButton.setBackground(new Color(200, 230, 255));
        compileButton.setFocusPainted(false);
         ActionListener compileAction = e -> {
        String command = commandInput.getText().trim();
        if (!command.isEmpty()) {
            commandHistory.append(command + "\n");
            Parser.execute(command, canvas.getTurtle(), canvas, commandHistory);
            commandInput.setText("");
        }
    };
        compileButton.addActionListener(compileAction);
       commandInput.addActionListener(compileAction);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(commandInput, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(compileButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        codePanel.add(commandHistoryScroll, BorderLayout.CENTER);
        codePanel.add(inputPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, codePanel);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(1.0);

        arenaFrame.add(splitPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        Font menuFont = new Font("Consolas", Font.PLAIN, 13);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(menuFont);
        fileMenu.setBackground(new Color(161, 218, 252));
        JMenuItem openFile = new JMenuItem("Open");
        openFile.setFont(menuFont);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.setFont(menuFont);
        saveFile.addActionListener(e -> save(commandHistory, arenaFrame));
        openFile.addActionListener(e -> openFile(commandHistory, arenaFrame, canvas));
        fileMenu.add(openFile);
        fileMenu.add(saveFile);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(menuFont);
        JMenuItem userGuide = new JMenuItem("User Guide");
        userGuide.setFont(menuFont);
        userGuide.addActionListener(e -> new UserGuide(arenaFrame).setVisible(true));
        helpMenu.add(userGuide);

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setFont(menuFont);
        aboutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new AboutPage(arenaFrame).setVisible(true);
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        arenaFrame.setJMenuBar(menuBar);
        arenaFrame.setVisible(true);
    }

    private static void save(JTextArea commandHistory, JFrame arenaFrame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Commands");
        if (fileChooser.showSaveDialog(arenaFrame) == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write(commandHistory.getText());
                JOptionPane.showMessageDialog(arenaFrame, "Commands saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(arenaFrame, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void openFile(JTextArea commandHistory, JFrame arenaFrame, Arena canvas) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(arenaFrame) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    commandHistory.append(line + "\n");
                    Parser.execute(line.trim(), canvas.getTurtle(), canvas, commandHistory);
                }
                JOptionPane.showMessageDialog(arenaFrame, "File loaded and commands executed successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(arenaFrame, "Error opening file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void showAboutDialog(JFrame parentFrame, String title, String message) {
        JDialog aboutDialog = new JDialog(parentFrame, title, true);
        aboutDialog.setSize(400, 200);
        aboutDialog.setLocationRelativeTo(parentFrame);

        JTextArea textArea = new JTextArea();
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        aboutDialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        aboutDialog.setVisible(true);
    }
}

class AboutPage extends JFrame {
    public AboutPage(JFrame parentFrame) {
        setTitle("About Turtle Application");
        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JTextArea aboutText = new JTextArea();
        aboutText.setText(
            "Welcome to the Turtle, recreated with Java!\n\n" +
            "This application has been made to provide an interactive way to create art and learn programming concepts, using an easy-to-learn version of LOGO.\n" +
            "You can issue commands to the Turtle in this language, to draw shapes, navigate the canvas, and explore creativity.\n\n" +
            "Created by Srikanth Chandrasekaran, an aspiring data scientist and tech enthusiast at Mahindra University.\n\n" +
            "Thank you for exploring this project! I'm open to all suggestions for improving this app."
        );
        aboutText.setFont(new Font("Consolas", Font.PLAIN, 14));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);

        JScrollPane aboutScrollPane = new JScrollPane(aboutText);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton linkedinButton = new JButton("Visit My LinkedIn Profile");
        linkedinButton.setFont(new Font("Consolas", Font.BOLD, 14));
        linkedinButton.setBackground(new Color(66, 103, 178));
        linkedinButton.setForeground(Color.WHITE);
        linkedinButton.setFocusPainted(false);

        linkedinButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.linkedin.com/in/srikanth-chandrasekaran-aa8599254/"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unable to open LinkedIn profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(linkedinButton);

        contentPanel.add(aboutScrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);
    }
}


class UserGuide extends JFrame {
    private JTextArea contentArea;

    public UserGuide(JFrame parentFrame) {
        setTitle("User Guide");
        setSize(800, 600);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createMenuPanel(), createContentPanel());
        splitPane.setDividerLocation(200);
        add(splitPane);

        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1));
        String[] commands = {
            "FD", "BK", "PC", "PU", "PD", "CIRCLE", "REPEAT", 
            "SQUARE", "RECT", "LT", "RT", "RESET", "RETURN"
        };

        Arrays.sort(commands);

        for (String command : commands) {
            JButton button = new JButton(command);
            button.setPreferredSize(new Dimension(200, 40));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contentArea.setText(getCommandDescription(command));
                }
            });

            menuPanel.add(button);
        }

        return menuPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        return contentPanel;
    }

    private String getCommandDescription(String command) {
        switch (command) {
            case "FD":
                return "FD <distance> - Move forward by the specified distance.";
            case "BK":
                return "BK <distance> - Move backward by the specified distance.";
            case "PC":
                return "PC <number> - Choice of pen color according to color-code, given as follows:\n\n0: BLACK\n1: BLUE\n2: RED\n3: GREEN\n4: YELLOW\n5: ORANGE\n6: BROWN\n7: PINK\n8: CYAN\n9: MAGENTA\n10: PURPLE\n11: GRAY\n12: LIGHT GRAY\n13: GOLD\n14: DEEP PINK\n15: TEAL";
            case "PU":
                return "PU - Lift the pen (pen is up).";
            case "PD":
                return "PD - Lower the pen (pen is down).";
            case "CIRCLE":
                return "CIRCLE <radius> - Draw a circle with the given radius.";
            case "REPEAT":
                return "REPEAT <count> [commands] - Repeat the given commands for the specified count.";
            case "SQUARE":
                return "SQUARE - Draw a square using the current pen position and angle.";
            case "RECT":
                return "RECT <length> <width> - Draw a rectangle with the given length and width.";
            case "LT":
                return "LT <angle> - Turn left by the specified angle.";
            case "RT":
                return "RT <angle> - Turn right by the specified angle.";
            case "RESET":
                return "RESET - Reset the turtle's position and state.";
            case "RETURN":
                return "RETURN - Return to the previous state or position.";
            default:
                return "Unknown command.";
        }
    }
}

class SplashScreen extends JFrame {
    private static final int WIDTH = 340;
    private static final int HEIGHT = 240;
    private int x = 0;
    private boolean closed = false;

    public SplashScreen(Runnable onClose) {
        setTitle("Splash Screen");
        setSize(WIDTH, HEIGHT);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Timer animationTimer = new Timer(15, e -> {
            x += 5;
            if (x > WIDTH) x = 0;
            repaint();
        });

        animationTimer.start();

        Timer closeTimer = new Timer(3500, e -> {
            if (!closed) {
                closed = true;
                animationTimer.stop();
                onClose.run();
                dispose();
            }
        });

        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Consolas", Font.BOLD, 24));
        String title = "Turtle";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (WIDTH - titleWidth) / 2, 75);

        g2d.setColor(Color.GREEN);
        int[] xPoints = {x, x - 20, x - 20};
        int[] yPoints = {HEIGHT / 2, HEIGHT / 2 - 10, HEIGHT / 2 + 10};
        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Consolas", Font.BOLD, 16));
        String author = "verison 1.0";
        int authorWidth = g2d.getFontMetrics().stringWidth(author);
        g2d.drawString(author, (WIDTH - authorWidth) / 2, HEIGHT - 20);
    }
}
