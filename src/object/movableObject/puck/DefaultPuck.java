package object.movableObject.puck;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class DefaultPuck extends Puck{

    protected Cylinder puck;

    public DefaultPuck( double radius, double height )
    {
        super(radius, height);

        this.puck = new Cylinder( radius, height );
        puck.setMaterial ( new PhongMaterial( Color.GRAY ) );
        super.getChildren ( ).addAll (
                puck
        );

    }

    @Override
    public int destroyLives()
    {
        return 1;
    }
}
