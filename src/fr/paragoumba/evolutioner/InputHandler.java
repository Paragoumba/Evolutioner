package fr.paragoumba.evolutioner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

        if (Evolutioner.debug) System.out.println("Input ! (" + e.getKeyChar() + ", Control:" + e.isControlDown() + ", Alt:" + e.isAltDown() + ")");

        if (e.isAltDown() && e.getKeyChar() == 'k'){

            Evolutioner.displayConfigFrame();

        } else if (e.getKeyChar() == 'g'){

            Farm.generateCreatures();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
