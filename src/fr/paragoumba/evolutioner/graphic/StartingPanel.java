package fr.paragoumba.evolutioner.graphic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartingPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0,0,SimulationPanel.frameWidth,SimulationPanel.frameHeight);

        try {

            BufferedImage darwin = ImageIO.read(StartingPanel.class.getResource("../resources/startImage.png"));
            g.drawImage(darwin,SimulationPanel.frameWidth/2-darwin.getWidth()/2,SimulationPanel.frameHeight/2-darwin.getHeight()/2,null);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
