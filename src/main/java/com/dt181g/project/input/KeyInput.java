package com.dt181g.project.input;

import com.dt181g.project.Model;
import com.dt181g.project.Values.MovingValues;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.dt181g.project.Values.Constants.IDLE;
import static com.dt181g.project.Values.Constants.RUNNING;

/**
 * This KeyInput class implements KeyListener and collecting KeyInputs from the user.
 * @author Robin Kadergran
 */
public class KeyInput implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * This override method takes the input from the user and sets the hero's animation and position on the screen.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 87:
                MovingValues.INSTANCE.setyDelta(-7);
                Model.setAnimation(RUNNING);
                break;
            case 65:
                MovingValues.INSTANCE.setxDelta(-7);
                Model.setAnimation(RUNNING);
                break;
            case 83:
                MovingValues.INSTANCE.setyDelta(7);
                Model.setAnimation(RUNNING);
                break;
            case 68:
                MovingValues.INSTANCE.setxDelta(7);
                Model.setAnimation(RUNNING);
                break;
        }

    }

    /**
     * This override method sets the hero's animation back to IDLE when the key is released.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 87:
            case 65:
            case 83:
            case 68:
                Model.setAnimation(IDLE);
                break;
        }
    }
}