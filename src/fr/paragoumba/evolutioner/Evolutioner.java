package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.Display;
import fr.paragoumba.evolutioner.graphic.StartingPanel;
import fr.paragoumba.evolutioner.graphic.TutorialPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static fr.paragoumba.evolutioner.graphic.Display.fps;

public class Evolutioner {

    public static boolean debug = false;
    public static final int BASE_TIME = 1;
    public static final String version = "0.5b1";
    public static final String title = "Evolutioner";
    public static JFrame frame = new JFrame(title + " - " + version);
    public static JLabel livingCreatures = new JLabel("None.");

    private static Display display = new Display();
    private static Thread displayThread = new Thread(display, "Thread-Display");
    private static Dimension oldDimension;

    public static void main(String[] args) throws InterruptedException {

        for (String arg : args) {

            if (arg.equalsIgnoreCase("debug")) {

                debug = true;
                break;

            }
        }

        //Initializing display's components
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowStateListener(e -> {if (e.getNewState() == WindowEvent.WINDOW_CLOSED) Display.stop();});
        frame.setPreferredSize(dimension);
        frame.setSize(dimension);

        try {

            frame.setIconImage(ImageIO.read(Evolutioner.class.getResourceAsStream("res/icon32x32.png")));

        } catch (IOException e) {

            e.printStackTrace();

        }

        /* StartingPanel */
        StartingPanel startingPanel = new StartingPanel();

        frame.setContentPane(startingPanel);
        frame.setLocationRelativeTo(null);
        frame.setTitle(title);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        Thread.sleep(2000);
        /* END */

        /* TutorialPanel */
        TutorialPanel tutorialPanel = new TutorialPanel();
        frame.setContentPane(tutorialPanel);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Thread.sleep(10000);
        /* END */

        /* Display */
        oldDimension = frame.getSize();
        InputHandler inputHandler = new InputHandler();

        frame.addKeyListener(inputHandler);
        frame.setFocusable(true);
        display.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                Display.setWorldSize();

                Dimension newDimension = e.getComponent().getSize();

                EntityManager.updateCoords(oldDimension, newDimension);
                Farm.updateCoords(oldDimension, newDimension);

                oldDimension = newDimension;

            }
        });

        Display.setWorldSize();
        EntityManager.setSigns();
        Farm.generateCreatures();
        Farm.startSimulation();

        frame.setContentPane(display);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        /* END*/

        displayThread.start();

    }

    public static void displayConfigFrame(){

        JFrame configFrame = new JFrame(title + " - Config");
        JPanel panel = new JPanel();

        JLabel creatureNumberJLabel = new JLabel("Number of creatures");
        JTextField creatureNumberTextField = new JTextField(Integer.toString(Farm.getDefaultCreatureNumber()), 3);

        JLabel fpsJLabel = new JLabel("FPS");
        JTextField fpsTextField = new JTextField(Integer.toString(fps), 2);

        JLabel widthJLabel = new JLabel("Width");
        JTextField widthTextField = new JTextField(Integer.toString(frame.getWidth()), 3);

        JLabel heightJLabel = new JLabel("Height");
        JTextField heightTextField = new JTextField(Integer.toString(frame.getHeight()), 3);

        JButton validateButton = new JButton("Validate");

        configFrame.setContentPane(panel);
        configFrame.setResizable(false);
        configFrame.setLocationRelativeTo(null);

        panel.add(creatureNumberJLabel);
        panel.add(creatureNumberTextField);
        panel.add(fpsJLabel);
        panel.add(fpsTextField);
        panel.add(widthJLabel);
        panel.add(widthTextField);
        panel.add(heightJLabel);
        panel.add(heightTextField);
        panel.add(validateButton);

        PlainDocument creatureNumberTextFieldDocument = (PlainDocument) creatureNumberTextField.getDocument();
        PlainDocument fpsTextFieldDocument = (PlainDocument) fpsTextField.getDocument();
        PlainDocument widthTextFieldDocument = (PlainDocument) widthTextField.getDocument();
        PlainDocument heightTextFieldDocument= (PlainDocument) heightTextField.getDocument();

        creatureNumberTextFieldDocument.setDocumentFilter(new DocumentFilter());
        fpsTextFieldDocument.setDocumentFilter(new DocumentFilter());
        widthTextFieldDocument.setDocumentFilter(new DocumentFilter());
        heightTextFieldDocument.setDocumentFilter(new DocumentFilter());

        validateButton.addActionListener(e -> {

            DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode();
            int width = displayMode.getWidth();
            int height = displayMode.getHeight();

            if (!creatureNumberTextField.getText().equals("")) Farm.setDefaultCreatureNumber(Integer.parseInt(creatureNumberTextField.getText()));

            if (!fpsTextField.getText().equals("")) {

                int fps = Integer.parseInt(fpsTextField.getText());
                int refreshRate = displayMode.getRefreshRate();

                if (!debug) {

                    fps = refreshRate != DisplayMode.REFRESH_RATE_UNKNOWN && fps > refreshRate ? refreshRate : fps;

                }

                Display.fps = fps;
            }

            if (!widthTextField.getText().equals("")){

                width = Integer.parseInt(widthTextField.getText());
                int screenWidth = displayMode.getWidth();
                if (!debug) width = width > screenWidth ? screenWidth : width;

            }

            if (!heightTextField.getText().equals("")){

                height = Integer.parseInt(heightTextField.getText());
                int screenHeight = displayMode.getHeight();
                if (!debug) height = height > screenHeight ? screenHeight : height;

            }

            frame.setSize(width, height);

        });

        configFrame.setAlwaysOnTop(true);
        configFrame.pack();
        configFrame.setVisible(true);

    }

    public static void displayStatsFrame(){

        JFrame logFrame = new JFrame(title + " - Stats");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Stats");

        logFrame.setContentPane(panel);
        panel.add(label);
        panel.add(livingCreatures);
        logFrame.setLocationRelativeTo(null);
        logFrame.pack();
        logFrame.setVisible(true);

    }

    public static void displayLogFrame(){

        JFrame logFrame = new JFrame(title + " - Logs");
        JPanel panel = new JPanel();
        TextArea textArea = new TextArea();

        panel.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                Dimension dimension = e.getComponent().getSize();

                textArea.setSize(dimension.width - 8, dimension.height - 8);
                textArea.setLocation(4, 4);

            }
        });
        
        logFrame.setContentPane(panel);
        textArea.setEditable(false);
        panel.add(textArea);
        logFrame.setSize(500, 500);
        logFrame.setLocationRelativeTo(null);
        logFrame.pack();
        logFrame.setVisible(true);

    }

    public static void displayDebugFrame(){

        JFrame logFrame = new JFrame(title + " - Debug");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Debug");

        logFrame.setContentPane(panel);
        panel.add(label);
        logFrame.setLocationRelativeTo(null);
        logFrame.pack();
        logFrame.setVisible(true);

    }

    /* Key bindings
    & : Show up config frame
    é : Show up stats frame
    " : Show up log frame
    \ : Show up debug frame
    g : Generate creatures
    h : Run Simulation
    */
}
