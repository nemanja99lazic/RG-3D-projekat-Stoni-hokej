package cameras.trackingCameras;

import object.staticObject.paddle.Paddle;

public class LeftPaddleTrackingCamera extends PaddleTrackingCamera{

    public LeftPaddleTrackingCamera(Paddle paddle, double nearClip, double farClip)
    {
        super(paddle, nearClip, farClip);
        this.rotateY.setAngle(90);
        this.rotateZ.setAngle(30);
        this.moveCamera();
    }

    public void moveCamera()
    {
        this.translate.setX(paddle.getBoundsInParent().getMinX() - 700);
        this.translate.setY(paddle.getBoundsInParent().getMinY() + 100);
        this.translate.setZ((paddle.getBoundsInParent().getMinZ() + paddle.getBoundsInParent().getMaxZ()) / 2);

        System.out.println("X = " + this.getBoundsInParent().getMinX() +
                            " Y = " + this.getBoundsInParent().getMinY() +
                            " Z = " + this.getBoundsInParent().getMinZ());
    }

}
