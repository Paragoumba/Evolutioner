package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class Evolutioner {

    public static boolean debug;
    public static int WIDTH;
    public static int HEIGHT;
    public static final int BASE_TIME = 1;
    public static final String version = "0.3b1";
    public static final String title = "Evolutioner v" + version;

    private static Thread displayThread = new Thread(new Display(), "Thread-Display");
    static JFrame frame = new JFrame("Evolutioner");
    private static JFrame configFrame = new JFrame("Evolutioner - Config");
    private static Display display = new Display();

    public static void main(String[] args) {

        for (String arg : args) {

            if (arg.equalsIgnoreCase("debug")) {

                debug = true;
                break;

            }

            debug = false;
        }

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = dimension.width;
        HEIGHT = dimension.height;

        //Initializing display's components
        frame.setContentPane(display);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.addKeyListener(new InputHandler());
        frame.setFocusable(true);
        frame.addWindowStateListener(e -> {if (e.getNewState() == WindowEvent.WINDOW_CLOSED) Display.stop();});
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle(title);
        frame.setVisible(true);
        Farm.setCreatures(3);
        Farm.generateCreatures();

        displayThread.start();

    }

    public static void displayConfigFrame(){

        JPanel panel = new JPanel();

        configFrame.setContentPane(panel);
        configFrame.setSize(500, 500);
        configFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        configFrame.pack();

        TextField fpsTextField = new TextField();
        TextField widthTextField = new TextField();
        TextField heightTextField = new TextField();

        fpsTextField.setText(Integer.toString(60));
        widthTextField.setText(Integer.toString(1920));
        heightTextField.setText(Integer.toString(1080));

        panel.add("fpsTextField", fpsTextField);
        panel.add("widthTextField", widthTextField);
        panel.add("heightTextField", heightTextField);

        configFrame.setVisible(true);

    }
}
