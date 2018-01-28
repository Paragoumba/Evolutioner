package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private EntityManager(){}

    private static ArrayList<Entity> entities = new ArrayList<>();

    public static void addEntity(Entity entity){

        entities.add(entity);

    }

    public static void drawEntities(Graphics graphics){

        for (Entity entity : entities){

            entity.draw(graphics);

        }
    }
}
