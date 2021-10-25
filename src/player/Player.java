package player;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.awt.*;

public class Player extends Group {

    private int lives;
    private double energy;

    private SubScene subscene;

    public static final int MAX_LIVES = 5;
    public static final double LIFE_HEIGHT = 50;
    public static final double LIFE_WIDTH = 10;
    public static final Color LIFE_COLOR = Color.YELLOW;
    private static final double MAX_ENERGY = 1.2;
    private static final double MIN_ENERGY = 1.05;
    //private static final double ENERGY_DECREMENT = 0.999;

    public Player()
    {
        //super(root, LIFE_WIDTH, 10 * LIFE_WIDTH);
        lives = MAX_LIVES;
        energy = MAX_ENERGY;

        drawLives();

        subscene = new SubScene(this, LIFE_WIDTH * 10, LIFE_HEIGHT);
    }

    public double getEnergy()
    {
        return energy;
    }

    public void setEnergyToMax()
    {
        energy = MAX_ENERGY;
    }

    public void setEnergyToMin()
    {
        energy = MIN_ENERGY;
    }

    public void decreaseEnergy()
    {
//        System.out.println("ENERGY: " + energy);
//        if(energy - ENERGY_DECREMENT >= MIN_ENERGY)
//            energy = energy - ENERGY_DECREMENT;
        energy = MIN_ENERGY;
    }

    public void drawLives()
    {
        this.getChildren().setAll();

        double dx = LIFE_WIDTH + 10;

        for(int i = 0; i < lives; i++)
        {
            Rectangle rectangle = new Rectangle(LIFE_WIDTH, LIFE_HEIGHT, LIFE_COLOR);
            rectangle.getTransforms().add(new Translate(i * dx, 0, 0));
            this.getChildren().add(rectangle);
        }
    }

    public SubScene getSubscene() {
        return subscene;
    }

    public void decreaseLives()
    {
        this.lives--;

        if(this.lives > 0)
            this.getChildren().remove(0);
    }

    public int getLives()
    {
        return this.lives;
    }
}
