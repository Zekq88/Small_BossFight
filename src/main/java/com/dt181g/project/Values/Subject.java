package com.dt181g.project.Values;

/**
 * This Subject interface is the subject part of the Observer pattern.
 * @author Robin Kadergran
 */
public interface Subject {

    /**
     * This override method registers the observer to the list.
     * @param o Observer parameter.
     */
    void register(Observer o);

    /**
     * This override method unregistered the observer to the list.
     * @param o Observer parameter.
     */
    void unRegister(Observer o);

    /**
     * This override method notifies all the observers in the list.
     */
    void notifyObserver();
}
