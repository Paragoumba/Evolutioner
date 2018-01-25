package fr.paragoumba.evolutioner.creatures;

import java.awt.*;

public class Creature implements Runnable {

    public Creature(int nodeNumber){

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            nodes[i] = i == 0 ? new Node() : new Node(muscles[i - 1].relativeX2, muscles[i - 1].relativeY2);
            muscles[i] = i == nodeNumber - 1 ? new Muscle(nodes[i].relativeX, nodes[i].relativeY, nodes[0].relativeX, nodes[0].relativeY) : new Muscle(nodes[i].relativeX, nodes[i].relativeY);

        }
    }

    private boolean living = false;
    private Node[] nodes;
    private Muscle[] muscles;

    @Override
    public void run() {

        boolean extending = true;
        long targetTime = 1;

        while (living) {

            //Run code
            //...

            try {

                Thread.sleep(targetTime);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }
        }
    }

    public void draw(Graphics graphics){

        for (Node node : nodes){

            node.draw(graphics);

        }

        for (Muscle muscle : muscles){

            muscle.draw(graphics);

        }
    }

    public void die(){

        living = false;

    }
}
