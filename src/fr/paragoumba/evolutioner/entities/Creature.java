package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;

import java.awt.*;

public class Creature extends Entity implements Runnable {

    public Creature(int nodeNumber){

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            nodes[i] = i == 0 ? new Node() : new Node(muscles[i - 1].x2, muscles[i - 1].y2);
            muscles[i] = i == nodeNumber - 1 ? new Muscle(nodes[i].x, nodes[i].y, nodes[0].x, nodes[0].y) : new Muscle(nodes[i].x, nodes[i].y);

        }
    }

    public boolean living = false;
    private final Node[] nodes;
    private final Muscle[] muscles;
    private final Thread creatureThread = new Thread(this);

    public void live(){

        System.out.println(creatureThread.isAlive());

        if (!creatureThread.isAlive()){
            creatureThread.start();
        }
    }

    @Override
    public void run() {

        living = true;

        //boolean extending = true;
        long targetTime = 17; //3ms = 333fps; 200ms = 5fps
        int velocityX = 1;
        int velocityY = 1;

        while (living) {

            //Run code
            //Node maxXNode = new Node(SimulationPanel.worldWidth / 2, 0);
            Node maxYNode = new Node(SimulationPanel.worldHeight / 2, 0);

            //Check for Nodes with the highest X and Y
            for (Node node : nodes){

                //if ((velocityX > 0 && node.x > maxXNode.x) || (velocityX < 0 && node.x < maxXNode.x)) maxXNode = node;
                if (velocityY > 0 && node.y > maxYNode.y) maxYNode = node;

                //System.out.println(node.y);
            }

            //Invert velocity if edge is touched
            //if (maxXNode.x + Node.radius > SimulationPanel.worldWidth || maxXNode.x - Node.radius < 0) velocityX *= -1;

            //Set velocity to 0 if ground is touched (gravity)
            if (maxYNode.y + Node.radius > SimulationPanel.worldHeight) velocityY = 0;

            //Apply velocity to each Node
            for (Node node : nodes) {
                
                node.x += velocityX;
                node.y += velocityY;
            
            }

            //Apply velocity to each Muscle
            for (Muscle muscle : muscles) {

                muscle.x1 += velocityX;
                muscle.x2 += velocityX;
                muscle.y1 += velocityY;
                muscle.y2 += velocityY;

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

            x += node.x;
            ++i;

        }

        return x / i;

    }

    public int getAverageY(){

        int y = 0;
        int i = 0;

        for (Node node : nodes){

            y += node.y;
            ++i;

        }

        return y / i;

    }

    public void die(){

        living = false;

    }
}
