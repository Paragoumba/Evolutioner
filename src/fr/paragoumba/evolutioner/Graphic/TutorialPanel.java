package fr.paragoumba.evolutioner.Graphic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TutorialPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        try {

            BufferedImage tutorialImage = ImageIO.read(Display.class.getResourceAsStream("res/tutorialImage.png"));
            g.drawImage(tutorialImage, 0, 0, null);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
