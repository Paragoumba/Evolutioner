package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.entities.Creature;

import java.awt.*;

public class Farm implements Runnable {

    private Farm(){}

    public static int baseWidth;
    public static int baseHeight;
    private static Creature[] creatures = new Creature[0];
    private static long runTime = 15000;
    private static int livingCreature;
    private static boolean running = false;
    private static int creatureNumber = 10;

    public static void setDefaultCreatureNumber(int creatureNumber){

        Farm.creatureNumber = creatureNumber;

    }

    public static int getDefaultCreatureNumber() {

        return creatureNumber;

    }

    public static void setRunTime(long runTime){

        Farm.runTime = runTime;

    }

    public static void generateCreatures(){

        baseWidth = SimulationPanel.worldWidth;
        baseHeight = SimulationPanel.worldHeight;

        stopSimulation();

        creatures = new Creature[creatureNumber];

        for (int i = 0; i < creatureNumber; ++i) creatures[i] = new Creature(3);

    }

    public static void startSimulation(){

        Thread livingThread = new Thread(new Farm(), "Thread - LifeCycle");
        livingThread.start();

    }

    private static void pauseSimulation(){

        running = false;

    }

    private static void stopSimulation(){

        pauseSimulation();
        killCreatures();
        creatures = new Creature[0];

    }

    public static void drawCreature(Graphics graphics){

        StringBuilder livingCreatures = new StringBuilder();

        for (int i = 0; i < creatures.length; ++i) livingCreatures.append(i).append(":").append(creatures[i].living).append('\n');

        Evolutioner.livingCreatures.setText(livingCreatures.toString());

        if (creatures[livingCreature].living) creatures[livingCreature].draw(graphics);

    }

    public static Creature get1stCreature(){

        return creatures.length > 0 ? creatures[0] : null;

    }

    public static void killCreatures(){

        for (Creature creature : creatures) creature.die();

    }

    public static void updateCoords(Dimension oldDimension, Dimension newDimension){

        for (Creature creature : creatures) creature.updateCoords(oldDimension, newDimension);

    }

    @Override
    public void run() {

        running = true;
        long runTime = Farm.runTime;

        for (int i = 0; running && i < creatures.length; ++i){

            creatures[livingCreature = i].live();

            try {

                Thread.sleep(runTime);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            creatures[i].die();

        }
    }
}
