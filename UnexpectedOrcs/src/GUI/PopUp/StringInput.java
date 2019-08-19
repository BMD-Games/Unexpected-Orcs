package GUI.PopUp;

import Entities.Player;
import File.GameFile;
import GUI.Button;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class StringInput extends PopUpWindow {

    private Button yes, no;
    public ConfirmationCallback confirm;

    private String inputString = "";

    public StringInput(String title, String message, float x, float y, float w, float h, ConfirmationCallback confirm) {
        super(title, message, x, y, w, h);

        yes = new Button(x + w/3 - TILE_SIZE, y + h - TILE_SIZE * 3/2, "YES");
        no = new Button(x + w * 2/3 - TILE_SIZE, y + h - TILE_SIZE * 3/2, "NO");

        this.confirm = confirm;

    }

    public int show(PGraphics screen) {
        int val = super.show(screen);

        yes.show(screen);
        no.show(screen);

        screen.fill(200);
        screen.noStroke();
        screen.rect(x + w/2 - TILE_SIZE * 2, y + h/2 - TILE_SIZE/4 * 5, TILE_SIZE * 4, TILE_SIZE);

        screen.fill(50);
        screen.noStroke();
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text(inputString, x + w/2, y + h/2 - (TILE_SIZE/8 * 7));

        if(game.frameCount % 60 < 30) {
            screen.rect(x + w/2 + game.textWidth(inputString)/2 + gui.buff/2, y + h/2 - (TILE_SIZE/6 * 7), gui.buff, TILE_SIZE/3 * 2);
        }

        if(yes.pressed() && mouseReleased) {
            confirm.callback(inputString);
            return CONFIRM;
        }

        if(no.pressed() && mouseReleased) {
            return CANCEL;
        }

        return val;
    }

    public void handleKeyInput(char key) {
        if (Character.isLetterOrDigit(key) && inputString.length() < 10) {
            inputString = inputString + key;
        } else if (key == game.BACKSPACE && inputString.length() > 0) {
            inputString = inputString.substring(0, inputString.length() - 1);
        }
    }

}
