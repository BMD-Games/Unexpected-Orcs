package GUI.Scroll;

import GUI.Button;
import javafx.util.Pair;
import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Constants.TILE_SIZE;
import static Utility.Constants.game;

public class SelectElement extends ScrollElement {

    private String[] names;
    private Object[] parameters;
    private SelectCallback callback;

    private int currentSelection = 0;
    private int currentView = 0;

    private Button left = new Button(0, 0, "LEFT");
    private Button right = new Button(0, 0, "RIGHT");
    private Button select = new Button(0, 0, "BLANK_1x1");

    public SelectElement(String title, String[] names, Object[] parameters, SelectCallback callback) {
        super(title, "", TILE_SIZE * 2);

        this.names = names;
        this.parameters = parameters;
        this.callback = callback;
    }

    public SelectElement(String title, String[] names, Object[] parameters, SelectCallback callback, int currentSelection) {
        super(title, "", TILE_SIZE * 2);

        this.names = names;
        this.parameters = parameters;
        this.callback = callback;
        this.currentSelection = currentSelection;
        this.currentView = currentSelection;
    }

    @Override
    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);

        screen.fill(88);
        screen.noStroke();
        screen.rect(x + w/2 - TILE_SIZE * 2, y + h/2 - TILE_SIZE/2, TILE_SIZE * 4, TILE_SIZE);

        screen.fill(250);
        screen.textSize(TILE_SIZE/2);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text(names[currentView], xpos + w/2, ypos + h/2);

        left.x = xpos + w/2 - TILE_SIZE * 3 - TILE_SIZE/4;
        left.y = ypos + h/2 - TILE_SIZE/4;
        left.show(screen);

        right.x = xpos + w/2 + TILE_SIZE * 3;
        right.y = ypos + h/2 - TILE_SIZE/4;
        right.show(screen);

        select.x = xpos + w - TILE_SIZE * 3/2;
        select.y = ypos + h/2 - TILE_SIZE/2;
        select.show(screen);

        if(currentView == currentSelection) {
            screen.image(guiSprites.get("TICK"), xpos + w - TILE_SIZE * 3/2, ypos + h/2 - TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        }
    }

    public void handleMouse() {
        if(select.pressed()) {
            currentSelection = currentView;
            callback.callback(parameters[currentSelection]);

        } else if(left.pressed()) {
            currentView = game.constrain(currentView - 1, 0, names.length - 1);
        } else if(right.pressed()) {
            currentView = game.constrain(currentView + 1, 0, names.length - 1);
        }
    }
}
