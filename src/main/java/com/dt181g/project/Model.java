package com.dt181g.project;


import com.dt181g.project.Values.Constants;
import com.dt181g.project.Values.MovingValues;

import static com.dt181g.project.Values.Constants.*;

/**
 * The model part of the MVC pattern.
 * This class contains logic for animations and the boss hitbox.
 * @author Robin Kadergran
 */
public class Model {

    private int animationFrame;
    private int animationIndex;

    static boolean dmgHit = false;

    /**
     * This constructor method loads all the method to start the game.
     */
    public Model() {
        bossHitBox();
    }

    /**
     * This method is geting updated through the Observer pattern.
     */
    public void updating() {
        updateAnimation();
    }

    /**
     * This method is responsible to sync each animation type from hero move with each frame each second.
     */
    public void updateAnimation(){
        MovingValues.INSTANCE.setAnimationFrame(animationFrame++);
        int animationSpeed = 4;
        if (MovingValues.INSTANCE.getAnimationFrame() >= animationSpeed){
            MovingValues.INSTANCE.setAnimationFrame(animationFrame = 0);
            MovingValues.INSTANCE.setAnimationIndex(animationIndex++);
            if (MovingValues.INSTANCE.getAnimationIndex() >= Constants.getHeroAnimation(MovingValues.INSTANCE.getHeroMove())){  // Constants.getHeroAnimation(heroMove)
                setAnimation(IDLE);
                MovingValues.INSTANCE.setAnimationIndex(animationIndex = 0);
            }
        }else if(MovingValues.INSTANCE.getAnimationFrame() == MovingValues.INSTANCE.getHeroMove()){
            setAnimation(IDLE);
        }
    }

    /**
     * This method sets the different types av animation to the heroMove variable depending on the int x from keyListener input.
     * @param x int.
     */
    public static void setAnimation(int x) {
        if (x == RUNNING){

            MovingValues.INSTANCE.setHeroMove(RUNNING);
        } else if (x == ATTACK){

            MovingValues.INSTANCE.setHeroMove(ATTACK);
            MovingValues.INSTANCE.setAnimationFrame(0);
            MovingValues.INSTANCE.setAnimationIndex(0);
        } else {
            MovingValues.INSTANCE.setHeroMove(IDLE);
        }
    }

    /**
     * This method creates a hitbox, and if the hero is attacking inside it, the boss will take damage.
     * @return dmgHit if true else false if there is not a hit.
     */
    public static boolean bossHitBox(){
        if (MovingValues.INSTANCE.getyDelta() > 280 & MovingValues.INSTANCE.getyDelta() < 360){
            if (MovingValues.INSTANCE.getxDelta() < 580 & MovingValues.INSTANCE.getxDelta() > 460){
                return dmgHit = true;
            }
        }
        return false;
    }
}
