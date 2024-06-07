package com.dt181g.project;

import com.dt181g.project.Values.MovingValues;
import com.dt181g.project.Values.Observer;
import com.dt181g.project.input.KeyInput;
import com.dt181g.project.input.MouseInputs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The controller part of the MVC pattern and the observer part of the Observer pattern.
 * @author Robin Kadergran
 */
public class Controller implements Observer, ActionListener {
    private Model model;
    private View view;

    /**
     * This constructor method which takes Model and View as arguments execute the EDT which starts the timer,
     * actionListner, KeyListeners, MouseListener, and adds this class to the Observer pattern.
     * @param model the argument.
     * @param view the argument.
     */
    public Controller(Model model, View view) {

        // EDT execution of the frames per second game engine functionality.
        SwingUtilities.invokeLater(() -> {

            this.model = model;
            this.view = view;
            Timer timer = new Timer(20,this);
            timer.start();

            view.addKeyListener(new KeyInput());
            view.addMouseListener(new MouseInputs());
            view.setFocusable(true);
            view.requestFocusInWindow();
        });
       //Adding the controller to the Observer pattern.
        MovingValues.INSTANCE.register(this);
    }

    /**
     * This override actionListener method notifies the Observers and adds stability though the threadPool.
     * @param e the event to be processed
     */
        @Override
        public void actionPerformed(ActionEvent e) {
            MovingValues.INSTANCE.notifyObserver();
            ExecutorService executor = Executors.newFixedThreadPool(3, r -> new Thread());
            executor.execute(this::update);
            executor.shutdown();
        }

    /**
     * This override Observer method updates the animation inside the model and repaints the view.
     */
    @Override
    public void update() {
        model.updating();
        view.repaint();
    }
}
