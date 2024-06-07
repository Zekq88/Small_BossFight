package com.dt181g.project;

import javax.swing.*;

/**
 * The main starting point for Project Assignment.
 * @author Robin Kadergran
 */
public final class Project {
    /**
     * The utility class with a <c>IllegalStateException</c> thrown.
     */
    private Project() {throw new IllegalStateException("Utility Class");}

    /**
     * Starts the MVC pattern and the game using the Event Dispatch Thread (EDT).
     * @param args command arguments.
     */
    public static void main(String[] args) {
        // The Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            new Controller(model, view);
        });
    }
}
