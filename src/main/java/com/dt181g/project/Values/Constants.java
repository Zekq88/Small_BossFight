package com.dt181g.project.Values;

/**
 * This class provides the game with static variables and method.
 * @author Robin Kadergran
 */
public class Constants {


    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int HIT = 5;
    public static final int ATTACK = 6;

    /**
     * This method sets the limit of frames in each available hero animation.
     * @param heroMove parameter.
     * @return an integer specified to the correct hero animation.
     */
    public static int getHeroAnimation(int heroMove){
        return switch (heroMove) {
            case RUNNING, IDLE -> 5;
            case HIT -> 4;
            case ATTACK -> 3;
            default -> 0;
        };
    }
}
