package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.Display;
import fr.paragoumba.evolutioner.Farm;

import java.awt.*;
import java.util.Random;

class Node {
    
    Node() {

        Random random = new Random();
        int bound = Display.worldWidth + 1;

        if (bound < 0) bound = 1;

        this.relativeX = random.nextInt(bound);
        bound = Display.worldHeight + 1;

        if (bound < 0) bound = 1;

        this.relativeY = random.nextInt(bound);
        this.friction = random.nextDouble();

        initColor();

    }

    Node(int relativeX, int relativeY) {

        Random random = new Random();
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.friction = random.nextDouble();

        initColor();

    }
    
    Node(int relativeX, int relativeY, double friction) {
        
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.friction = friction;

        initColor();
        
    }

    public static final int radius = 7;
    int relativeX;
    int relativeY;
    private final double friction;
    private Color color;

    public void draw(Graphics graphics){

        graphics.setColor(color);
        graphics.fillArc(relativeX * Display.worldWidth / Farm.baseWidth - radius, relativeY * Display.worldHeight / Farm.baseHeight - radius, radius * 2, radius * 2, 0, 360);

    }

    public void updateCoords(Dimension oldDimension, Dimension newDimension){

        relativeX = relativeX * newDimension.width / oldDimension.width;
        relativeY = relativeY * newDimension.height / oldDimension.height;

    }

    private void initColor(){

        int rgb = (int) (friction * 255);
        color = new Color(rgb, rgb, rgb);

    }
}
