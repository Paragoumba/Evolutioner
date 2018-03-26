package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.graphic.StartingPanel;
import fr.paragoumba.evolutioner.graphic.TutorialPanel;
import org.yaml.snakeyaml.Yaml;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class Evolutioner implements Runnable {

    public static boolean debug = false;
    public static final int BASE_TIME = 1;
    private static final String version = "0.5b1";
    private static final String title = "Evolutioner";
    public static JFrame frame = new JFrame(title + " - " + version);
    public static JLabel livingCreatures = new JLabel("None.");

    private static Thread displayThread = new Thread(new Evolutioner(), "Thread-Graphics");
    private static Dimension oldDimension;
    private static HashMap<Object, Object> config;
    private static File configFile;
    private static int fps = 60;

    private static double lastFPSDisplay = 0;
    private static boolean running = true;
    private static JPanel[] panels = new JPanel[0];
    private static int displayedPanel;

    private static final int STARTING_PANEL = registerPanel(new StartingPanel());
    private static final int TUTORIAL_PANEL = registerPanel(new TutorialPanel());
    private static final int SIMULATION_PANEL = registerPanel(new SimulationPanel());

    public static void main(String[] args) throws InterruptedException, IOException {

        for (String arg : args) {

            if (arg.equalsIgnoreCase("debug")) {

                debug = true;
                break;

            }
        }

        loadConfig();

        //Initializing display's components
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowStateListener(e -> {

            if (e.getNewState() == WindowEvent.WINDOW_CLOSED) stop();

        });
        frame.setPreferredSize(dimension);
        frame.setSize(dimension);
        frame.setIconImage(ImageIO.read(Evolutioner.class.getResourceAsStream("res/icon32x32.png")));
        frame.setLocationRelativeTo(null);
        frame.setTitle(title);

        /* StartingPanel */
        frame.setContentPane(panels[STARTING_PANEL]);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        /* END */

        Thread.sleep(2000);

        if ((boolean) config.get("firstVisit")) {

            PrintWriter out = new PrintWriter(configFile);
            Yaml yaml = new Yaml();

            config.put("firstVisit", false);
            yaml.dump(config, out);
            out.close();

            /* TutorialPanel */
            frame.setContentPane(panels[TUTORIAL_PANEL]);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            /* END */

            Thread.sleep(10000);

        }

        /* SimulationPanel */
        oldDimension = frame.getSize();
        InputHandler inputHandler = new InputHandler();

        frame.addKeyListener(inputHandler);
        frame.setFocusable(true);
        panels[displayedPanel].addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                SimulationPanel.setWorldSize();

                Dimension newDimension = e.getComponent().getSize();

                EntityManager.updateCoords(oldDimension, newDimension);
                Farm.updateCoords(oldDimension, newDimension);

                oldDimension = newDimension;

            }
        });

        SimulationPanel.setWorldSize();
        EntityManager.setSigns();
        Farm.generateCreatures();
        Farm.startSimulation();

        frame.setContentPane(panels[SIMULATION_PANEL]);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        /* END*/

        displayThread.start();

    }

    @Override
    public void run() {

        int i = 0;

        while (running) {

            double targetTime = (1e3 / fps);
            long start = System.currentTimeMillis();

            // Drawing
            panels[displayedPanel].repaint();

            long elapsed = System.currentTimeMillis() - start;
            long wait = Math.round(targetTime - elapsed);

            if (wait < 0) wait = (long) targetTime;

            try {

                Thread.sleep(wait);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            ++i;

            lastFPSDisplay += System.currentTimeMillis() - start;

            if (i > 59) {

                Evolutioner.frame.setTitle(Evolutioner.title + " - " + Math.round(1d/lastFPSDisplay * 1E3 * 60) + "FPS" + (debug ? " (" + lastFPSDisplay + "ms)" : ""));

                i = 0;
                lastFPSDisplay = 0;

            }
        }
    }

    private static int registerPanel(JPanel panel){

        int i = 0;

        for (; i < panels.length; ++i){

            if (panels[i].equals(panel)) return i;

        }

        for (JPanel panel1 : panels) System.out.println(panel1);

        panels = Arrays.copyOf(panels, panels.length + 1);
        panels[panels.length - 1] = panel;

        return panels.length - 1;

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

                Evolutioner.fps = fps;

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

    private static void loadConfig() throws IOException {

        File paraCorpDir = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath(), "ParaCorp");
        File configDir = new File(paraCorpDir, "Evolutioner");
        configFile = new File(configDir, "config.yml");

        Yaml yaml = new Yaml();

        if (!paraCorpDir.exists()) paraCorpDir.mkdir();
        if (!configDir.exists()) configDir.mkdir();

        if (!configFile.exists()){

            System.out.println(configFile.getAbsolutePath());

            configFile.createNewFile();

            PrintWriter out = new PrintWriter(configFile);
            yaml.dump(yaml.load(Evolutioner.class.getResourceAsStream("config.yml")), out);
            out.close();

        }

        FileInputStream fis = new FileInputStream(configFile);
        config = (HashMap<Object, Object>) yaml.load(fis);

        fis.close();

        if (debug) System.out.println(config);

    }

    private static void stop(){

        running = false;
        Farm.killCreatures();

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
