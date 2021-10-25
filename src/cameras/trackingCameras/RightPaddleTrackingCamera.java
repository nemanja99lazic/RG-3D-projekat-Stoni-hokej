package cameras.trackingCameras;

import object.staticObject.paddle.Paddle;

public class RightPaddleTrackingCamera extends PaddleTrackingCamera {

    public RightPaddleTrackingCamera(Paddle paddle, double nearClip, double farClip)
    {
        super(paddle, nearClip, farClip);
        this.rotateY.setAngle(-90);
        this.rotateZ.setAngle(-30);
        this.moveCamera();
    }

    public void moveCamera()
    {
        this.translate.setX(paddle.getBoundsInParent().getMaxX() + 700);
        this.translate.setY(paddle.getBoundsInParent().getMinY() + 100);
        this.translate.setZ((paddle.getBoundsInParent().getMinZ() + paddle.getBoundsInParent().getMaxZ()) / 2);
    }

}
