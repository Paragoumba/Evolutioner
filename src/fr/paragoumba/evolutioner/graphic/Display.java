package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.EntityManager;
import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;
import fr.paragoumba.evolutioner.entities.Creature;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements Runnable {

    public static int worldWidth;
    public static int worldHeight;
    public static int fps = 60;

    private static int dirtHeight = 50;
    private static int grassHeight = 10;
    private static Color BACKGROUND_COLOR = Color.CYAN;
    private static Color DIRT_COLOR = new Color(124, 44, 4);
    private static Color GRASS_COLOR = Color.GREEN;
    private static double lastFPSDisplay = 0;
    private static boolean running = true;
    private static int i = 0;

    @Override
    public void run() {

        int i = 0;

        while (running) {

            double targetTime = (1e3 / fps);
            long start = System.currentTimeMillis();

            // Drawing
            repaint();

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

                Evolutioner.frame.setTitle(Evolutioner.title + " - " + Math.round(1d/lastFPSDisplay * 1E3 * 60) + "FPS (" + lastFPSDisplay + "ms)");

                i = 0;
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

        //if (Evolutioner.debug) System.out.println(Evolutioner.frame.getWidth() + "x" + Evolutioner.frame.getHeight());

        // Drawing background
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
        Creature firstCreature = Farm.get1stCreature();
        int averageYNode = firstCreature != null ? firstCreature.getAverageY() : 0;
        int yEyes = (75 + 50 - 20 - 10 + averageYNode * eyesYDistance / nodesYDistance);

        int eyesXDistance = 30 * worldWidth / 1600;
        int nodesXDistance = Display.worldWidth;
        int averageXNode = firstCreature != null ? firstCreature.getAverageX() : 0;
        int xEyes = (75 + 50 + averageXNode * eyesXDistance / nodesXDistance);

        // Makes the sun blinks his eyes
        int eyesHeight = 5;

        if (i >= 620){

            eyesHeight = 2;
            yEyes -= 5 - 2;

        }
        if (i >= 640){

            i = 0;

        }

        ++i;
        //

        graphics.fillOval(xEyes - 10, yEyes, 5, eyesHeight);
        graphics.fillOval(xEyes + 10, yEyes, 5, eyesHeight);
        /* Ca nous a fait trop golri */

        // Drawing dirt
        graphics.setColor(DIRT_COLOR);
        graphics.fillRect(0, worldHeight + 10, worldWidth, dirtHeight);

        // Drawing grass.
        graphics.setColor(GRASS_COLOR);
        graphics.fillRect(0, worldHeight, worldWidth, grassHeight);

        // Drawing Entities
        EntityManager.drawEntities(graphics);
        Farm.drawCreature(graphics);

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
    }

    public static void setWorldSize(){

        Insets insets = Evolutioner.frame.getInsets();

        worldWidth = Evolutioner.frame.getWidth() - insets.left - insets.right;
        worldHeight = Evolutioner.frame.getHeight() - dirtHeight - grassHeight - insets.top - insets.bottom;

        //System.out.println(Evolutioner.frame.getHeight() + " - " + dirtHeight + " - " + grassHeight + " - " + insets.top + " - " + insets.bottom + " = " + worldHeight);

    }
}
