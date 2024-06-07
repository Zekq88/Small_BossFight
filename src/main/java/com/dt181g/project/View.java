package com.dt181g.project;

import com.dt181g.project.Values.HealthValue;
import com.dt181g.project.Values.MovingValues;
import com.dt181g.project.Values.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.Font.BOLD;

/**
 * The viewer part of the MVC pattern, the observer of the Observer pattern, images for both hero and boss.
 *This class extends Jpanel.
 * @author Robin Kadergran
 */
public class View extends JPanel implements Observer {
    private Image backgroundImage;
    private BufferedImage img;
    private BufferedImage enemyImg;
    private BufferedImage enemyDeathImg;

    /**
     * This constructor method inserts the background image to the stream and adds this class to the Observer pattern.
     */
    public View() {
        setupWindow();
        LoadHeroAnimations();
        ImportEnemyDeathImg();
        ImportEnemyImg();
        try (InputStream stream = getClass().getResourceAsStream("/bkimg.jpg")) {
            assert stream != null;
            backgroundImage = ImageIO.read(stream);
        } catch (IOException e) {
            e.getCause();
        }
        //Adding the view to the Observer pattern.
        MovingValues.INSTANCE.register(this);
    }

    /**
     * This method sets the foundational window for the game including menu.
     */
    private void setupWindow(){
        JFrame frame = new JFrame("Boss Scene");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JLabel la = new JLabel("         Click left mouse button to attack. If the player isn't moving while pressing 'W', 'S', 'A', 'D' please reboot the game. Sorry for this inconvenient");
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOption = new JMenu("File");
        JMenuItem closeGame = new JMenuItem("Exit Game");
        closeGame.addActionListener((e) -> {System.exit(0);});
        menuOption.add(closeGame);
        menuBar.add(menuOption);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuBar);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(la, BorderLayout.SOUTH);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
    }

    /**
     * This method inserts the boss image into the Stream.
     */
    private void ImportEnemyImg() {
        try{
            InputStream is = getClass().getResourceAsStream("/smallguy.png");
            assert is != null;
            enemyImg = ImageIO.read(is);
            is.close();
        } catch (IOException e){
            e.getCause();
        }
    }

    /**
     * This method inserts the boss death image into the Stream.
     */
    private void ImportEnemyDeathImg() {
        try{
            InputStream is = getClass().getResourceAsStream("/enemyDeath.png");
            assert is != null;
            enemyDeathImg = ImageIO.read(is);
            is.close();
        } catch (IOException e){
            e.getCause();
        }
    }

    /**
     * This method inserts the hero sprite sheet into the Stream.
     */
    public void LoadHeroAnimations(){
        try{
            InputStream is = getClass().getResourceAsStream("/player_sprites.png");
            assert is != null;
            img = ImageIO.read(is);
            is.close();
        } catch (IOException e){
            e.getCause();
        }
    }

    /**
     * This method uses the BufferedImage from LoadAnimation() and uses it a double for-loop to chop all the images
     * into a 3d array of BufferedImages.
     * @return a 3d array containing 9 different animations with a maximum 6 frames each.
     */
    public BufferedImage[][] updateHeroMove(){
        BufferedImage[][] heroAnimation = new BufferedImage[9][6];
        for (int j = 0; j < heroAnimation.length; j++)
            for (int i = 0; i < heroAnimation[j].length; i++){
                heroAnimation[j][i] = img.getSubimage(i*64, j*40,64,40);
            }
        return heroAnimation;
    }

    /**
     * This override paintComponent paints and draw all the animation of the characters, background, and the texts.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            //Drawing the background image.
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            //Drawing the scene with health bars, texts and the hero and the boss.
            g.setColor(Color.RED);
            g.fillRect(MovingValues.INSTANCE.getxDelta() + 10,MovingValues.INSTANCE.getyDelta() - 30, 80,20);
            g.setColor(Color.GREEN);
            g.fillRect(MovingValues.INSTANCE.getxDelta() + 10,MovingValues.INSTANCE.getyDelta() - 30, HealthValue.INSTANCE.getHero_Health() / 2,20);
            g.drawImage(updateHeroMove()[MovingValues.INSTANCE.getHeroMove()][MovingValues.INSTANCE.getAnimationIndex()],
                    MovingValues.INSTANCE.getxDelta(), MovingValues.INSTANCE.getyDelta(), 96,70, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("sansSarif Bold Italic", BOLD, 32 ));
            g.drawString("The Boss is Stunned by fear! Act now Hero!", 60, 100);
            g.drawString("Use your vampire sword to regain your life!", 60, 140);

            //An if loop on which picture of the boss depending on health.
            if (HealthValue.INSTANCE.getBossHealth() > 0) {
                g.setColor(Color.RED);
                g.fillRect(530,280, 150,20);
                g.setColor(Color.GREEN);
                g.fillRect(530,280, HealthValue.INSTANCE.getBossHealth(),20);
                g.drawImage(enemyImg, 550, 300, 120, 100, null);
            } else {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("sansSarif Bold Italic", BOLD, 32 ));
                g.drawString("You Won!",540,280);
                g.drawImage(enemyDeathImg, 550, 300, 120, 100, null);
        }
    }

    /**
     * This override Observer method repaints the view.
     */
    @Override
    public void update() {
        repaint();
    }
}

