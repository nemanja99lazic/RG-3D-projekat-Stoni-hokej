package object.staticObject.paddle;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class BasicPaddle extends Paddle{

    public BasicPaddle( double width, double height, double depth )
    {
        super(width, height, depth);

        Box paddle = new Box ( width, height / 2, depth );
        paddle.setMaterial ( new PhongMaterial( Color.GREEN ) );
        super.getChildren ( ).addAll ( paddle );
    }
}
