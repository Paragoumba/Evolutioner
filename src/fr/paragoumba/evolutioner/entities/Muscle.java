package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Farm;
import fr.paragoumba.evolutioner.Utils.Vector2D;
import fr.paragoumba.evolutioner.graphic.SimulationPanel;

import java.awt.*;
import java.util.Random;

class Muscle {

    Muscle(int creatureIndex, int previousNodeIndex, int nextNodeIndex) {

        Random random = new Random();
        double ratio = random.nextDouble() * BASE_TIME;

        this.creatureIndex = creatureIndex;
        this.previousNodeIndex = previousNodeIndex;
        this.nextNodeIndex = nextNodeIndex;
        this.extendedLength = random.nextDouble() * 50;
        this.contractedLength = random.nextDouble() * extendedLength;
        this.extendedTime = ratio;
        this.contractedTime = BASE_TIME - ratio;
        this.strength = random.nextDouble();

        color = initColor();

    }

    Muscle(int creatureIndex, int previousNodeIndex, int nextNodeIndex, double extendedLength, double contractedLength, double extendedTime, double contractedTime, double strength) {

        this.creatureIndex = creatureIndex;
        this.previousNodeIndex = previousNodeIndex;
        this.nextNodeIndex = nextNodeIndex;
        this.extendedLength = extendedLength;
        this.contractedLength = contractedLength;
        this.extendedTime = extendedTime;
        this.contractedTime = contractedTime;
        this.strength = strength;

        color = initColor();

    }

    private static final int BASE_TIME = 1000;

    private final int creatureIndex;
    private final int previousNodeIndex;
    private final int nextNodeIndex;
    final double extendedLength;
    final double contractedLength;
    final double extendedTime;
    final double contractedTime;
    final double strength;
    private final Color color;
    boolean isContracting = true;

    void draw(Graphics graphics){

        graphics.setColor(color);

        int x1 = Farm.getCreature(creatureIndex).nodes[previousNodeIndex].x;
        int y1 = Farm.getCreature(creatureIndex).nodes[previousNodeIndex].y;
        int x2 = Farm.getCreature(creatureIndex).nodes[nextNodeIndex].x;
        int y2 = Farm.getCreature(creatureIndex).nodes[nextNodeIndex].y;

        if (SimulationPanel.isOnScreen(x1, y1) || SimulationPanel.isOnScreen(x2, y2)){

            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight - 1 - SimulationPanel.getCameraY(), x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight - 1 - SimulationPanel.getCameraY());
            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight - SimulationPanel.getCameraY(), x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight - SimulationPanel.getCameraY());
            graphics.drawLine(x1 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y1 * SimulationPanel.worldHeight / Farm.baseHeight + 1 - SimulationPanel.getCameraY(), x2 * SimulationPanel.worldWidth / Farm.baseWidth - SimulationPanel.getCameraX(), y2 * SimulationPanel.worldHeight / Farm.baseHeight + 1 - SimulationPanel.getCameraY());

        }
    }
    
    Node getPreviousNode(){
        
        return Farm.getCreature(creatureIndex).nodes[previousNodeIndex];
        
    }
    
    Node getNextNode(){
        
        return Farm.getCreature(creatureIndex).nodes[nextNodeIndex];
        
    }

    void toggleContraction(){

        isContracting = !isContracting;

    }

    double getOrientedAngle(){

        Vector2D vec = new Vector2D(Farm.getCreature(creatureIndex).nodes[previousNodeIndex].x - Farm.getCreature(creatureIndex).nodes[nextNodeIndex].x, Farm.getCreature(creatureIndex).nodes[previousNodeIndex].y - Farm.getCreature(creatureIndex).nodes[nextNodeIndex].y);
        return vec.getOrientedAngle();

    }

    private Color initColor(){

        int ratio = (int) (strength * (50 + 1));
        return new Color(255, ratio, ratio);

    }
}
