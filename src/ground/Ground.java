package ground;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Ground extends Group {

    public static final double LINE_STROKE_WIDTH = 10;
    public static final double CIRCLE_RADIUS = 100;

    public Ground(double width, double height){
//        Rectangle ground = new Rectangle(width, height, Color.LIGHTBLUE);
//        //ground.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));  ------------- treba da se doda ovo za ceo ROOT
//        Line line = new Line(width / 2, 0, width / 2, height);
//        line.setStroke(Color.RED);
//        line.setStrokeWidth(LINE_STROKE_WIDTH);
//
//        Circle centerCircle = new Circle(0.2 * height);
//        centerCircle.setFill(null);
//        centerCircle.setStroke(Color.RED);
//        centerCircle.setStrokeWidth(LINE_STROKE_WIDTH);
//        centerCircle.getTransforms().add(new Translate(width / 2, height / 2));
//
//        this.getChildren().addAll(ground, line, centerCircle);

//        this.getTransforms().addAll(
//                new Rotate(-90, Rotate.X_AXIS));

        Canvas ground = new Canvas(width, height);
        GraphicsContext context = ground.getGraphicsContext2D();

        context.setFill(Color.LIGHTBLUE);
        context.fillRect(0, 0, width, height);

        context.setFill(null);
        context.setStroke(Color.RED);
        context.setLineWidth(LINE_STROKE_WIDTH);

        context.strokeLine(width / 2, 0, width / 2, height);

        context.strokeOval(width / 2 - 0.2 * height, height / 2 - 0.2 * height, 0.2 * height * 2, 0.2 * height * 2);

        context.strokeArc(- 0.25 * height, height / 2 / 2, 0.25 * height * 2, 0.25 * height * 2, -90, 180, ArcType.OPEN);

        context.strokeArc(width - 0.25 * height, height / 2 / 2, 0.25 * height * 2, 0.25 * height * 2, -90, -180, ArcType.OPEN);

        this.getChildren().add(ground);
    }
}
