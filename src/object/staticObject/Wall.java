package object.staticObject;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import object.movableObject.MovableObject;
import object.staticObject.wallMaterial.WallMaterial;

import java.sql.Time;

public class Wall extends StaticObject {
	private Type        type;
	private Timeline	shakingWallTimeline;
	
	public Wall ( double width, double height, double depth, Type type) {
		switch (type) {
			case UPPER:
			case LOWER: {
				Box wall = new Box(width, height, depth);
				wall.setMaterial(new WallMaterial());
				super.getChildren().addAll(wall);

				break;
			}
			case RIGHT:
			case LEFT: {
				Box wallPart1 = new Box(0.25 * width, height, depth);
				wallPart1.setMaterial(new WallMaterial());
				wallPart1.getTransforms().add(new Translate(3 * width / 8, 0, 0));

				Box wallPart2 = new Box(0.25 * width, height, depth);
				wallPart2.setMaterial(new WallMaterial());
				wallPart2.getTransforms().add(new Translate( - 3 * width / 8, 0, 0));

				super.getChildren().addAll(wallPart1, wallPart2);
			}
		}

		this.type = type;

		createShakingWallTimeline();
	}

	private void createShakingWallTimeline()
	{
		this.shakingWallTimeline = new Timeline();

		Duration d0 = Duration.ZERO;
		Duration d1 = Duration.seconds(0.1);
		Duration d2 = Duration.seconds(0.2);

		KeyValue keyValueStart = null, keyValueMoved = null, keyValueReturnToStart = null;

		switch(this.type)
		{
			case LEFT:
				keyValueStart = new KeyValue(super.translateXProperty(), super.getTranslateX());
				keyValueMoved = new KeyValue(super.translateXProperty(), super.getTranslateX() - 5);
				keyValueReturnToStart = new KeyValue(super.translateXProperty(), super.getTranslateX() + 5);

				break;
			case RIGHT:
				keyValueStart = new KeyValue(super.translateXProperty(), super.getTranslateX());
				keyValueMoved = new KeyValue(super.translateXProperty(), super.getTranslateX() + 5);
				keyValueReturnToStart = new KeyValue(super.translateXProperty(), super.getTranslateX() - 5);

				break;
			case LOWER:
				keyValueStart = new KeyValue(super.translateZProperty(), super.getTranslateZ());
				keyValueMoved = new KeyValue(super.translateZProperty(), super.getTranslateZ() - 5);
				keyValueReturnToStart = new KeyValue(super.translateZProperty(), super.getTranslateZ() + 5);

				break;
			case UPPER:
				keyValueStart = new KeyValue(super.translateZProperty(), super.getTranslateZ());
				keyValueMoved = new KeyValue(super.translateZProperty(), super.getTranslateZ() + 5);
				keyValueReturnToStart = new KeyValue(super.translateZProperty(), super.getTranslateZ() - 5);

				break;
		}

		KeyFrame frame1 = new KeyFrame(d0, keyValueStart);
		KeyFrame frame2 = new KeyFrame(d1, keyValueMoved);
		KeyFrame frame3 = new KeyFrame(d2, keyValueReturnToStart);

		this.shakingWallTimeline.getKeyFrames().addAll(frame1, frame2, frame3);

	}
	
	@Override public boolean collision ( MovableObject movableObject ) {
		boolean collisionDetected = super.collision ( movableObject );

		if ( collisionDetected ) {
			switch ( type ) {
				case UPPER:
				case LOWER: {
					movableObject.invertSpeedZ ( );
					System.out.println("TIME = " + this.shakingWallTimeline.getCurrentTime().toMillis());
					if(this.shakingWallTimeline.getCurrentTime().toMillis() <= 0.0 || this.shakingWallTimeline.getCurrentTime().toMillis() >= 200.0) {
						this.shakingWallTimeline.stop();
						this.shakingWallTimeline.playFromStart();
					}
					else
						movableObject.invertSpeedZ ( ); // ako animacija traje, ponovo obrni Z
					break;
				}
				case LEFT:
				case RIGHT: {
					movableObject.invertSpeedX();
					break;
				}
			}

		}
		
		return collisionDetected;
	}
	
	public enum Type {
		UPPER, LOWER, LEFT, RIGHT
	}

}