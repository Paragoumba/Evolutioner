package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.entities.Creature;

import java.awt.*;

public class Farm implements Runnable {

    private Farm(){}

    public static int baseWidth;
    public static int baseHeight;
    private static Creature[] creatures;
    private static long runTime = 15000;
    private static int livingCreature;

    public static void setCreaturesNumber(int creaturesNumber){

        Farm.creatures = new Creature[creaturesNumber];

    }

    public static void setRunTime(long runTime){

        Farm.runTime = runTime;

    }

    public static void generateCreatures(){

        baseWidth = Display.worldWidth;
        baseHeight = Display.worldHeight;

        for (int i = 0; i < creatures.length; ++i) creatures[i] = new Creature(3);

    }

    public static void startSimulation(){

        Thread livingThread = new Thread(new Farm(), "Thread - LifeCycle");
        livingThread.start();

    }

    public static void drawCreature(Graphics graphics){

        //for (Creature creature : creatures) creature.draw(graphics);

        creatures[livingCreature].draw(graphics);

    }

    public static Creature get1stCreature(){

        return creatures[0];

    }

    public static void killCreatures(){

        for (Creature creature : creatures) creature.die();

    }

    public static void updateCoords(Dimension oldDimension, Dimension newDimension){

        for (Creature creature : creatures) creature.updateCoords(oldDimension, newDimension);

    }

    @Override
    public void run() {

        long runTime = Farm.runTime;

        for (int i = 0; i <= creatures.length; ++i){

            livingCreature = i;
            creatures[i].live();

            try {

                Thread.sleep(runTime);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            creatures[i].die();

        }
    }
}
