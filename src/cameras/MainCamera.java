package cameras;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class MainCamera extends PerspectiveCamera implements EventHandler<Event>{

    private Rotate rotateX;
    private Rotate rotateY;
    private Translate translate;

    private double x, y;

    public MainCamera(boolean fixedEyeAtZero, double x, double y, double z, double rotateXAngle, double nearClip, double farClip)
    {
        super(fixedEyeAtZero);

        this.setNearClip ( nearClip );
		this.setFarClip ( farClip );

		rotateX = new Rotate( rotateXAngle, Rotate.X_AXIS );
		rotateY = new Rotate(0, Rotate.Y_AXIS);
		translate = new Translate( x, y, z );

		this.getTransforms ( ).addAll (
				rotateY,
				rotateX,
				translate
		);
    }

    private void handleMouseEvent(MouseEvent event)
    {
        if(event.isPrimaryButtonDown())
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED))
            {
                this.x = event.getSceneX ( );
                this.y = event.getSceneY ( );
            }
            if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            {
                double dx = event.getSceneX ( ) - this.x;
                double dy = event.getSceneY ( ) - this.y;

                this.x = event.getSceneX();
                this.y = event.getSceneY();

                this.rotateY.setAngle ( this.rotateY.getAngle ( ) + dx * 0.1 );
                if((this.rotateX.getAngle ( ) - dy * 0.1) % 360 < 0 && (this.rotateX.getAngle ( ) - dy * 0.1) % 360 > -90)
                    this.rotateX.setAngle ( this.rotateX.getAngle ( ) - dy * 0.1 );
            }
        }
    }

    private void handleScrollEvent(ScrollEvent event)
    {
        final double STEP = 50;
        double y = event.getDeltaY ( );

        double step = STEP * ( y > 0 ? 1 : -1 );
        this.translate.setZ( this.translate.getZ ( ) + step );
    }

    @Override
    public void handle(Event event) {
        if(event instanceof MouseEvent)
            handleMouseEvent((MouseEvent)event);
        if(event instanceof ScrollEvent)
            handleScrollEvent((ScrollEvent)event);
    }
}
