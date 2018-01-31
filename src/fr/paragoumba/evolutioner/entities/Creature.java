package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Display;

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

    private boolean living = true;
    private Node[] nodes;
    private Muscle[] muscles;

    @Override
    public void run() {

        boolean extending = true;
        long targetTime = 3;
        int velocityX = 1;
        int velocityY = 1;

        while (living) {

            //Run code
            Node maxXNode = new Node(Display.worldWidth / 2, 0);
            Node minXNode = new Node(Display.worldWidth / 2, 0);
            Node maxYNode = new Node(Display.worldHeight / 2, 0);
            Node minYNode = new Node(Display.worldHeight / 2, 0);

            for (Node node : nodes){

                if (velocityX > 0) {

                    if (node.relativeX > maxXNode.relativeX){

                        maxXNode = node;

                    } else if (node.relativeX < minXNode.relativeX){

                        minXNode = node;

                    }

                } else if (velocityX < 0){

                    if (node.relativeX < maxXNode.relativeX){

                        maxXNode = node;

                    } else if (node.relativeX > minXNode.relativeX){

                        minXNode = node;

                    }
                }

                if (velocityY > 0) {

                    if (node.relativeY > maxYNode.relativeY){

                        maxYNode = node;

                    } else if (node.relativeY < minYNode.relativeY){

                        minYNode = node;

                    }

                } else if (velocityY < 0){

                    if (node.relativeY < maxYNode.relativeY){

                        maxYNode = node;

                    } else if (node.relativeY > minYNode.relativeY){

                        minYNode = node;

                    }
                }
            }

            if (maxXNode.relativeX >= Display.worldWidth || maxXNode.relativeX <= 0) velocityX *= -1;
            if (maxYNode.relativeY >= Display.worldHeight || maxYNode.relativeY <= 0) velocityY *= -1;

            for (Node node : nodes) {
                
                node.relativeX += velocityX;
                node.relativeY += velocityY;
            
            }
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

        for (Muscle muscle : muscles){

            muscle.draw(graphics);

        }

        for (Node node : nodes){

            node.draw(graphics);

        }
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
