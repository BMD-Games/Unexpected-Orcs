package GUI.PopUp;

import processing.core.PGraphics;

import File.GameFile.*;
import static Utility.Constants.*;

public class ConfirmDelete extends ConfirmationWindow {


    public ConfirmDelete(String title, String message, float x, float y, float w, float h, ConfirmationCallback confirm, ConfirmationCallback cancel, Object[] callbackParams) {
        super(title, message, x, y, w, h, confirm, cancel, callbackParams);
    }

    public void display(PGraphics screen) {
        int val = super.show(screen);

        if(val == OPEN) {
            return;
        } else if(val == CONFIRM) {
            //if(callbackParams == null) return;
            confirm.callback(callbackParams);
        } else if(val == CANCEL) {
            cancel.callback(callbackParams);
        }
    }
}
