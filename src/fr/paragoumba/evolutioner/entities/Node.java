package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.Farm;
import fr.paragoumba.evolutioner.utils.Vector2D;

import java.awt.*;
import java.util.Random;

class Node {
    
    Node(int creatureIndex, int previousNodeIndex) {

        this.creatureIndex = creatureIndex;
        this.index = previousNodeIndex;

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

    Node(int creatureIndex, int index, double previousNodeX, double previousNodeY, double extendedLength) {

        this.creatureIndex = creatureIndex;
        this.index = index;
        x = previousNodeX + (int) Math.round(extendedLength * Math.cos(Math.PI / 3));
        y = previousNodeY + (int) Math.round(extendedLength * Math.sin(Math.PI / 3));
        this.friction = Math.random();
        color = initColor();

    }

    private static final int radius = 7;
    private final int index;
    private final int creatureIndex;
    private double x;
    private double y;
    int velocityX = 0;
    int velocityY = 1;
    private final double friction;
    private final Color color;

    void draw(Graphics graphics){

        if (SimulationPanel.isOnScreen(x, y)) {

            graphics.setColor(color);
            graphics.fillArc((int) Math.round(x - SimulationPanel.getCameraX()), (int) Math.round(y - radius - SimulationPanel.getCameraY()), radius * 2, radius * 2, 0, 360);

        }
    }

    void move(double x, double y){

        Muscle muscle = Farm.getCreature(creatureIndex).muscles[index];
        Vector2D newMuscle = new Vector2D(muscle.getPreviousNode().x + x, Farm.getCreature(creatureIndex).muscles[index].getPreviousNode().y + y);
        double newMuscleLength = newMuscle.getLength();

        if (newMuscleLength <= muscle.getExtendedLength() || newMuscleLength >= muscle.getContractedLength()){

            this.x += x;
            this.y += y;

        }
    }

    public double getX() {

        return x;

    }

    public double getY() {

        return y;

    }

    void updateCoords(Dimension oldDimension, Dimension newDimension){

        x *= newDimension.width / oldDimension.width;
        y *= newDimension.height / oldDimension.height;

    }

    boolean isOnGround(){

        return y + radius >= SimulationPanel.worldHeight;

    }

    private Color initColor(){

        int rgb = (int) (friction * 255);
        return new Color(rgb, rgb, rgb);

    }
}
