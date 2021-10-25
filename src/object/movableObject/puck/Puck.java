package object.movableObject.puck;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;
import object.movableObject.MovableObject;

public abstract class Puck extends MovableObject {
	
	private static final double SPEED_X_MIN = 120;
	private static final double SPEED_X_MAX = 150;
	private static final double SPEED_Z_MIN = 60;
	private static final double SPEED_Z_MAZ = 90;
	
	public Puck ( double radius, double height ) {
		super ( new Translate ( 0, 0, 0 ), Puck.getRandomSpeed ( ) );
	}
	
	private static Point3D getRandomSpeed ( ) {
		double x = Math.random ( ) * ( Puck.SPEED_X_MAX - Puck.SPEED_X_MIN ) + SPEED_X_MIN;
		double z = Math.random ( ) * ( Puck.SPEED_Z_MAZ - Puck.SPEED_Z_MIN ) + SPEED_Z_MIN;
		return new Point3D ( x, 0, z );
	}

	public int destroyLives()
	{
		return 1;
	}
}
