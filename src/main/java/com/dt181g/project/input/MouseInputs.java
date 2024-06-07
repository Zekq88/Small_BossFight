package com.dt181g.project.input;

import com.dt181g.project.Model;
import com.dt181g.project.Values.Constants;
import com.dt181g.project.Values.HealthValue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.dt181g.project.Values.Constants.*;

/**
 * This MouseInputs class implements MouseListener and collects inputs from users mouse while starting the consumer
 * part of the consumer and producer pattern.
 * @author Robin Kadergran
 */
public class MouseInputs implements MouseListener, Runnable{
    private Thread consumer;

    /**
     * This override method takes the input from the user and sets the hero's animation to ATTACK, and if the Hero is inside
     * the boss hitbox, the boss is taking damage.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1) {
            Model.setAnimation(ATTACK);
            Constants.getHeroAnimation(ATTACK);
            if (HealthValue.INSTANCE.getBossHealth() >= 0) {
                if (Model.bossHitBox()) {
                    consumer = new Thread(this);
                    consumer.start();
                }
            }
        } else {
            Model.setAnimation(IDLE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * This override method sets the hero animation to IDLE when released.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Model.setAnimation(IDLE);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * This override run() executes the consumer action.
     */
    @Override
    public void run() {
        HealthValue.INSTANCE.setBossHealth(10);
    }
}