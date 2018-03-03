package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.Evolutioner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TutorialPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        try {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Evolutioner.frame.getWidth(), Evolutioner.frame.getHeight());

            BufferedImage oneKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/ampersand.png"));
            BufferedImage twoKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/eacute.png"));
            BufferedImage threeKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/quotation_mark.png"));
            BufferedImage fourKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/apostrophe.png"));
            BufferedImage gKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/g.png"));
            BufferedImage hKey = ImageIO.read(Evolutioner.class.getResourceAsStream("res/h.png"));

            int length = oneKey.getWidth() + twoKey.getWidth() + threeKey.getWidth() + fourKey.getWidth() + gKey.getWidth() + hKey.getWidth();
            int x = Evolutioner.frame.getWidth() / 2 - length / 2;
            int y = Evolutioner.frame.getHeight() / 2;

            g.drawImage(oneKey, x, y - oneKey.getHeight(), null);
            g.drawImage(twoKey, x + length / 6, y - twoKey.getHeight(), null);
            g.drawImage(threeKey, x + length / 6 * 2, y - threeKey.getHeight(), null);
            g.drawImage(fourKey, x + length / 6 * 3, y - fourKey.getHeight(), null);
            g.drawImage(gKey, x + length / 6 * 4, y - gKey.getHeight(), null);
            g.drawImage(hKey, x + length / 6 * 5, y - hKey.getHeight(), null);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
