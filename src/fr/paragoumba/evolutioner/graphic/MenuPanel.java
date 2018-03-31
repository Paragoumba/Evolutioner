package fr.paragoumba.evolutioner.graphic;

import fr.paragoumba.evolutioner.Evolutioner;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(){

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");

        startButton.addActionListener(e -> Evolutioner.setWaiting(false));
        quitButton.addActionListener(e -> {

            Evolutioner.stop();
            System.exit(0);

        });

        add(startButton);
        add(quitButton);

    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

    }
}
