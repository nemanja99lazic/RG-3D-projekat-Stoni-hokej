package menu;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

public class MenuChoosePuck extends Menu{

    public static double SCENE_WIDTH = 900;
    public static double SCENE_HEIGHT = 700;
    public static int FONT_SIZE = 30;

    private Translate triangleTranslation;

    public MenuChoosePuck()
    {
        choice = 1;

        Rectangle background = new Rectangle( - SCENE_WIDTH / 2, - SCENE_HEIGHT / 2, SCENE_WIDTH + SIZE / 2, SCENE_HEIGHT + SIZE / 2);
        background.setFill(Color.LIGHTYELLOW);

        Path triangle = new Path(
                new MoveTo(0, 0),
                new LineTo(15, 15),
                new LineTo(0, 30),
                new ClosePath()
        );
        triangle.setFill(Color.BLACK);
        triangleTranslation = new Translate(0, 10);
        triangle.getTransforms().add(
                triangleTranslation
        );

        Text textPuck1 = new Text("Basic puck");
        Text textPuck2 = new Text("Hexagon puck");
        Text textPuck3 = new Text("Radioactive puck");

        textPuck1.setFont(Font.font(FONT_SIZE));
        textPuck1.getTransforms().add(
                new Translate(30, SIZE / 3 - FONT_SIZE / 2)
        );

        textPuck2.setFont(Font.font(FONT_SIZE));
        textPuck2.getTransforms().add(
                new Translate(30, 2 * SIZE / 3 - FONT_SIZE / 2)
        );

        textPuck3.setFont(Font.font(FONT_SIZE));
        textPuck3.getTransforms().add(
                new Translate(30, SIZE - FONT_SIZE / 2)
        );

        root.getChildren().addAll(background, triangle, textPuck1, textPuck2, textPuck3);

        this.getChildren().add(root);
        this.getTransforms().add(
                new Translate(SCENE_WIDTH / 2 - SIZE / 2, SCENE_HEIGHT / 2 - SIZE / 2)
        );

    }

    @Override
    public void move(int direction)
    {
        switch (direction)
        {
            case DOWN:
            {
                if(choice == 3)
                    return;
                choice++;
                triangleTranslation.setY(triangleTranslation.getY() + 50);
            }
            break;
            case UP:
            {
                if(choice == 1)
                    return;
                choice--;
                triangleTranslation.setY(triangleTranslation.getY() - 50);
            }
            break;
            default:
                break;
        }
    }

    public int getChoice()
    {
        return choice;
    }
}
