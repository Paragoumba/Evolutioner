package fr.paragoumba.evolutioner.Graphic;

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

            BufferedImage startImage = ImageIO.read(Display.class.getResourceAsStream("res/startImage.png"));
            g.drawImage(startImage, Evolutioner.frame.getWidth() / 2 - 512 / 2, Evolutioner.frame.getHeight() / 2 - 768 / 2, null);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
