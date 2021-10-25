package object.movableObject.puck;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import object.movableObject.puck.Puck;

public class SpecialPuck extends DefaultPuck {

    public SpecialPuck(double radius, double height )
    {
        super(radius, height);
        puck.setMaterial(new PhongMaterial(Color.ORANGE));
    }

    @Override
    public int destroyLives()
    {
        return 2;
    }
}
