import javafx.animation.AnimationTimer;
import object.movableObject.MovableObject;
import object.staticObject.StaticObject;

import java.util.Arrays;
import java.util.List;

public class Timer extends AnimationTimer {
	private MovableObject      movableObject;
	private List<StaticObject> staticObjects;
	private long               previous;
	private static final int   NANOSECONDS_IN_SECOND = 1000000000;
	private double timePastForGameTimer;
	private GameTimer gameTimer;
	
	public Timer (MovableObject movableObject, StaticObject... staticObjects ) {
		this.movableObject = movableObject;
		this.staticObjects = Arrays.asList ( staticObjects );
		this.timePastForGameTimer = 0;
		this.gameTimer = null;
	}

	public void setGameTimer(GameTimer gameTimer)
	{
		this.gameTimer = gameTimer;
	}
	
	@Override public void handle ( long now ) {
		if ( this.previous == 0 ) {
			this.previous = now;
		}

		if(this.timePastForGameTimer == 0)
		{
			this.timePastForGameTimer = now;
		}
		
		double dt = ( now - this.previous ) / 1e9;
		this.previous = now;
		
		this.movableObject.update ( dt );
		
		this.staticObjects.forEach ( staticObject -> staticObject.collision ( this.movableObject ) );

		if(now - this.timePastForGameTimer >= 1e9)
		{
			this.timePastForGameTimer = now;
			if(this.gameTimer != null)
			{
				this.gameTimer.tick();
				if(this.gameTimer.isTimeout())
						this.gameTimer.notifyGameStopper();
			}

		}
	}

	public void setMovableObject(MovableObject movableObject)
	{
		this.movableObject = movableObject;
	}
}
