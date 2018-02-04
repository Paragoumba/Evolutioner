package fr.paragoumba.evolutioner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

        if (Evolutioner.debug) System.out.println("Input ! (" + e.getKeyChar() + ", Control:" + e.isControlDown() + ", Alt:" + e.isAltDown() + ")");

        if (e.isAltDown()){

            char c = e.getKeyChar();

            if (c == '&'){

                Evolutioner.displayConfigFrame();

            } else if (c == 'é'){

                Evolutioner.displayStatsFrame();

            } else if (Evolutioner.debug && c == '"'){

                Evolutioner.displayLogFrame();

            } else if (Evolutioner.debug && c == '\''){

                Evolutioner.displayDebugFrame();

            }
        }

        else if (e.getKeyChar() == 'g') Farm.generateCreatures();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
