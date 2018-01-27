package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;

import java.awt.*;
import java.util.Random;

class Muscle {
    
    Muscle(int relativeX1, int relativeY1) {

        Random random = new Random();
        double ratio = random.nextDouble() * Evolutioner.BASE_TIME;

        this.relativeX1 = relativeX1;
        this.relativeY1 = relativeY1;

        int bound = Evolutioner.frame.getWidth() + 1;

        if (bound < 0) bound = 1;

        this.relativeX2 = random.nextInt(bound);

        bound = Evolutioner.frame.getHeight() - 60 + 1;

        if (bound < 0) bound = 1;

        this.relativeY2 = random.nextInt(bound);
        this.extendedLength = random.nextDouble() * 50;
        this.contractedLength = random.nextDouble() * extendedLength;
        this.extendedTime = ratio;
        this.contractedTime = 1 - ratio;
        this.strength = random.nextDouble();

        initColor();
        
    }

    Muscle(int relativeX1, int relativeY1, int relativeX2, int relativeY2) {

        Random random = new Random();
        double ratio = random.nextDouble() * Evolutioner.BASE_TIME;

        this.relativeX1 = relativeX1;
        this.relativeY1 = relativeY1;
        this.relativeX2 = relativeX2;
        this.relativeY2 = relativeY2;

        this.extendedLength = random.nextDouble() * 50;
        this.contractedLength = random.nextDouble() * extendedLength;
        this.extendedTime = ratio;
        this.contractedTime = 1 - ratio;
        this.strength = random.nextDouble();

        initColor();

    }

    Muscle(int relativeX1, int relativeY1, int relativeX2, int relativeY2, double extendedLength, double contractedLength, double extendedTime, double contractedTime, double strength) {

        this.relativeX1 = relativeX1;
        this.relativeY1 = relativeY1;
        this.relativeX2 = relativeX2;
        this.relativeY2 = relativeY2;
        this.extendedLength = extendedLength;
        this.contractedLength = contractedLength;
        this.extendedTime = extendedTime;
        this.contractedTime = contractedTime;
        this.strength = strength;

        initColor();

    }

    int relativeX1;
    int relativeY1;
    int relativeX2;
    int relativeY2;
    private double extendedLength;
    private double contractedLength;
    private double extendedTime;
    private double contractedTime;
    private double strength;
    private Color color;

    public void draw(Graphics graphics){

        /*int slope = (relativeY2 - relativeY1) / (relativeX2 - relativeY1);
        int y0 = relativeY1 - relativeX1 * slope;

        int ;

        int relativeX1L = 0;
        int relativeX1R = 0;
        int relativeX2L = 0;
        int relativeX2R = 0;
        int relativeY1L = 0;
        int relativeY1R = 0;
        int relativeY2L = 0;
        int relativeY2R = 0;

        graphics.fillPolygon(new int[]{relativeX1L, relativeX2L, relativeX2R, relativeX1R}, new int[]{relativeX1L, relativeX2L, relativeX2R, relativeX1R}, 4);*/

        //Simple Line
        graphics.setColor(color);

        graphics.drawLine(relativeX1 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY1 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight - 1, relativeX2 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY2 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight - 1);
        graphics.drawLine(relativeX1 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY1 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight, relativeX2 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY2 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight);
        graphics.drawLine(relativeX1 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY1 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight + 1, relativeX2 * Evolutioner.frame.getWidth() / Farm.baseWidth, relativeY2 * (Evolutioner.frame.getHeight() - 60) / Farm.baseHeight + 1);
    }

    private void initColor(){

        color = new Color((int) (strength * (255 - 100 + 1) + 100), 0, 0);

    }
}
