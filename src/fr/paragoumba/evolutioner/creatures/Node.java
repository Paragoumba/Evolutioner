package fr.paragoumba.evolutioner.creatures;

import fr.paragoumba.evolutioner.Evolutioner;

import java.awt.*;
import java.util.Random;

class Node {
    
    Node() {

        Random random = new Random();
        
        this.relativeX = random.nextInt(Evolutioner.WIDTH - 200 + 1) + 100;
        this.relativeY = random.nextInt(Evolutioner.HEIGHT - 112 + 1) + 56;
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
        graphics.fillArc(relativeX - radius, relativeY - radius, radius * 2, radius * 2, 0, 360);

    }

    private void initColor(){

        int rgb = (int) (friction * 255);
        color = new Color(rgb, rgb, rgb);

    }
}
