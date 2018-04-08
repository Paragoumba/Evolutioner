package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Evolutioner;
import fr.paragoumba.evolutioner.Farm;
import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.graphic.Vector2D;

import java.awt.*;
import java.util.Random;

class Muscle {
    
    Muscle(int x1, int y1, int x2, int y2) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        muscleVector = new Vector2D(x2 - x1, y2 - y1);

        Random random = new Random();
        double ratio = random.nextDouble() * Evolutioner.BASE_TIME;

        this.extendedLength = random.nextDouble() * SimulationPanel.screenHeight / 3 * 2;
        this.contractedLength = random.nextDouble() * extendedLength;
        this.extendedTime = ratio;
        this.contractedTime = 1 - ratio;
        this.strength = random.nextDouble();

        initColor();

    }

    Muscle(int x1, int y1, int x2, int y2, double extendedLength, double contractedLength, double extendedTime, double contractedTime, double strength) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        muscleVector = new Vector2D(x2 - x1, y2 - y1);

        this.extendedLength = extendedLength;
        this.contractedLength = contractedLength;
        this.extendedTime = extendedTime;
        this.contractedTime = contractedTime;
        this.strength = strength;

        initColor();

    }

    double length;

    private final double extendedLength;
    private final double contractedLength;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Vector2D muscleVector;
    private double extendedTime;
    private double contractedTime;
    private double strength;
    private Color color;

    public void draw(Graphics graphics){

        if (SimulationPanel.isOnScreen(x1, y1) || SimulationPanel.isOnScreen(x2, y2)) {

            /*//Simple Line
            graphics.setColor(color);

            int n = 4;
            int[] pointX = new int[n];
            int[] pointY = new int[n];

            double theta = muscleVector.getAngle() + Math.PI / 2;
            Vector2D vec = new Vector2D((int) Math.round(Math.cos(theta) * length), (int) Math.round(Math.sin(theta) * length));

            pointX[0] = ;
            pointY[0] = ;
            pointX[1] = ;
            pointY[1] = ;
            pointX[2] = ;
            pointY[2] = ;
            pointX[3] = ;
            pointY[3] = ;

            for (int i = 0; i < n; ++i){

                pointX[i] = vec.x;
                pointY[i] = vec.y;

            }

            graphics.fillPolygon(pointX, pointY, n);*/

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
