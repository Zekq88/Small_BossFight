package com.dt181g.project.Values;

/**
 * This Observer interface is the observer part of the Observer pattern.
 * @author Robin Kadergran
 */
public interface Observer {
    /**
     * The override method for the observer pattern.
     */
    default void update(){}
}
