import cameras.BirdViewCamera;
import cameras.MainCamera;
import cameras.trackingCameras.LeftPaddleTrackingCamera;
import cameras.trackingCameras.PaddleTrackingCamera;
import cameras.trackingCameras.RightPaddleTrackingCamera;
import ground.Ground;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import menu.Menu;
import menu.MenuChoosePaddle;
import menu.MenuChoosePuck;
import object.movableObject.puck.*;
import object.staticObject.Goal;
import object.staticObject.paddle.BasicPaddle;
import object.staticObject.paddle.DefaultPaddle;
import object.staticObject.paddle.Paddle;
import object.staticObject.Wall;
import object.staticObject.paddle.RedPaddle;
import player.Player;
import reflector.TruncatedConeReflector;

public class Main extends Application implements Goal.GameStopper, EventHandler<KeyEvent> {
	
	private static final double WIDTH            = 800;
	private static final double HEIGHT           = 800;
	private static final double NEAR_CLIP        = 0.1;
	private static final double FAR_CLIP         = 5000;
	private static final double CAMERA_Z         = -2500;
	private static final double PUCK_RADIUS      = 20;
	private static final double PUCK_HEIGHT      = 20;
	private static final double LONG_WALL_WIDTH  = 1000;
	private static final double SHORT_WALL_WIDTH = 500;
	private static final double WALL_HEIGHT      = PUCK_HEIGHT;
	private static final double WALL_DEPTH       = 20;
	private static final double PADDLE_WIDTH     = 100;
	private static final double PADDLE_HEIGHT    = 100;
	private static final double PADDLE_DEPTH     = 20;
	private static final double STEP             = 10;
	private static final double GROUND_WIDTH	 = 1000;
	private static final double GROUND_HEIGHT	 = 500;
	private static final double REFLECTOR_r		 = 50;
	private static final double REFLECTOR_H		 = 100;
	private static final double REFLECTOR_R		 = 100;
	private static final int	GAME_DURATION_IN_SECONDS = 120;
	private              Timer  timer;
	private              Paddle leftPaddle, rightPaddle;
	private PerspectiveCamera camera;
	private PerspectiveCamera birdViewCamera;
	private PaddleTrackingCamera leftPaddleTrackingCamera;
	private PaddleTrackingCamera rightPaddleTrackingCamera;
	private SubScene scene;
	private Scene comprehensiveScene;
	private Group root;
	private Puck puck;
	private Player player1;
	private Player player2;
	private TruncatedConeReflector reflectorLeft;
	private TruncatedConeReflector reflectorRight;
	private GameTimer gameTimer;

	private int goalsForSpecialPuck;

	private Stage primaryStage;
	private Menu menuChoosePuck;
	private Menu menuChoosePaddle;

	private int puckChoice;
	private int paddleChoice;

	public static void main ( String[] arguments ) {
		launch ( arguments );
	}
	
	private void createCameras () {
		this.camera = new MainCamera(true, 0, 0, CAMERA_Z, -45, NEAR_CLIP, FAR_CLIP);
		this.birdViewCamera = new BirdViewCamera(true, 0, -2000, 0, -90, NEAR_CLIP, FAR_CLIP);
		this.leftPaddleTrackingCamera = new LeftPaddleTrackingCamera(leftPaddle, NEAR_CLIP, FAR_CLIP);
		this.rightPaddleTrackingCamera = new RightPaddleTrackingCamera(rightPaddle, NEAR_CLIP, FAR_CLIP);
	}

	private void createPlayers(Group root, Scene scene)
	{
		this.player1 = new Player();
		this.player2 = new Player();

		SubScene player1SubScene = this.player1.getSubscene();
		SubScene player2SubScene = this.player2.getSubscene();

		player1SubScene.getTransforms().addAll(new Translate(0, 0));
		player2SubScene.getTransforms().addAll(new Translate(WIDTH - player2SubScene.getWidth(), 0));

		root.getChildren().addAll(player1SubScene, player2SubScene);
	}
	
