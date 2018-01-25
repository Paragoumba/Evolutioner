package fr.paragoumba.evolutioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class Display extends JPanel implements Runnable, KeyListener {

    private static int fps = 60;
    private static String actualFPS = "";
    private static double actualLoopTime = 0;
    private static boolean running = true;

    @Override
    public void run() {

        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        double targetTime = (1e3 / fps);

        while (running) {

            long start = System.currentTimeMillis();

            //Run code
            requestFocus();
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

            actualFPS =  decimalFormat.format(1d / (System.currentTimeMillis() - start) * 1E3);
            //actualLoopTime = wait;
        }
    }

    public static void stop(){

        running = false;

    }

    @Override
    public void paintComponent(Graphics graphics) {

        graphics.setColor(new Color(100, 255, 100));
        graphics.fillRect(0, 0, Evolutioner.WIDTH, Evolutioner.HEIGHT);

        graphics.setColor(Color.YELLOW);
        graphics.drawRect(100, 56, Evolutioner.WIDTH - 200, Evolutioner.HEIGHT - 56*2);

        if (Evolutioner.debug) Evolutioner.frame.setTitle(Evolutioner.title + " - " + actualFPS + "FPS");

        Farm.drawCreatures(graphics);

        if (Evolutioner.debug) {

            graphics.setColor(Color.BLUE);
            graphics.drawString(String.valueOf(Evolutioner.frame.getInsets().top) + ":" + String.valueOf(Evolutioner.frame.getInsets().right) + ":" + String.valueOf(Evolutioner.frame.getInsets().bottom) + ":" + String.valueOf(Evolutioner.frame.getInsets().left), Evolutioner.WIDTH / 2, Evolutioner.HEIGHT - 50);

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

        System.out.println("Input !");

        if (e.getKeyChar() == 'g'){

            System.out.println('g');

            Farm.generateCreatures();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
