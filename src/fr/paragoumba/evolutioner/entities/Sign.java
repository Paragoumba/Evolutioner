package fr.paragoumba.evolutioner.entities;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;
import fr.paragoumba.evolutioner.Unit;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;

import static fr.paragoumba.evolutioner.Unit.METER;
import static fr.paragoumba.evolutioner.Unit.PIXEL;

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

        if (SimulationPanel.isOnScreen(x, y) || SimulationPanel.isOnScreen(x + width, y)) {

            y = SimulationPanel.worldHeight - height;

            graphics.setColor(color);
            graphics.fillRect(x - SimulationPanel.getCameraX() - width / 2, y, width, height / 3);
            graphics.fillRect(x + width / 3 - SimulationPanel.getCameraX() - width / 2, y + height / 3, width / 3, height / 3 * 2);

            graphics.setColor(Color.BLACK);
            graphics.drawString(getMeters() + "m", x + width / 4 / 3 - SimulationPanel.getCameraX() - width / 2, y + height / 3 / 4 + 10);

            //System.out.println();

        }
    }

    @Override
    public void updateCoords(Dimension oldDimension, Dimension newDimension) {

        x = x * newDimension.width / oldDimension.width;
        y = y * newDimension.height / oldDimension.height;
        width = width * newDimension.width / oldDimension.width;
        height = height * newDimension.height / oldDimension.height;

    }

    public int getDistance(){

        return x;

    }

    public int getMeters(){

        return x / 250;

    }
}
