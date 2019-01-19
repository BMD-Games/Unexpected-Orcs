package GUI.Screens;

import GUI.Button;
import processing.core.PGraphics;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class OptionsScreen extends GUIScreen {

    private static Button keyUp = new Button(width/2, height/2 - TILE_SIZE * 4, "BLANK_1x1");
    private static Button keyDown = new Button(width/2, height/2 - TILE_SIZE * 3, "BLANK_1x1");
    private static Button keyLeft = new Button(width/2, height/2 - TILE_SIZE * 2, "BLANK_1x1");
    private static Button keyRight = new Button(width/2, height/2 - TILE_SIZE * 1, "BLANK_1x1");
    private static Button keyAbility = new Button(width/2, height/2 - TILE_SIZE * 0, "BLANK_1x1");

    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");

    public static void show(PGraphics screen) {
        //Draws the options menu
        screen.beginDraw();
        screen.textAlign(game.CENTER, game.CENTER);
        screen.textSize(TILE_SIZE/2);

        //background(screen);
        screen.background(50);
        //show buttons
        back.show(screen);
        keyUp.show(screen);
        keyDown.show(screen);
        keyLeft.show(screen);
        keyRight.show(screen);
        keyAbility.show(screen);

        screen.fill(0, 112, 188);
        //display relevant key on remap buttons
        if (!remapNextKey || remapAction != up) screen.text(getKeyString(up), keyUp.x + keyUp.w/2, keyUp.y + keyUp.h/2);
        if (!remapNextKey || remapAction != down) screen.text(getKeyString(down), keyDown.x + keyDown.w/2, keyDown.y + keyDown.h/2);
        if (!remapNextKey || remapAction != left) screen.text(getKeyString(left), keyLeft.x + keyLeft.w/2, keyLeft.y + keyLeft.h/2);
        if (!remapNextKey || remapAction != right) screen.text(getKeyString(right), keyRight.x + keyRight.w/2, keyRight.y + keyRight.h/2);
        if (!remapNextKey || remapAction != ability) screen.text(getKeyString(ability), keyAbility.x + keyAbility.w/2, keyAbility.y + keyAbility.h/2);
        screen.fill(255);
        screen.textAlign(game.RIGHT, game.CENTER);
        screen.text("Forward", keyUp.x, keyUp.y + keyUp.h/2);
        screen.text("Back", keyDown.x, keyDown.y + keyDown.h/2);
        screen.text("Left", keyLeft.x, keyLeft.y + keyLeft.h/2);
        screen.text("Right", keyRight.x, keyRight.y + keyRight.h/2);
        screen.text("Ability", keyAbility.x, keyAbility.y + keyAbility.h/2);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.revertState();
        } else if (keyUp.pressed()) {
            remapNextKey = true;
            remapAction = up;
        } else if (keyDown.pressed()) {
            remapNextKey = true;
            remapAction = down;
        } else if (keyLeft.pressed()) {
            remapNextKey = true;
            remapAction = left;
        } else if (keyRight.pressed()) {
            remapNextKey = true;
            remapAction = right;
        } else if (keyAbility.pressed()) {
            remapNextKey = true;
            remapAction = ability;
        }
    }
}