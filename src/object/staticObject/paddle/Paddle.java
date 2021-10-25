package object.staticObject.paddle;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;
import object.movableObject.MovableObject;
import object.staticObject.StaticObject;
import player.Player;

public abstract class Paddle extends StaticObject {
	protected Translate translate;
	protected Player myPlayer;

	private void constructorHelper(double width, double height, double depth )
	{
		this.myPlayer = null;

		this.translate = new Translate ( );

		super.getTransforms ( ).addAll (
				this.translate
		);
	}
	
	public Paddle ( double width, double height, double depth ) {

		constructorHelper(width, height, depth);
	}

	public Paddle(double width, double height, double depth, Player player)
	{
		constructorHelper(width, height, depth);
		this.myPlayer = player;
	}

	public void setMyPlayer(Player player)
	{
		this.myPlayer = player;
	}

	public Player getMyPlayer()
	{
		return this.myPlayer;
	}
	
	@Override public boolean collision ( MovableObject movableObject ) {
		boolean collisionDetected = super.collision ( movableObject );
		
		if ( collisionDetected ) {
			movableObject.invertSpeedX ( );
			if(this.myPlayer != null) {
				Point3D movableObjectSpeed = movableObject.getSpeed();
				if(Math.abs(movableObjectSpeed.getX() * this.myPlayer.getEnergy()) < 1000
						&& Math.abs(movableObjectSpeed.getZ() * this.myPlayer.getEnergy()) < 1000){
					movableObjectSpeed = new Point3D(
							movableObjectSpeed.getX() * this.myPlayer.getEnergy(),
							0,
							movableObjectSpeed.getZ() * this.myPlayer.getEnergy()
					);
				}
				movableObject.setSpeed(movableObjectSpeed);
				System.out.println("ENERGY: " + myPlayer.getEnergy());
				System.out.println("SPEED: X = " + movableObjectSpeed.getX() + ", Y = " + movableObjectSpeed.getY() + ", Z = " + movableObjectSpeed.getZ());
			}
		}
		
		return collisionDetected;
	}
	
	public void move ( double dx, double dy, double dz ) {
		this.translate.setX ( this.translate.getX ( ) + dx );
		this.translate.setY ( this.translate.getY ( ) + dy );
		this.translate.setZ ( this.translate.getZ ( ) + dz );
	}
	
	public double getZ ( ) {
		return this.translate.getZ ( );
	}
}
