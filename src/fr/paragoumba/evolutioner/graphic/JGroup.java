package fr.paragoumba.evolutioner.graphic;

import javax.swing.*;
import java.awt.*;

public class JGroup extends JComponent {

    public JGroup(int x, int y, int width, int height){

        setSize(width, height);
        setLocation(x, y);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        System.out.println("drawing");

        g.setColor(new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255)));
        g.drawOval((int) Math.round(getWidth() * Math.random()), (int) Math.round(getHeight() * Math.random()), 50, 50);

    }
}
