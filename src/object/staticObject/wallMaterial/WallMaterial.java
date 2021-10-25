package object.staticObject.wallMaterial;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;

public class WallMaterial extends PhongMaterial {

    public WallMaterial()
    {
        super();
        this.setDiffuseMap(new Image("wall.jpg"));
    }
}
