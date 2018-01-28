package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.Display;

import java.awt.*;

/**
 * Created by Paragoumba on 28/01/2018.
 */

public class Sign extends Entity {

    public Sign(int distance){

        x = distance;

    }

    private final int width = 50;
    private final int height = 75;
    private int x;
    private int y;
    private final Color color = Color.WHITE;

    @Override
    public void draw(Graphics graphics) {

        y = Display.worldHeight - height;

        graphics.setColor(color);
        graphics.fillRect(x, y, width, height / 3);
        graphics.fillRect(x + width / 3, y + height / 3, width / 3, height / 3 * 2);

        graphics.setColor(Color.BLACK);
        graphics.drawString(x + "px", x + width / 7, y + height / 3 / 4 + 10);

    }
}
