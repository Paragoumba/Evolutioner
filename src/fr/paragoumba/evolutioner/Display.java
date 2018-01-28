package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class Display extends JPanel implements Runnable, KeyListener {

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

    @Override
    public void run() {

        DecimalFormat decimalFormat = new DecimalFormat(fps > 99 ? "###.##" : "##.##");
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

                if (Evolutioner.debug) Evolutioner.frame.setTitle(Evolutioner.title + " - " + decimalFormat.format(1d/lastFPSDisplay * 1E3 * 60) + "FPS (" + lastFPSDisplay + "ms)");

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

        Insets insets = Evolutioner.frame.getInsets();

        worldWidth = Evolutioner.frame.getWidth() - insets.left - insets.right;
        worldHeight = Evolutioner.frame.getHeight() - insets.top - insets.bottom - 60;
        dirtWidth = grassWidth = worldWidth;

        //if (Evolutioner.debug) System.out.println(Evolutioner.frame.getWidth() + "x" + Evolutioner.frame.getHeight());

        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, worldWidth, worldHeight);


        graphics.setColor(DIRT_COLOR);
        graphics.fillRect(0, worldHeight + 10, dirtWidth, dirtHeight);

        graphics.setColor(GRASS_COLOR);
        graphics.fillRect(0, worldHeight, grassWidth, grassHeight);

        Farm.drawCreatures(graphics);

        //Evolutioner.frame.pack();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        System.out.println("Input !");

        if (e.getKeyChar() == 'g'){

            Farm.generateCreatures();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
