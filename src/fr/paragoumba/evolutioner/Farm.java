package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.entities.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Farm implements Runnable {

    private Farm(){}

    public static final int NODE_NUMBER = 3;
    public static int baseWidth;
    public static int baseHeight;
    private static Creature[] creatures = new Creature[0];
    private static long runTime = 150000;
    private static int livingCreature;
    private static boolean running = false;
    private static int creatureNumber = 10;
    private static final Thread livingThread = new Thread(new Farm(), "Thread - LifeCycle");

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

        for (int i = 0; i < creatureNumber; ++i) creatures[i] = new Creature(i, NODE_NUMBER);

    }

    public static Creature getCreature(int index){

        return creatures[index];

    }

    public static void toggleSimulation(){

        if (livingThread.isAlive()) stopSimulation();
        else startSimulation();

    }

    public static void startSimulation(){

        livingThread.start();

    }

    private static void stopSimulation(){

        running = false;
        killCreatures();
        creatures = new Creature[0];

    }

    public static void drawCreature(Graphics graphics){

        StringBuilder livingCreatures = new StringBuilder();

        for (int i = 0; i < creatures.length; ++i) livingCreatures.append(i).append(":").append(creatures[i].living).append('\n');

        Evolutioner.livingCreatures.setText(livingCreatures.toString());

        if (creatures.length > 0 && creatures[livingCreature].living) creatures[livingCreature].draw(graphics);

    }

    public static Creature getLivingCreature(){

        return creatures.length > 0 ? creatures[livingCreature] : null;

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

        List<Creature> creaturesList = Arrays.asList(creatures);
        creaturesList.sort(Comparator.comparingInt(Creature::getAverageX));

        for (int i = 0; i < creaturesList.size(); i++ ){
            creaturesList.get(i).die();
            creaturesList.add(i, new Creature(i, NODE_NUMBER));

        }

        creatures = (Creature[]) creaturesList.toArray();

    }
}
