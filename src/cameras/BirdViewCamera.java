package cameras;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class BirdViewCamera extends PerspectiveCamera {

    private Translate translate;
    private Rotate rotate;

    public BirdViewCamera(boolean fixedEyeAtZero, double x, double y, double z, double rotateX, double nearClip, double farClip)
    {
        super(fixedEyeAtZero);
        this.setNearClip(nearClip);
        this.setFarClip(farClip);

        translate = new Translate(x, y, z);
        rotate = new Rotate(rotateX, Rotate.X_AXIS);

        this.getTransforms().addAll(
                translate,
                rotate
        );
    }
}
