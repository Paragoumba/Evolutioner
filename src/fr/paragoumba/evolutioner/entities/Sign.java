package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Converter;
import fr.paragoumba.evolutioner.graphic.Display;
import fr.paragoumba.evolutioner.Unit;

import java.awt.*;

/**
 * Created by Paragoumba on 28/01/2018.
 */

public class Sign extends Entity {

    public Sign(int distance){

        x = distance;

    }

    private int width = 50;
    private int height = 75;
    private int x;
    private int y;
    private final Color color = Color.WHITE;

    @Override
    public void draw(Graphics graphics) {

        y = Display.worldHeight - height;

        graphics.setColor(color);
        graphics.fillRect(x, y, width, height / 3);
        graphics.fillRect(x + width / 3, y + height / 3, width / 3, height / 3 * 2);

        //if (Evolutioner.debug) System.out.println(x + ";" + Converter.convert(Unit.PIXEL, Unit.METER, x));

        graphics.setColor(Color.BLACK);
        graphics.drawString(Converter.convert(Unit.PIXEL, Unit.METER, x + 1) + "m", x + width / 4 / 3, y + height / 3 / 4 + 10);

    }

    @Override
    public void updateCoords(Dimension oldDimension, Dimension newDimension) {

        x = x * newDimension.width / oldDimension.width;
        y = y * newDimension.height / oldDimension.height;
        width = width * newDimension.width / oldDimension.width;
        height = height * newDimension.height / oldDimension.height;

    }
}
