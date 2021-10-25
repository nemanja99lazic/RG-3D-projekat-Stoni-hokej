package cameras.trackingCameras;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import object.staticObject.paddle.Paddle;

public abstract class PaddleTrackingCamera extends PerspectiveCamera {

    protected Paddle paddle;
    protected Translate translate;
    protected Rotate rotateY;
    protected Rotate rotateZ;

    public PaddleTrackingCamera(Paddle paddle, double nearClip, double farClip)
    {
        super(true);
        this.paddle = paddle;

        this.setNearClip(nearClip);
        this.setFarClip(farClip);

        translate = new Translate(0, 0, 0);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);

        this.getTransforms().addAll(
                rotateZ,
                translate,
                rotateY
        );

    }

    public abstract void moveCamera();

}