	private SubScene getScene ( ) {
		Group root = new Group ( );
		this.root = root;

		SubScene scene = new SubScene ( root, WIDTH, HEIGHT, true , SceneAntialiasing.BALANCED);

		Ground ground = new Ground(GROUND_WIDTH, GROUND_HEIGHT);
		ground.getTransforms().addAll(
				new Translate(-GROUND_WIDTH / 2, 10, GROUND_HEIGHT / 2),
				new Rotate(-90, Rotate.X_AXIS));
		root.getChildren().addAll(ground);

		this.reflectorLeft = new TruncatedConeReflector(REFLECTOR_r, REFLECTOR_H, REFLECTOR_R);
		reflectorLeft.getTransforms().addAll(
				new Translate(-LONG_WALL_WIDTH / 2 - 800, -500, 0),
				new Rotate(-45, Rotate.Z_AXIS)
		);

		this.reflectorRight = new TruncatedConeReflector(REFLECTOR_r, REFLECTOR_H, REFLECTOR_R);
		reflectorRight.getTransforms().addAll(
				new Translate(LONG_WALL_WIDTH / 2 + 800, -500, 0),
				new Rotate(45, Rotate.Z_AXIS));
		root.getChildren().addAll(reflectorLeft, reflectorRight);

		//Puck puck = new RadioactivePuck( PUCK_RADIUS, PUCK_HEIGHT );
		Puck puck = getNewPuckBasedOnChoice();
		root.getChildren ( ).addAll ( puck );
		this.puck = puck;
		goalsForSpecialPuck = (int)(1 + 7 * Math.random());
		System.out.println(goalsForSpecialPuck);

		Wall upperWall = new Wall ( LONG_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.UPPER );
		Wall lowerWall = new Wall ( LONG_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LOWER );
		Wall leftWall  = new Wall ( SHORT_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LEFT);
		Wall rightWall = new Wall ( SHORT_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.RIGHT);
		Goal goal1	   = new Goal(SHORT_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LEFT, this);
		Goal goal2	   = new Goal(SHORT_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.RIGHT, this);
		
		upperWall.getTransforms ( ).addAll (
				new Translate ( 0, 0, SHORT_WALL_WIDTH / 2 + WALL_DEPTH / 2 )
		);
		lowerWall.getTransforms ( ).addAll (
				new Translate ( 0, 0, -( SHORT_WALL_WIDTH / 2 + WALL_DEPTH / 2 ) )
		);
		leftWall.getTransforms ( ).addAll (
				new Translate ( -( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2 ), 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		rightWall.getTransforms ( ).addAll (
				new Translate ( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2, 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		goal1.getTransforms().addAll(
				new Translate ( -( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2 ), 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		goal2.getTransforms ( ).addAll (
				new Translate ( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2, 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		
		root.getChildren ( ).addAll ( upperWall, lowerWall, leftWall, rightWall, goal1, goal2 );
		
		setPaddlesBasedOnChoice();
		
		root.getChildren ( ).addAll ( this.leftPaddle, this.rightPaddle );

		//----------------------TEST----------------------
//		RedPaddle testPaddle = new RedPaddle(PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_WIDTH);
//		testPaddle.getTransforms().addAll(
//				//new Translate(0, -100, 0)
//		);
//		root.getChildren().addAll(testPaddle);
		
		this.createCameras ();
		scene.setCamera ( this.camera );
		
		this.timer = new Timer ( puck, upperWall, lowerWall, leftWall, rightWall, this.rightPaddle, this.leftPaddle, goal1, goal2);
		this.timer.start ( );

		scene.setFill(Color.BLACK);

		return scene;
	}

	private Scene getComprehensiveScene()
	{
		Group root = new Group();

		this.scene = getScene();
		root.getChildren().addAll(this.scene);

		Scene scene = new Scene(root, WIDTH , HEIGHT, true , SceneAntialiasing.BALANCED);

		scene.addEventHandler ( KeyEvent.KEY_PRESSED, this );
		scene.addEventHandler(ScrollEvent.ANY, (EventHandler<ScrollEvent>) this.camera);
		scene.addEventHandler(MouseEvent.ANY, (EventHandler<MouseEvent>) this.camera);

		createPlayers(root, scene);

		this.leftPaddle.setMyPlayer(this.player1);
		this.rightPaddle.setMyPlayer(this.player2);

		this.gameTimer = new GameTimer(GAME_DURATION_IN_SECONDS, this);
		this.gameTimer.getTransforms().add(new Translate(WIDTH / 2 - this.gameTimer.getLayoutBounds().getMaxX() / 2, 50));
		this.timer.setGameTimer(this.gameTimer);
		root.getChildren().add(this.gameTimer);

		return scene;
	}

	private Scene getPuckChooseScene()
	{
		this.menuChoosePuck = new MenuChoosePuck();

		Group rootMenuChoosePuck = new Group();
		rootMenuChoosePuck.getChildren().add(this.menuChoosePuck);
		Scene menuScene = new Scene(rootMenuChoosePuck, WIDTH, HEIGHT);
		menuScene.setOnKeyPressed(e -> onKeyPressedSceneChoosePuck(e));

		return menuScene;
	}

	private void onKeyPressedSceneChoosePuck(KeyEvent e)
	{
		switch (e.getCode())
		{
			case UP:
				this.menuChoosePuck.move(Menu.UP);
				break;
			case DOWN:
				this.menuChoosePuck.move(Menu.DOWN);
				break;
			case ENTER:
				this.puckChoice = this.menuChoosePuck.getChoice();
				this.primaryStage.setScene(null);
				Scene sceneChoosePaddle = getPaddleChooseScene();
				this.primaryStage.setScene(sceneChoosePaddle);
				break;
		}
	}

	private Scene getPaddleChooseScene()
	{
		this.menuChoosePaddle = new MenuChoosePaddle();
		Group rootPaddleChooseScene = new Group();
		rootPaddleChooseScene.getChildren().add(this.menuChoosePaddle);
		Scene paddleChooseScene = new Scene(rootPaddleChooseScene, WIDTH, HEIGHT);
		paddleChooseScene.setOnKeyPressed(e -> onKeyPressedSceneChoosePaddle(e));
		return paddleChooseScene;
	}

	private void onKeyPressedSceneChoosePaddle(KeyEvent e)
	{
		switch (e.getCode())
		{
			case UP:
				this.menuChoosePaddle.move(Menu.UP);
				break;
			case DOWN:
				this.menuChoosePaddle.move(Menu.DOWN);
				break;
			case ENTER:
				this.paddleChoice = this.menuChoosePaddle.getChoice();
				this.primaryStage.setScene(null);
				Scene comprehensiveScene = getComprehensiveScene();
				this.primaryStage.setScene(comprehensiveScene);
		}
	}

	
	@Override public void start ( Stage primaryStage ) throws Exception {
		//this.comprehensiveScene = this.getComprehensiveScene();
		this.primaryStage = primaryStage;

		this.comprehensiveScene = this.getPuckChooseScene();

		primaryStage.setScene ( this.comprehensiveScene );
		primaryStage.setTitle ( "Vazdusni hokej" );
		primaryStage.show ( );
	}

	
	@Override public void stopGame (Wall.Type wallType) {

		// gameTimer has stopped the game
		if(wallType == null) {
			if (this.gameTimer.isTimeout()) {
				this.timer.stop();
				showEndGameScene(null);
				root.getChildren().remove(this.puck);
				return;
			}
		}

		switch (wallType)
		{
			case LEFT:
				for(int i = 0; i < puck.destroyLives(); i++)
					player1.decreaseLives();
				if(player1.getLives() == 0) {
					this.timer.stop();
					showEndGameScene(player1);
					return;
				}
				player2.setEnergyToMax();
				player1.setEnergyToMin();
				break;

			case RIGHT:
				for(int i = 0; i < puck.destroyLives(); i++)
					player2.decreaseLives();
				if(player2.getLives() == 0)
				{
					this.timer.stop();
					showEndGameScene(player2);
					return;
				}
				player2.setEnergyToMin();
				player1.setEnergyToMax();
				break;

		}

		root.getChildren().remove(this.puck);

		goalsForSpecialPuck--;
		if(goalsForSpecialPuck == 0)
			this.puck = new SpecialPuck(PUCK_RADIUS, PUCK_HEIGHT);
		else
			this.puck = getNewPuckBasedOnChoice();
			//this.puck = new DefaultPuck ( PUCK_RADIUS, PUCK_HEIGHT );
		root.getChildren ( ).addAll ( puck );
		timer.setMovableObject(this.puck);
	}

	private void showEndGameScene(Player loser)
	{
		Group root = (Group)this.comprehensiveScene.getRoot();
		Text text = null;
		if(loser == this.player1)
			text = new Text("Right wins!");
		else
			if(loser == this.player2)
				text = new Text("Left wins!");
			else if(loser == null)
				text = new Text("DRAW!");
		text.setFont(Font.font(50));
		text.setFill(Color.RED);

		root.getChildren().setAll(text);

		ParallelCamera cameraEndGameScene = new ParallelCamera();
		cameraEndGameScene.getTransforms().add(new Translate(-WIDTH / 2 + text.getBoundsInLocal().getMaxX() / 2, -HEIGHT / 2, 0));
		this.comprehensiveScene.setCamera(cameraEndGameScene);
	}
	
	@Override public void handle ( KeyEvent event ) {
		if ( event.getCode ( ).equals ( KeyCode.W ) ) {
			double newZ = this.leftPaddle.getZ ( ) + STEP;
			
			if ( ( newZ + PADDLE_WIDTH / 2 ) <= SHORT_WALL_WIDTH / 2 ) {
				this.leftPaddle.move ( 0, 0, STEP );
				if(this.scene.getCamera().equals(leftPaddleTrackingCamera))
					leftPaddleTrackingCamera.moveCamera();
			}
		} else if ( event.getCode ( ).equals ( KeyCode.S ) ) {
			double newZ = this.leftPaddle.getZ ( ) - STEP;
			
			if ( ( newZ - PADDLE_WIDTH / 2 ) >= -SHORT_WALL_WIDTH / 2 ) {
				this.leftPaddle.move ( 0, 0, -STEP );
				if(this.scene.getCamera().equals(leftPaddleTrackingCamera))
					leftPaddleTrackingCamera.moveCamera();
			}
		} else if ( event.getCode ( ).equals ( KeyCode.UP ) ) {
			double newZ = this.rightPaddle.getZ ( ) + STEP;
			
			if ( ( newZ + PADDLE_WIDTH / 2 ) <= SHORT_WALL_WIDTH / 2 ) {
				this.rightPaddle.move ( 0, 0, STEP );
				if(this.scene.getCamera().equals(rightPaddleTrackingCamera))
					rightPaddleTrackingCamera.moveCamera();
			}
		} else if ( event.getCode ( ).equals ( KeyCode.DOWN ) ) {
			double newZ = this.rightPaddle.getZ ( ) - STEP;
			
			if ( ( newZ - PADDLE_WIDTH / 2 ) >= -SHORT_WALL_WIDTH / 2 ) {
				this.rightPaddle.move ( 0, 0, -STEP );
				if(this.scene.getCamera().equals(rightPaddleTrackingCamera))
					rightPaddleTrackingCamera.moveCamera();
			}
		} else if(event.getCode().equals(KeyCode.NUMPAD1) || event.getCode().equals(KeyCode.DIGIT1))
		{
			if(this.scene.getCamera() != camera)
				this.scene.setCamera(camera);
		} else if(event.getCode().equals(KeyCode.NUMPAD2) || event.getCode().equals(KeyCode.DIGIT2))
		{
			if(this.scene.getCamera() != birdViewCamera)
				this.scene.setCamera(birdViewCamera);
		} else if(event.getCode().equals(KeyCode.NUMPAD3) || event.getCode().equals(KeyCode.DIGIT3))
		{
			if(this.scene.getCamera() != leftPaddleTrackingCamera) {
				this.scene.setCamera(leftPaddleTrackingCamera);
				leftPaddleTrackingCamera.moveCamera();
			}
		} else if(event.getCode().equals(KeyCode.NUMPAD4) || event.getCode().equals(KeyCode.DIGIT4))
		{
			if(this.scene.getCamera() != rightPaddleTrackingCamera) {
				this.scene.setCamera(rightPaddleTrackingCamera);
				rightPaddleTrackingCamera.moveCamera();
			}
		} else if(event.getCode().equals(KeyCode.NUMPAD9) || event.getCode().equals(KeyCode.DIGIT9))
		{
			reflectorLeft.switchLight();
		} else if(event.getCode().equals(KeyCode.NUMPAD0) || event.getCode().equals(KeyCode.DIGIT0))
		{
			reflectorRight.switchLight();
		}

	}

	private Puck getNewPuckBasedOnChoice()
	{
		switch(this.puckChoice)
		{
			case 1:
				return new DefaultPuck(PUCK_RADIUS, PUCK_HEIGHT);
			case 2:
				return new HexagonPuck(PUCK_RADIUS, PUCK_HEIGHT);
			case 3:
				return new RadioactivePuck(PUCK_RADIUS, PUCK_HEIGHT);
			default:
				return new DefaultPuck(PUCK_RADIUS, PUCK_HEIGHT);
		}
	}

	private void setPaddlesBasedOnChoice()
	{
		switch (this.paddleChoice)
		{
			case 1:
			default:
			{
				this.leftPaddle = new DefaultPaddle( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.rightPaddle = new DefaultPaddle ( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.leftPaddle.getTransforms ( ).addAll (
						new Translate ( -LONG_WALL_WIDTH / 2 * 0.8, -15, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
				this.rightPaddle.getTransforms ( ).addAll (
						new Translate ( LONG_WALL_WIDTH / 2 * 0.8, -15, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
			}
			break;
			case 2: {
				this.leftPaddle = new BasicPaddle( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.rightPaddle = new BasicPaddle ( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.leftPaddle.getTransforms ( ).addAll (
						new Translate ( -LONG_WALL_WIDTH / 2 * 0.8, -15, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
				this.rightPaddle.getTransforms ( ).addAll (
						new Translate ( LONG_WALL_WIDTH / 2 * 0.8, -15, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
			}
			break;
			case 3:
			{
				this.leftPaddle = new RedPaddle( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.rightPaddle = new RedPaddle ( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
				this.leftPaddle.getTransforms ( ).addAll (
						new Translate ( -LONG_WALL_WIDTH / 2 * 0.8, 0, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
				this.rightPaddle.getTransforms ( ).addAll (
						new Translate ( LONG_WALL_WIDTH / 2 * 0.8, 0, 0 ),
						new Rotate ( 90, Rotate.Y_AXIS )
				);
			}
			break;
		}
	}
}
