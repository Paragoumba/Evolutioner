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

        initColor();

    }

    Node(int x, int y) {

        Random random = new Random();
        this.x = x;
        this.y = y;
        this.friction = random.nextDouble();

        initColor();

    }
    
    Node(int x, int y, double friction) {
        
        this.x = x;
        this.y = y;
        this.friction = friction;

        initColor();
        
    }

    public static final int radius = 7;
    int x;
    int y;
    int velocityX = 0;
    int velocityY = 0;
    private final double friction;
    private Color color;

    public void draw(Graphics graphics){

        if (SimulationPanel.isOnScreen(x, y)) {

            graphics.setColor(color);
            graphics.fillArc(x * SimulationPanel.worldWidth / Farm.baseWidth - radius - SimulationPanel.getCameraX(), y * SimulationPanel.worldHeight / Farm.baseHeight - radius, radius * 2, radius * 2, 0, 360);

        }
    }

    public void updateCoords(Dimension oldDimension, Dimension newDimension){

        x = x * newDimension.width / oldDimension.width;
        y = y * newDimension.height / oldDimension.height;

    }

    private void initColor(){

        int rgb = (int) (friction * 255);
        color = new Color(rgb, rgb, rgb);

    }
}
