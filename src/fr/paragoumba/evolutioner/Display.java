package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class Display extends JPanel implements Runnable, KeyListener {

    public static Color BACKGROUND_COLOR = Color.CYAN;
    private static Color DIRT_COLOR = new Color(124, 44, 4);
    private static Color GRASS_COLOR = Color.GREEN;

    public static int fps = 60;
    private static String actualFPS = "";
    //private static double actualLoopTime = 0;
    private static boolean running = true;

    @Override
    public void run() {

        DecimalFormat decimalFormat = new DecimalFormat(fps > 99 ? "###.##" : "##.##");
        double targetTime = (1e3 / fps);

        while (running) {

            long start = System.currentTimeMillis();

            //Run code
            //requestFocus();
            repaint();
            //Evolutioner.frame.pack();

            if (Evolutioner.debug) Evolutioner.frame.setTitle(Evolutioner.title + " - " + actualFPS + "FPS");
            //

            long elapsed = System.currentTimeMillis() - start;
            long wait = Math.round(targetTime - elapsed);

            if (wait < 0) wait = (long) targetTime;

            try {

                Thread.sleep(wait);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }



            actualFPS =  decimalFormat.format(1d / (System.currentTimeMillis() - start) * 1E3);
            //actualLoopTime = wait;
        }
    }

    public static void stop(){

        running = false;
        Farm.killCreatures();

    }

    @Override
    public void paintComponent(Graphics graphics) {

        //System.out.println(Evolutioner.frame.getWidth() + "x" + Evolutioner.frame.getHeight());

        super.paintComponent(graphics);

        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, Evolutioner.frame.getWidth(), Evolutioner.frame.getHeight() - 60);

        graphics.setColor(GRASS_COLOR);
        graphics.fillRect(0, Evolutioner.frame.getHeight() - 10 - 50, Evolutioner.frame.getWidth(), 10);

        graphics.setColor(DIRT_COLOR);
        graphics.fillRect(0, Evolutioner.frame.getHeight() - 50, Evolutioner.frame.getWidth(), 50);

        Farm.drawCreatures(graphics);

        Evolutioner.frame.pack();
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
