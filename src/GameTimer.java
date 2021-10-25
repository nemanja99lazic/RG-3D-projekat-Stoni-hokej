import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import object.staticObject.Goal;

public class GameTimer extends Group {

    private int minutes;
    private int seconds;
    private boolean timeout;
    private Text text;
    private Goal.GameStopper gameStopper;

    public GameTimer(int seconds, Goal.GameStopper gameStopper)
    {
        this.gameStopper = gameStopper;

        this.seconds = seconds % 60;
        this.minutes = seconds / 60;

        this.text = new Text();

        String formatedMinutes = this.minutes < 10 ? "0" + this.minutes : String.valueOf(this.minutes);
        String formatedSeconds = this.seconds < 10 ? "0" + this.seconds : String.valueOf(this.seconds);
        this.text.setText(formatedMinutes + ":" + formatedSeconds);

        this.text.setFill(Color.GREEN);
        this.text.setFont(Font.font(50));
        this.getChildren().add(this.text);

        if(this.seconds != 0 || this.minutes != 0)
            this.timeout = false;
        else
            this.timeout = true;
    }

    public void notifyGameStopper()
    {
        this.gameStopper.stopGame(null);
    }

    public void tick()
    {
        if(this.seconds != 0)
            this.seconds--;
        else
            if(this.minutes != 0)
            {
                this.minutes--;
                this.seconds = 60;
            }
            else
                this.timeout = true;

        String formatedMinutes = this.minutes < 10 ? "0" + this.minutes : String.valueOf(this.minutes);
        String formatedSeconds = this.seconds < 10 ? "0" + this.seconds : String.valueOf(this.seconds);
        this.text.setText(formatedMinutes + ":" + formatedSeconds);
    }

    public boolean isTimeout()
    {
        return this.timeout;
    }
}
