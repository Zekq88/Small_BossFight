package com.dt181g.project.Values;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This Enum singleton is keeping track of the boss and the hero health bar.
 * This Class is also containing the producer part of the consumer and producer pattern.
 * @author Robin Kadergran
 */
public enum HealthValue implements Runnable{

    INSTANCE;
    private Thread producer;
    private BlockingQueue<Integer> LifeSteal = new ArrayBlockingQueue<>(8);
    private int Boss_Health = 150;
    private int Hero_Health = 10;

    /**
     * This method is a getter that returns the hero's life.
     */
    public int getHero_Health(){
        return Hero_Health;
    }

    /**
     * This method is a typical getter and provides the current health of the boss.
     * @return Boss_Health
     */
    public int getBossHealth() {
        return Boss_Health;
    }

    /**
     * This method is a setter and updates the boss health bar using the x integer
     * , adds the integer x to the blockingQueue LifeSteal, and starts the producer thread.
     * @param x int parameter.
     * @return Boss_Health
     */
    public int setBossHealth(int x) {
        LifeSteal.add(x);
        producer = new Thread(this);
        producer.start();

        if(Boss_Health == 0) {
            LifeSteal.clear();
        }
        return Boss_Health -= x;
    }

    /**
     * This override run() which executes the producer threads actions.
     */
    @Override
    public void run() {
        try {
            Hero_Health += LifeSteal.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

