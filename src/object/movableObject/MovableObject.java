package object.movableObject;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.transform.Translate;

public abstract class MovableObject extends Group {
	private Translate position;
	private Point3D   speed;

	private static final double FRICTION_FACTOR = 0.01;
	private static final double GRAVITATION = 9.81;

	public MovableObject ( Translate position, Point3D speed ) {
		this.position = position;
		this.speed = speed;

		super.getTransforms ( ).addAll (
				this.position
		);
	}

	public void setSpeed(Point3D speed)
	{
		this.speed = speed;
	}

	public Point3D getSpeed()
	{
		return this.speed;
	}
	
	public void update ( double dt ) {

		double newX = position.getX ( ) + speed.getX ( ) * dt;
		double newY = position.getY ( ) + speed.getY ( ) * dt;
		double newZ = position.getZ ( ) + speed.getZ ( ) * dt;

		speed = new Point3D(speed.getX() - Math.signum(speed.getX()) * dt * FRICTION_FACTOR * GRAVITATION,
				speed.getY(),
				speed.getZ() - Math.signum(speed.getZ()) * dt * FRICTION_FACTOR * GRAVITATION
		);
		
		this.position.setX ( newX );
		this.position.setY ( newY );
		this.position.setZ ( newZ );
	}
	
	public void invertSpeedX ( ) {
		this.speed = new Point3D ( -this.speed.getX ( ), this.speed.getY ( ), this.speed.getZ ( ) );
	}
	
	public void invertSpeedZ ( ) {
		this.speed = new Point3D ( this.speed.getX ( ), this.speed.getY ( ), -this.speed.getZ ( ) );
	}
}
