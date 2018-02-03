package fr.paragoumba.evolutioner.entities;

import java.awt.*;

public abstract class Entity {

    public abstract void draw(Graphics graphics);

    public abstract void updateCoords(Dimension oldDimension, Dimension newDimension);

}
