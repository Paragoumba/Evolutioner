package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Evolutioner;

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

    private boolean living = false;
    private Node[] nodes;
    private Muscle[] muscles;

    @Override
    public void run() {

        boolean extending = true;
        long targetTime = 3;
        int velocity = 1;

        while (living) {

            //Run code
            Node maxNode = new Node(Evolutioner.frame.getWidth() / 2, 0);

            for (Node node : nodes){

                if (velocity > 0 && node.relativeX > maxNode.relativeX) maxNode = node;
                else if (velocity < 0 && node.relativeX < maxNode.relativeX) maxNode = node;

            }

            if (maxNode.relativeX >= Evolutioner.frame.getWidth()) velocity *= -1;

            System.out.println(velocity);

            for (Node node : nodes) node.relativeX += velocity;
            for (Muscle muscle : muscles) {

                muscle.relativeX1 += velocity;
                muscle.relativeX2 += velocity;

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

        for (Muscle muscle : muscles){

            muscle.draw(graphics);

        }

        for (Node node : nodes){

            node.draw(graphics);

        }
    }

    public void die(){

        living = false;

    }
}
