package object.staticObject;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.util.Duration;
import object.movableObject.MovableObject;

public class Goal extends StaticObject{

    private GameStopper gameStopper;
    private Wall.Type type;

    private Box goal;
    private PhongMaterial goalMaterial;

    private Timeline colorChangeTimeline;

    public static final Color GOAL_COLOR = Color.BLUE;

    public Goal (double width, double height, double depth, Wall.Type type, Goal.GameStopper gameStopper)
    {
        this.goal= new Box(0.5 * width, height, depth);

        this.goalMaterial = new PhongMaterial(GOAL_COLOR);
        this.goal.setMaterial(this.goalMaterial);

        this.type = type;
        this.gameStopper = gameStopper;
        super.getChildren().add(goal);

        KeyValue keyValueColorRed = new KeyValue(this.goalMaterial.diffuseColorProperty(), Color.RED);
        KeyValue keyValueColorBlue = new KeyValue(this.goalMaterial.diffuseColorProperty(), Color.BLUE);

        Duration t1 = Duration.ZERO;
        Duration t2 = Duration.seconds(2);

        KeyFrame frameStart = new KeyFrame(t1, keyValueColorRed);
        KeyFrame frameEnd = new KeyFrame(t2, keyValueColorBlue);

        this.colorChangeTimeline = new Timeline(frameStart, frameEnd);

    }

    public boolean collision ( MovableObject movableObject ) {
        boolean collisionDetected = super.collision ( movableObject );

        if(collisionDetected)
        {
            this.colorChangeTimeline.playFromStart();
            gameStopper.stopGame(this.type);
        }

        return collisionDetected;
    }

    public static interface GameStopper {
        void stopGame ( Wall.Type wallType);
    }
}
