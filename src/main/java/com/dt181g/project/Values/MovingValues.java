package com.dt181g.project.Values;

import java.util.ArrayList;

/**
 * This Enum Singleton is keeping track of the hero's position on the screen, and using the Subject interface of the
 * Observer pattern, and it adds other classes as observers into the ArrayList.
 * @author Robin Kadergran
 */
public enum MovingValues implements Subject{
    INSTANCE;
    private ArrayList<Observer> observers;
    private int xDelta = 200;
    private int yDelta = 330;

    private int animationIndex;
    private int animationFrame;
    private int heroMove;

    MovingValues(){
        observers = new ArrayList<Observer>();
    }

    /**
     * This method is a typical getter and provides the hero's current position on the y-axel.
     * @return yDelta
     */
    public int getyDelta() {
        return yDelta;
    }

    /**
     * This method is a typical setter and updates the hero's position on the y-axel using the yDealta integer.
     * @param yDelta int parameter.
     */
    public void setyDelta(int yDelta) {
        this.yDelta += yDelta;
    }

    /**
     * This method is a typical getter and provides the hero's current position on the x-axel.
     * @return xDelta
     */
    public int getxDelta() {
        return xDelta;
    }

    /**
     * This method is a typical setter and updates the hero's position on the x-axel using the xDealta integer.
     * @param xDelta int parameter.
     */
    public void setxDelta(int xDelta) {
        this.xDelta += xDelta;
    }

    /**
     * This method is a getter for the animationFrame variable.
     * @return animationFrame
     */
    public int getAnimationFrame() {
        return animationFrame;
    }

    /**
     * This method is a getter for the heroMove variable.
     * @return heroMove
     */
    public int getHeroMove() {
        return heroMove;
    }

    /**
     * This method is a getter for the animationIndex variable.
     * @return animationIndex
     */
    public int getAnimationIndex() {
        return animationIndex;
    }

    /**
     * This method is a setter and rewrite the animationIndex variable
     * @param animationIndex int parameter.
     */
    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    /**
     * This method is a setter and rewrite the heroMove variable
     * @param heroMove int parameter.
     */
    public void setHeroMove(int heroMove) {
        this.heroMove = heroMove;
    }

    /**
     * This method is a setter and rewrite the animationFrame variable
     * @param animationFrame int parameter.
     */
    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
    }

    /**
     * This override method adds the observer to the ArrayList.
     * @param o Observer parameter.
     */
    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    /**
     * This override method removes the observer to the ArrayList.
     * @param o Observer parameter.
     */
    @Override
    public void unRegister(Observer o) {
        observers.remove(o);
    }

    /**
     * This override method notifies all the observers inside the ArrayList.
     */
    @Override
    public void notifyObserver() {
        for(Observer observer : observers){
            observer.update();
        }
    }
}
