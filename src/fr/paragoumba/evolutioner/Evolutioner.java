package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.entities.Sign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

import static fr.paragoumba.evolutioner.Display.fps;

public class Evolutioner {

    public static boolean debug = false;
    public static final int BASE_TIME = 1;
    public static final String version = "0.5b1";
    public static final String title = "Evolutioner";

    public static JFrame frame = new JFrame(title + " - " + version);
    private static JFrame configFrame = new JFrame(title + " - Config");
    private static Display display = new Display();
    private static Thread displayThread = new Thread(display, "Thread-Display");

    public static void main(String[] args) {

        for (String arg : args) {

            if (arg.equalsIgnoreCase("debug")) {

                debug = true;
                break;

            }
        }

        //Initializing display's components
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setContentPane(display);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(dimension);
        frame.setSize(dimension);
        frame.addKeyListener(new InputHandler());
        frame.setFocusable(true);
        frame.addWindowStateListener(e -> {if (e.getNewState() == WindowEvent.WINDOW_CLOSED) Display.stop();});
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);

        Display.setWorldSize();
        Farm.setCreatures(1);
        Farm.generateCreatures();
        EntityManager.addEntity(new Sign(0));
        EntityManager.addEntity(new Sign(250));
        EntityManager.addEntity(new Sign(500));
        EntityManager.addEntity(new Sign(750));
        EntityManager.addEntity(new Sign(1000));
        EntityManager.addEntity(new Sign(1250));
        EntityManager.addEntity(new Sign(1500));
        EntityManager.addEntity(new Sign(1750));

        frame.pack();
        frame.setVisible(true);

        displayThread.start();

    }

    public static void displayConfigFrame(){

        JPanel panel = new JPanel();

        configFrame.setContentPane(panel);
        configFrame.setPreferredSize(new Dimension(300, 50));
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

        fpsTextField.setPreferredSize(new Dimension(100, fpsTextField.getHeight()));
        widthTextField.setSize(100, widthTextField.getHeight());
        heightTextField.setSize(100, heightTextField.getHeight());

        button.addActionListener(e -> {

            DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode();
            int width = displayMode.getWidth();
            int height = displayMode.getHeight();

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

                if (!debug) {

                    width = width > screenWidth ? screenWidth : width;

                }
            }

            if (!heightTextField.getText().equals("")){

                height = Integer.parseInt(heightTextField.getText());
                int screenHeight = displayMode.getHeight();

                if (!debug) {

                    height = height > screenHeight ? screenHeight : height;

                }
            }

            frame.setSize(width, height);
        });

        panel.add(/*"fpsTextField", */fpsTextField);
        panel.add(/*"widthTextField", */widthTextField);
        panel.add(/*"heightTextField", */heightTextField);
        panel.add(/*"button", */button);

        configFrame.pack();
        configFrame.setVisible(true);

    }
}
