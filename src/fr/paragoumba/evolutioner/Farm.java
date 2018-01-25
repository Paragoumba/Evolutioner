package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.creatures.Creature;

import java.awt.*;

public class Farm implements Runnable {

    private Farm(){}

    private static Creature[] creatures;
    private static long runTime = 15000;

    public static void setCreatures(int creatures){

        Farm.creatures = new Creature[creatures];

    }

    public static void setRunTime(long runTime){

        Farm.runTime = runTime;

    }

    public static void generateCreatures(){

        for (int i = 0; i < creatures.length; ++i){

            creatures[i] = new Creature(3);

        }
    }

    public static void drawCreatures(Graphics graphics){

        for (Creature creature : creatures){

            creature.draw(graphics);

        }
    }

    @Override
    public void run() {

        long runTime = Farm.runTime;

        for (Creature creature : creatures){

            creature.run();

            try {

                Thread.sleep(runTime);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            creature.die();
        }
    }
}
