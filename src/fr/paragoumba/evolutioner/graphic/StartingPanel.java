package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.Evolutioner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartingPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        try {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Evolutioner.frame.getWidth(), Evolutioner.frame.getHeight());

            BufferedImage startImage = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/startImage.png"));
            g.drawImage(startImage, Evolutioner.frame.getWidth() / 2 - 512 / 2, Evolutioner.frame.getHeight() / 2 - 768 / 2, null);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
