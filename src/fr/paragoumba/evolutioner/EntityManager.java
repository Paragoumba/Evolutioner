package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.entities.Entity;
import fr.paragoumba.evolutioner.entities.Sign;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private EntityManager(){}

    private static ArrayList<Entity> entities = new ArrayList<>();

    public static void addEntity(Entity entity){

        entities.add(entity);

    }

    public static void drawEntities(Graphics graphics){

        for (Entity entity : entities) entity.draw(graphics);

    }

    public static void updateCoords(Dimension oldDimension, Dimension newDimension){

        for (Entity entity : entities) entity.updateCoords(oldDimension, newDimension);

    }

    public static void setSigns(){

        for (int i = 0; i <= SimulationPanel.worldWidth / 250; ++i) entities.add(new Sign(250 * i));

    }
}
