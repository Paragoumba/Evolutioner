package fr.paragoumba.evolutioner.entities;

import java.awt.*;

public class Creature extends Entity implements Runnable {

    public Creature(int index, int nodeNumber){

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];
        musclesTimes = new double[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            nodes[i] = i == 0 ? new Node(index, i) : new Node(index, i, nodes[i - 1].getX(), nodes[i - 1].getY(), muscles[i - 1].extendedLength);
            muscles[i] = i == nodeNumber - 1 ? new Muscle(index, i, 0) : new Muscle(index, i, i + 1);

        }
    }

    public boolean living = false;
    final Node[] nodes;
    final Muscle[] muscles;
    private final double[] musclesTimes;
    private final Thread creatureThread = new Thread(this);

    public void live(){

        if (!creatureThread.isAlive()) creatureThread.start();

    }

    @Override
    public void run() {

        living = true;

        while (living) {

            //Apply velocity to each Node
            for (Node node : nodes) {

                node.move(node.velocityX, node.velocityY);

                //Set velocity to 0 if ground is touched (gravity)
                if (node.isOnGround()) node.velocityY = 0;
                else node.velocityY = 1;

            }

            try {

                for (Muscle muscle : muscles){

                    System.out.println(muscle.isContracting + ":" + muscle.contractedLength + ":"+ muscle.extendedLength);

                    Node previousNode = muscle.getPreviousNode();
                    Node nextNode = muscle.getNextNode();

                    double addedLength = muscle.isContracting ? (muscle.extendedLength - muscle.contractedLength) / muscle.contractedTime : (muscle.contractedLength - muscle.extendedLength) / muscle.extendedTime;
                    long start = System.currentTimeMillis();

                    System.out.println("added" + addedLength);

                    while (System.currentTimeMillis() - start < (muscle.isContracting ? muscle.contractedTime : muscle.extendedTime)){

                        double angle = muscle.getOrientedAngle();

                        System.out.println("alpha" + angle);

                        double cos = addedLength * Math.cos(angle);
                        double sin = addedLength * Math.sin(angle);

                        if (previousNode.getX() < nextNode.getX()) {

                            previousNode.move(cos, 0);
                            nextNode.move(-cos, 0);

                        } else {

                            previousNode.move(-cos, 0);
                            nextNode.move(cos, 0);

                        }

                        if (previousNode.getY() < nextNode.getY()) {

                            previousNode.move(0, sin);
                            nextNode.move(0, -sin);

                        } else {

                            previousNode.move(0, -sin);
                            nextNode.move(0, sin);

                        }

                    }

                    muscle.toggleContraction();

                }

                Thread.sleep(1);

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

    }

    public int getAverageX(){

        int x = 0;
        int i = 0;

        for (Node node : nodes){

            x += node.getX();
            ++i;

        }

        return x / i;

    }

    public int getAverageY(){

        int y = 0;
        int i = 0;

        for (Node node : nodes){

            y += node.getY();
            ++i;

        }

        return y / i;

    }

    public void die(){

        living = false;

    }
}
