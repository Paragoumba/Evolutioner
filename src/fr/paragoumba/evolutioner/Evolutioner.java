package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import static fr.paragoumba.evolutioner.Display.fps;

public class Evolutioner {

    public static boolean debug;
    public static final int BASE_TIME = 1;
    public static final String version = "0.3b1";
    public static final String title = "Evolutioner v" + version;

    private static Thread displayThread = new Thread(new Display(), "Thread-Display");
    public static JFrame frame = new JFrame("Evolutioner");
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

        //Initializing display's components
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setContentPane(display);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(dimension);
        frame.addKeyListener(new InputHandler());
        frame.setFocusable(true);
        frame.addWindowStateListener(e -> {if (e.getNewState() == WindowEvent.WINDOW_CLOSED) Display.stop();});
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        Farm.setCreatures(3);
        Farm.generateCreatures();

        displayThread.start();

    }

    public static void displayConfigFrame(){

        JPanel panel = new JPanel();

        configFrame.setContentPane(panel);
        configFrame.setPreferredSize(new Dimension(500, 500));
        configFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JTextField fpsTextField = new JTextField();
        JTextField widthTextField = new JTextField();
        JTextField heightTextField = new JTextField();
        JButton button = new JButton("Validate");

        fpsTextField.setText(Integer.toString(fps));
        widthTextField.setText(Integer.toString(frame.getWidth()));
        heightTextField.setText(Integer.toString(frame.getHeight()));
        fpsTextField.setDocument(new PlainDocument());
        widthTextField.setDocument(new PlainDocument());
        heightTextField.setDocument(new PlainDocument());
        fpsTextField.setSize(100, fpsTextField.getHeight());
        widthTextField.setSize(100, widthTextField.getHeight());
        heightTextField.setSize(100, heightTextField.getHeight());

        button.addActionListener(e -> {

            if (!fpsTextField.getText().equals("") && !widthTextField.getText().equals("") && !heightTextField.getText().equals("")) {

                int fps = Integer.parseInt(fpsTextField.getText());
                int width = Integer.parseInt(widthTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());

                DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode();
                int refreshRate = displayMode.getRefreshRate();
                int screenWidth = displayMode.getWidth();
                int screenHeight = displayMode.getHeight();

                if (!debug) {

                    fps = refreshRate != DisplayMode.REFRESH_RATE_UNKNOWN && fps > refreshRate ? refreshRate : fps;
                    width = width > screenWidth ? screenWidth : width;
                    height = height > screenHeight ? screenHeight : height;

                }

                Display.fps = fps;
                frame.setSize(width, height);
            }
        });

        panel.add(/*"fpsTextField", */fpsTextField);
        panel.add(/*"widthTextField", */widthTextField);
        panel.add(/*"heightTextField", */heightTextField);
        panel.add(/*"button", */button);

        configFrame.pack();
        configFrame.setVisible(true);

    }
}
