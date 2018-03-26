package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;

import java.awt.*;

public class Creature extends Entity implements Runnable {

    public Creature(int nodeNumber){

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            nodes[i] = i == 0 ? new Node() : new Node(muscles[i - 1].relativeX2, muscles[i - 1].relativeY2);
            muscles[i] = i == nodeNumber - 1 ? new Muscle(nodes[i].relativeX, nodes[i].relativeY, nodes[0].relativeX, nodes[0].relativeY) : new Muscle(nodes[i].relativeX, nodes[i].relativeY);

        }
    }

    public boolean living = false;
    private Node[] nodes;
    private Muscle[] muscles;

    public void live(){

        Thread creatureThread = new Thread(this);
        living = true;

        creatureThread.start();

    }

    @Override
    public void run() {

        //boolean extending = true;
        long targetTime = 3; //3ms = 333fps; 200ms = 5fps
        int velocityX = 1;
        int velocityY = 1;

        while (living) {

            //Run code
            Node maxXNode = new Node(SimulationPanel.worldWidth / 2, 0);
            Node maxYNode = new Node(SimulationPanel.worldHeight / 2, 0);

            //Check for Nodes with the highest X and Y
            for (Node node : nodes){

                if ((velocityX > 0 && node.relativeX > maxXNode.relativeX) || (velocityX < 0 && node.relativeX < maxXNode.relativeX)) maxXNode = node;
                if ((velocityY > 0 && node.relativeY > maxYNode.relativeY) || (velocityY < 0 && node.relativeY < maxYNode.relativeY)) maxYNode = node;

                //System.out.println(node.relativeY);
            }

            //Invert velocity if edge is touched
            if (maxXNode.relativeX + Node.radius > SimulationPanel.worldWidth || maxXNode.relativeX - Node.radius < 0) velocityX *= -1;

            //Set velocity to 0 if ground is touched (gravity)
            if (maxYNode.relativeY + Node.radius > SimulationPanel.worldHeight) velocityY = 0;

            //if (Evolutioner.debug) System.out.println("vx:" + velocityX + " (" + (maxXNode.relativeX >= SimulationPanel.worldWidth) + " || " + (maxXNode.relativeX <= 0) + ")(" + maxXNode.relativeX + " >= " +  SimulationPanel.worldWidth + " || " + maxXNode.relativeX + " <= " + 0 + ")");
            //if (Evolutioner.debug) System.out.println("vy:" + velocityY + " (" + (maxYNode.relativeY >= SimulationPanel.worldHeight) + " || " + (maxYNode.relativeY <= 0) + ")(" + maxYNode.relativeY + " >= " +  SimulationPanel.worldHeight + " || " + maxYNode.relativeY + " <= " + 0 + ")");

            //Apply velocity to each Node
            for (Node node : nodes) {
                
                node.relativeX += velocityX;
                node.relativeY += velocityY;
            
            }

            //Apply velocity to each Muscle
            for (Muscle muscle : muscles) {

                muscle.relativeX1 += velocityX;
                muscle.relativeX2 += velocityX;
                muscle.relativeY1 += velocityY;
                muscle.relativeY2 += velocityY;

            }

            try {

                Thread.sleep(targetTime);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }
        }
    }

    @Override
    public void draw(Graphics graphics){

        for (Muscle muscle : muscles) muscle.draw(graphics);
        for (Node node : nodes) node.draw(graphics);

    }

    public void updateCoords(Dimension oldDimension, Dimension newDimension){

        for (Node node : nodes) node.updateCoords(oldDimension, newDimension);
        for (Muscle muscle : muscles) muscle.updateCoords(oldDimension, newDimension);

    }

    public int getAverageX(){

        int x = 0;
        int i = 0;

        for (Node node : nodes){

            x += node.relativeX;
            ++i;

        }

        return x / i;

    }

    public int getAverageY(){

        int y = 0;
        int i = 0;

        for (Node node : nodes){

            y += node.relativeY;
            ++i;

        }

        return y / i;

    }

    public void die(){

        living = false;

    }
}
