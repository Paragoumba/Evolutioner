package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.Farm;

import java.awt.*;
import java.util.Random;

class Node {
    
    Node() {

        Random random = new Random();
        int bound = SimulationPanel.worldWidth + 1;

        if (bound < 0) bound = 1;

        this.x = random.nextInt(bound);
        bound = SimulationPanel.worldHeight + 1;

        if (bound < 0) bound = 1;

        this.y = random.nextInt(bound);
        this.friction = random.nextDouble();

        color = initColor();

    }

    Node(int previousNodeX, int previousNodeY, double extendedLength) {

        x = previousNodeX + (int) Math.round(extendedLength * Math.cos(Math.PI / 3));
        y = previousNodeY + (int) Math.round(extendedLength * Math.sin(Math.PI / 3));
        this.friction = Math.random();

        color = initColor();

    }

    private static final int radius = 7;
    int x;
    int y;
    int velocityX = 0;
    int velocityY = 1;
    private final double friction;
    private final Color color;

    void draw(Graphics graphics){

        if (SimulationPanel.isOnScreen(x, y)) {

            graphics.setColor(color);
            graphics.fillArc(x * SimulationPanel.worldWidth / Farm.baseWidth - radius - SimulationPanel.getCameraX(), y * SimulationPanel.worldHeight / Farm.baseHeight - radius - SimulationPanel.getCameraY(), radius * 2, radius * 2, 0, 360);

        }
    }

    void updateCoords(Dimension oldDimension, Dimension newDimension){

        x = x * newDimension.width / oldDimension.width;
        y = y * newDimension.height / oldDimension.height;

    }

    boolean isOnGround(){

        return y + radius >= SimulationPanel.worldHeight;

    }

    private Color initColor(){

        int rgb = (int) (friction * 255);
        return new Color(rgb, rgb, rgb);

    }
}
