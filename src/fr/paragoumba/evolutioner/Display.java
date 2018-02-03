package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Random;

public class Display extends JPanel implements Runnable {

    public static int worldWidth;
    public static int worldHeight;
    public static int dirtWidth;
    public static int dirtHeight = 50;
    public static int grassWidth;
    public static int grassHeight = 10;

    public static Color BACKGROUND_COLOR = Color.CYAN;
    private static Color DIRT_COLOR = new Color(124, 44, 4);
    private static Color GRASS_COLOR = Color.GREEN;

    public static int fps = 60;
    private static double lastFPSDisplay = 0;
    private static boolean running = true;
    private static int i = 0;

    @Override
    public void run() {

        double targetTime = (1e3 / fps);
        int i = 0;

        while (running) {

            long start = System.currentTimeMillis();

            //Run code
            repaint();
            //

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

                i = 0;

                //Correct FPS
                //lastFPSDisplay -= 20;

                if (Evolutioner.debug) Evolutioner.frame.setTitle(Evolutioner.title + " - " + Math.round(1d/lastFPSDisplay * 1E3 * 60) + "FPS (" + lastFPSDisplay + "ms)");

                lastFPSDisplay = 0;

            }
        }
    }

    public static void stop(){

        running = false;
        Farm.killCreatures();

    }

    @Override
    public void paintComponent(Graphics graphics) {

        setWorldSize();

        //if (Evolutioner.debug) System.out.println(Evolutioner.frame.getWidth() + "x" + Evolutioner.frame.getHeight());

        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, worldWidth, worldHeight);

        /* #Teleboubizes */
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(75, 75, 100, 100);

        graphics.setColor(Color.BLACK);
        graphics.drawArc(75 + 20, 75 + 20, 60, 60, -20, -140);
        graphics.drawArc(75 + 20, 75 + 20 + 1, 60, 60, -20, -140);
        graphics.drawArc(75 + 20, 75 + 20 + 2, 60, 60, -20, -140);

        int eyesYDistance = 30 * worldHeight / 811;
        int nodesYDistance = Display.worldHeight;
        int averageYNode = Farm.get1stCreature().getAverageY();
        int yEyes = (75 + 50 - 20 - 10 + averageYNode * eyesYDistance / nodesYDistance);

        int eyesXDistance = 30 * worldWidth / 1600;
        int nodesXDistance = Display.worldWidth;
        int averageXNode = Farm.get1stCreature().getAverageX();
        int xEyes = (75 + 50 + averageXNode * eyesXDistance / nodesXDistance);

        // Makes the sun blinks his eyes
        int eyesHeight = 5;
        if (i >= 620) eyesHeight = 2;
        if (i >= 640){

            i = 0;

        }

        ++i;
        //

        graphics.fillOval(xEyes - 10, yEyes, 5, eyesHeight);
        graphics.fillOval(xEyes + 10, yEyes, 5, eyesHeight);
        /* Ca nous a fait trop golri */

        graphics.setColor(DIRT_COLOR);
        graphics.fillRect(0, worldHeight + 10, dirtWidth, dirtHeight);

        graphics.setColor(GRASS_COLOR);
        graphics.fillRect(0, worldHeight, grassWidth, grassHeight);

        Farm.drawCreatures(graphics);
        EntityManager.drawEntities(graphics);

        if (Evolutioner.debug){

            // Displays location of mouse around it
            Point point = MouseInfo.getPointerInfo().getLocation();
            Point frame = Evolutioner.frame.getLocation();
            Insets insets = Evolutioner.frame.getInsets();

            point.translate(frame.x - insets.left, frame.y);

            String x = point.x + "x";
            String y = point.y + "y";

            graphics.setColor(Color.GRAY);
            graphics.drawString(x, point.x, point.y - 35);
            graphics.drawString(y, point.x - y.length() * 10, point.y - 20);

        }

        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0, worldWidth, worldHeight);
    }

    public static void setWorldSize(){

        Insets insets = Evolutioner.frame.getInsets();

        worldWidth = Evolutioner.frame.getWidth() - insets.left - insets.right;
        worldHeight = Evolutioner.frame.getHeight() - dirtHeight - grassHeight - insets.top - insets.bottom;
        dirtWidth = grassWidth = worldWidth;

        //System.out.println(Evolutioner.frame.getHeight() + " - " + dirtHeight + " - " + grassHeight + " - " + insets.top + " - " + insets.bottom + " = " + worldHeight);

    }
}
