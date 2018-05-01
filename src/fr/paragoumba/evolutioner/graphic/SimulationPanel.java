package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.EntityManager;
import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;
import fr.paragoumba.evolutioner.entities.Creature;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    public SimulationPanel(){

        setWorldSize();

    }

    public static int frameWidth;
    public static int frameHeight;
    public static int worldWidth;
    public static int worldHeight;

    private static int dirtHeight = 50;
    private static int grassHeight = 10;
    public static Color BACKGROUND_COLOR = Color.CYAN;
    public static Color DIRT_COLOR = new Color(124, 44, 4);
    public static Color GRASS_COLOR = Color.GREEN;
    public static Color SUN_COLOR = Color.YELLOW;
    public static Color SUN_2ND_COLOR = Color.BLACK;
    private static int i = 0;
    private static int cameraX = 0;
    private static int cameraY = 0;
    private static float cameraScale = 1;

    @Override
    public void paintComponent(Graphics graphics) {

        graphics.drawLine(-5, -5, -10, -10);

        // Drawing background
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, frameWidth, frameHeight);

        /* #Teleboubizes */
        if (Evolutioner.debug) {

            graphics.setColor(SUN_COLOR);
            graphics.fillOval(75, 75, 100, 100);

            graphics.setColor(SUN_2ND_COLOR);
            graphics.drawArc(75 + 20, 75 + 20, 60, 60, -20, -140);
            graphics.drawArc(75 + 20, 75 + 20 + 1, 60, 60, -20, -140);
            graphics.drawArc(75 + 20, 75 + 20 + 2, 60, 60, -20, -140);

            int eyesYDistance = 30 * worldHeight / 811;
            int nodesYDistance = SimulationPanel.worldHeight;
            Creature livingCreature = Farm.getLivingCreature();
            int averageYNode = livingCreature != null ? livingCreature.getAverageY() : 0;
            int yEyes = (75 + 50 - 20 - 10 + averageYNode * eyesYDistance / nodesYDistance);

            int eyesXDistance = 30 * worldWidth / 1600;
            int nodesXDistance = SimulationPanel.worldWidth;
            int averageXNode = livingCreature != null ? livingCreature.getAverageX() : 0;
            int xEyes = (75 + 50 + averageXNode * eyesXDistance / nodesXDistance);

            // Makes the sun blinks his eyes
            int eyesHeight = 5;

            if (i >= 620) {

                eyesHeight = 2;
                yEyes += 5 - eyesHeight;

            }

            if (i >= 640) {

                i = 0;

            }

            ++i;
            //

            graphics.fillOval(xEyes - 10, yEyes, 5, eyesHeight);
            graphics.fillOval(xEyes + 10, yEyes, 5, eyesHeight);

        }
        /* Ca nous a fait trop golri */

        // Drawing dirt
        graphics.setColor(DIRT_COLOR);
        graphics.fillRect(0, worldHeight + 10, worldWidth, dirtHeight);

        // Drawing grass
        graphics.setColor(GRASS_COLOR);
        graphics.fillRect(0, worldHeight, worldWidth, grassHeight);

        // Drawing Entities
        EntityManager.drawEntities(graphics);
        Farm.drawCreature(graphics);

        if (Evolutioner.debug){

            // Displays location of mouse around it
            Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
            Point frame = Evolutioner.frame.getLocation();
            Insets insets = Evolutioner.frame.getInsets();

            mouseLoc.translate(frame.x - insets.left, frame.y);

            String x = mouseLoc.x + SimulationPanel.getCameraX() + "x";
            String y = mouseLoc.y + SimulationPanel.getCameraY() + "y";

            graphics.setColor(Color.GRAY);
            graphics.drawString(x, mouseLoc.x, mouseLoc.y - 35);
            graphics.drawString(y, mouseLoc.x - y.length() * 10, mouseLoc.y - 20);

        }
    }

    public static void setWorldSize(){

        Insets insets = Evolutioner.frame.getInsets();

        frameWidth = worldWidth = Evolutioner.frame.getWidth() - insets.left - insets.right;
        frameHeight = Evolutioner.frame.getHeight() - insets.top - insets.bottom;
        worldHeight = frameHeight - dirtHeight - grassHeight;

        System.out.println("f" + Evolutioner.frame.getWidth() + ":" + Evolutioner.frame.getHeight());

    }

    public static int getCameraX() {

        return cameraX;

    }

    public static void setCameraX(int x){

        cameraX = x;
        EntityManager.setSigns();

    }

    public static int getCameraY() {

        return cameraY;

    }

    public static void setCameraY(int cameraY) {

        SimulationPanel.cameraY = cameraY < 0 ? 0 : cameraY + frameHeight * cameraScale > frameHeight ? frameHeight : cameraY;

    }

    public static float getCameraScale() {

        return cameraScale;

    }

    public static void setCameraScale(float scale){

        cameraScale = scale < 0 ? 0 : scale > 1 ? 1 : scale;

    }

    public static boolean isOnScreen(double x, double y){

        return x >= cameraX && x <= x + frameWidth * cameraScale && y >= cameraY && y <= y + frameHeight * cameraScale;

    }
}
