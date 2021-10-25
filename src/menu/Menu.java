package menu;

import javafx.scene.Group;

public abstract class Menu extends Group{

    protected int choice;

    public int SIZE = 150;
    public static final int UP = 1;
    public static final int DOWN = 2;
    protected Group root = new Group();

    public abstract void move(int direction);

    public abstract int getChoice();
}
