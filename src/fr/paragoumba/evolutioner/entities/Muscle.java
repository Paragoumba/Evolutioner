package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;

import java.awt.*;
import java.util.Random;

class Muscle {
    
    Muscle() {

        Random random = new Random();
        double ratio = random.nextDouble() * Evolutioner.BASE_TIME;

        this.extendedLength = random.nextDouble() * SimulationPanel.screenHeight / 3 * 2;
        this.contractedLength = random.nextDouble() * extendedLength;
        this.extendedTime = ratio;
        this.contractedTime = 1 - ratio;
        this.strength = random.nextDouble();

        initColor();

    }

    Muscle(double extendedLength, double contractedLength, double extendedTime, double contractedTime, double strength) {

        this.extendedLength = extendedLength;
        this.contractedLength = contractedLength;
        this.extendedTime = extendedTime;
        this.contractedTime = contractedTime;
        this.strength = strength;

        initColor();

    }

    double extendedLength;
    double contractedLength;
    private double extendedTime;
    private double contractedTime;
    private double strength;
    private Color color;

    public void draw(Graphics graphics, int x1, int y1, int x2, int y2){

        /*int slope = (y2 - y1) / (x2 - x1);
        int y0 = y1 - x1 * slope;

        int ;

        int x1L = 0;
        int x1R = 0;
        int x2L = 0;
        int x2R = 0;
        int y1L = 0;
        int y1R = 0;
        int y2L = 0;
        int y2R = 0;

        graphics.fillPolygon(new int[]{x1L, x2L, x2R, x1R}, new int[]{x1L, x2L, x2R, x1R}, 4);*/

        if (SimulationPanel.isOnScreen(x1, y1) || SimulationPanel.isOnScreen(x2, y2)) {

            //Simple Line
            graphics.setColor(color);

            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight - 1, x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight - 1);
            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight, x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight);
            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight + 1, x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight + 1);
        
        }
    }

    private void initColor(){

        int ratio = (int) (strength * (50 + 1));
        color = new Color(255, ratio, ratio);

    }
}
