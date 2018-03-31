package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.Evolutioner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TutorialPanel extends JPanel {

    private final String[] lines = {"I didn't thought you needed tutorial", ""};

    private JTextPane oneKeyTip = new JTextPane();
    private JTextPane twoKeyTip = new JTextPane();
    private JTextPane threeKeyTip = new JTextPane();
    private JTextPane fourKeyTip = new JTextPane();
    private JTextPane gKeyTip = new JTextPane();
    private JTextPane hKeyTip = new JTextPane();
    
    public TutorialPanel(){

        oneKeyTip.setText("Shows up config\nframe");
        twoKeyTip.setText("Shows up stats\nframe");
        threeKeyTip.setText("Shows up log\nframe");
        fourKeyTip.setText("Shows up debug\nframe");
        gKeyTip.setText("Generates new\nCreatures");
        hKeyTip.setText("Runs / Pauses\nsimulation");

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        StyledDocument styledDocument = oneKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        styledDocument = twoKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        styledDocument = threeKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        styledDocument = fourKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        styledDocument = gKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        styledDocument = hKeyTip.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        add(oneKeyTip);
        add(twoKeyTip);
        add(threeKeyTip);
        add(fourKeyTip);
        add(gKeyTip);
        add(hKeyTip);

    }

    @Override
    protected void paintComponent(Graphics g) {

        try {
            
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Evolutioner.frame.getWidth(), Evolutioner.frame.getHeight());

            BufferedImage oneKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/ampersand.png"));
            BufferedImage twoKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/e_acute.png"));
            BufferedImage threeKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/quotation_mark.png"));
            BufferedImage fourKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/apostrophe.png"));
            BufferedImage gKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/g.png"));
            BufferedImage hKey = ImageIO.read(Evolutioner.class.getResourceAsStream("resources/h.png"));

            int gapX = 100;
            int length = oneKey.getWidth() + twoKey.getWidth() + threeKey.getWidth() + fourKey.getWidth() + gKey.getWidth() + hKey.getWidth();
            int x = Evolutioner.frame.getWidth() / 2 - length / 2;
            int y = Evolutioner.frame.getHeight() / 2;
            int lineY = Evolutioner.frame.getHeight() - 42;

            g.drawImage(oneKey, (int) (x + gapX * -3 * 0.875), y - oneKey.getHeight(), null);
            g.drawImage(twoKey, (int) (x + length / 6 + gapX * 0.75 * -2), y - twoKey.getHeight(), null);
            g.drawImage(threeKey, x + length / 6 * 2 + gapX / 2 * -1, y - threeKey.getHeight(), null);
            g.drawImage(fourKey, x + length / 6 * 3 + gapX / 2, y - fourKey.getHeight(), null);
            g.drawImage(gKey, (int) (x + length / 6 * 4 + gapX * 1.5), y - gKey.getHeight(), null);
            g.drawImage(hKey, (int) (x + length / 6 * 5 + gapX * 2.5), y - hKey.getHeight(), null);

            oneKeyTip.setLocation((int) (x + gapX * -3 * 0.875) + oneKey.getWidth() / 2 - oneKeyTip.getWidth() / 2, y - oneKey.getHeight() + gapX / 2);
            twoKeyTip.setLocation((int) (x + length / 6 + gapX * 0.75 * -2) + twoKey.getWidth() / 2 - twoKeyTip.getWidth() / 2, y - twoKey.getHeight() + gapX / 2);
            threeKeyTip.setLocation(x + length / 6 * 2 + gapX / 2 * -1 + threeKey.getWidth() / 2 - threeKeyTip.getWidth() / 2, y - threeKey.getHeight() + gapX / 2);
            fourKeyTip.setLocation(x + length / 6 * 3 + gapX / 2 + fourKey.getWidth() / 2 - fourKeyTip.getWidth() / 2, y - fourKey.getHeight() + gapX / 2);
            gKeyTip.setLocation((int) (x + length / 6 * 4 + gapX * 1.5) + gKey.getWidth() / 2 - gKeyTip.getWidth() / 2, y - gKey.getHeight() + gapX / 2);
            hKeyTip.setLocation((int) (x + length / 6 * 5 + gapX * 2.5) + hKey.getWidth() / 2 - hKeyTip.getWidth() / 2, y - hKey.getHeight() + gapX / 2);
            
            g.setColor(Color.BLACK);

            Graphics2D g2d = (Graphics2D) g;
            AffineTransform orig = g2d.getTransform();

            g2d.rotate(Math.PI / 3);
            g2d.drawString(getRandomString(), 1000, 100);
            g2d.setTransform(orig);

            g.drawString("Press 'Enter' to pass", Evolutioner.frame.getWidth() - 155, Evolutioner.frame.getHeight() - 45);
            g.drawLine(Evolutioner.frame.getWidth() - 154, lineY, Evolutioner.frame.getWidth() - 27, lineY);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private String getRandomString(){

        return lines[(int) (Math.random() * lines.length)];

    }
}
