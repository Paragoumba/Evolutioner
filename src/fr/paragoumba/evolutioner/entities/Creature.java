package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;

import java.awt.*;

public class Creature extends Entity implements Runnable {

    public Creature(int nodeNumber){

        double alpha = 0;

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            alpha = i == 0 ? Math.random() * 2 * Math.PI : alpha + Math.PI / 3;

            nodes[i] = i == 0 ? new Node() : new Node((int) Math.round(nodes[i - 1].x + muscles[i - 1].extendedLength * Math.cos(alpha)), (int) Math.round(nodes[i - 1].y + muscles[i - 1].extendedLength * Math.sin(alpha)));
            muscles[i] = new Muscle();

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

        while (living) {

            //Invert velocity if edge is touched
            //if (maxXNode.x + Node.radius > SimulationPanel.worldWidth || maxXNode.x - Node.radius < 0) velocityX *= -1;

            //Apply velocity to each Node
            for (Node node : nodes) {
                
                node.x += node.velocityX;
                node.y += node.velocityY;
                
                //Set velocity to 0 if ground is touched (gravity)
                if (node.y + Node.radius > SimulationPanel.worldHeight) node.velocityY = 0;
                else node.velocityY = 1;
            
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

        for (int i = 0; i < muscles.length; ++i) muscles[i].draw(graphics, nodes[i].x, nodes[i].y, nodes[i < muscles.length - 1 ? i + 1 : 0].x, nodes[i < muscles.length - 1 ? i + 1 : 0].y);
        for (Node node : nodes) node.draw(graphics);

    }

    public void updateCoords(Dimension oldDimension, Dimension newDimension){

        for (Node node : nodes) node.updateCoords(oldDimension, newDimension);

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
