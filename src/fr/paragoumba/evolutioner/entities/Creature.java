package fr.paragoumba.evolutioner.entities;

import java.awt.*;

public class Creature extends Entity implements Runnable {

    public Creature(int index, int nodeNumber){

        nodes = new Node[nodeNumber];
        muscles = new Muscle[nodeNumber];

        for (int i = 0; i < nodeNumber; ++i){

            nodes[i] = i == 0 ? new Node() : new Node(index, i - 1, muscles[i - 1].extendedLength);
            muscles[i] = i == nodeNumber - 1 ? new Muscle(index, i, 0) : new Muscle(index, i, i + 1);

        }
    }

    public boolean living = false;
    final Node[] nodes;
    final Muscle[] muscles;
    private final Thread creatureThread = new Thread(this);

    public void live(){

        if (!creatureThread.isAlive()) creatureThread.start();

    }

    @Override
    public void run() {

        living = true;
        long targetTime = 17; //3ms = 333fps; 200ms = 5fps; 17ms = ~60fps

        while (living) {

            //Apply velocity to each Node
            for (Node node : nodes) {

                node.x += node.velocityX;
                node.y += node.velocityY;

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

                        double cos = addedLength * Math.cos(muscle.getOrientedAngle());
                        double sin = addedLength * Math.sin(muscle.getOrientedAngle());

                        if (previousNode.x < nextNode.x){

                            previousNode.x += cos;
                            nextNode.x -= cos;

                        } else {

                            previousNode.x -= cos;
                            nextNode.x += cos;

                        }

                        if (previousNode.y < nextNode.y){

                            previousNode.y += sin;
                            nextNode.y -= sin;

                        } else {

                            previousNode.y -= sin;
                            nextNode.y += sin;

                        }

                        Thread.sleep(1);

                    }

                    muscle.toggleContraction();

                }

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
