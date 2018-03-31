package fr.paragoumba.evolutioner;

import fr.paragoumba.evolutioner.graphic.SimulationPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getExtendedKeyCode();

        if (Evolutioner.debug){

            char c = e.getKeyChar();
            System.out.println("Input ! (" + (c == '\n' ? "\\n" : c) + ", Control:" + e.isControlDown() + ", Alt:" + e.isAltDown() + ", Shift:" + e.isShiftDown() + ", KeyCode:" + e.getExtendedKeyCode() + ")");

        }

        if (Evolutioner.displayedPanel == Evolutioner.SIMULATION_PANEL){

            if (keyCode == KeyEvent.VK_AMPERSAND) {

                Evolutioner.displayConfigFrame();

            } else if (keyCode == 16777449) {

                Evolutioner.displayStatsFrame();

            } else if (Evolutioner.debug && keyCode == KeyEvent.VK_QUOTEDBL) {

                Evolutioner.displayLogFrame();

            } else if (Evolutioner.debug && keyCode == KeyEvent.VK_QUOTE) {

                Evolutioner.displayDebugFrame();

            } else if (keyCode == KeyEvent.VK_G) {

                Farm.generateCreatures();

            } else if (keyCode == KeyEvent.VK_H) {

                Farm.toggleSimulation();

            } else if (keyCode == KeyEvent.VK_RIGHT){

                SimulationPanel.setCameraX(SimulationPanel.getCameraX() + 5);

            } else if (keyCode == KeyEvent.VK_LEFT){

                SimulationPanel.setCameraX(SimulationPanel.getCameraX() - 5);

            }

        } else if (Evolutioner.displayedPanel == Evolutioner.TUTORIAL_PANEL || Evolutioner.displayedPanel == Evolutioner.MENU_PANEL) {

            if (keyCode == '\n'){

                Evolutioner.displayedPanel = Evolutioner.SIMULATION_PANEL;
                Evolutioner.setWaiting(false);

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
