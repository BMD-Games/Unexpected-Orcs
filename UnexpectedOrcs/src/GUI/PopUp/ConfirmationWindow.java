package GUI.PopUp;

import GUI.Button;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class ConfirmationWindow extends PopUpWindow{

    private Button yes, no;
    public ConfirmationCallback confirm, cancel;
    public Object[] callbackParams;

    public ConfirmationWindow(String title, String message, float x, float y, float w, float h, ConfirmationCallback confirm, ConfirmationCallback cancel, Object... callbackParams) {
        super(title, message, x, y, w, h);

        yes = new Button(x + w/3 - TILE_SIZE, y + h - TILE_SIZE * 3/2, "YES");
        no = new Button(x + w * 2/3 - TILE_SIZE, y + h - TILE_SIZE * 3/2, "NO");

       this.confirm = confirm;
       this.cancel = cancel;

       this.callbackParams = callbackParams;

    }

    public void setParams(Object[] params) {
        callbackParams = params;
    }

    public int show(PGraphics screen) {
        int val = super.show(screen);

        yes.show(screen);
        no.show(screen);

        if(yes.pressed() && mouseReleased) {
            return CONFIRM;
        }

        if(no.pressed() && mouseReleased) {
            return CANCEL;
        }

        return val;
    }

}
