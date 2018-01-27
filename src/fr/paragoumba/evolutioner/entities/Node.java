package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;

import java.awt.*;
import java.util.Random;

class Node {
    
    Node() {

        Random random = new Random();

        int bound = Evolutioner.frame.getWidth() + 1;

        if (bound < 0) bound = 1;

        this.relativeX = random.nextInt(bound);

        bound = Evolutioner.frame.getHeight() - 60 + 1;

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

    private final int radius = 5;
    int relativeX;
    int relativeY;
    private final double friction;
    private Color color;

    public void draw(Graphics graphics){

        graphics.setColor(color);
        graphics.fillArc(relativeX * Evolutioner.frame.getWidth() / Farm.baseWidth - radius, relativeY * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight - radius, radius * 2, radius * 2, 0, 360);

    }

    private void initColor(){

        int rgb = (int) (friction * 255);
        color = new Color(rgb, rgb, rgb);

    }
}
