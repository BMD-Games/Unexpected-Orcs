package GUI;

import GUI.Screens.*;
import processing.core.PGraphics;
import processing.core.PImage;

import static Utility.Constants.*;

public class GUI {
    /**
     This class is used for drawing and handeling all UI related screens and elements
     **/

    private PImage title = game.loadImage("/assets/sprites/title.png");
    public PGraphics screen;

    public boolean keyInput = false;

    public static int buff = (int)(SCALE * 3/2f); //for mouseOver stuff;

    public GUI() {
        screen = game.createGraphics(game.width, game.height);
        screen.beginDraw();
        screen.noSmooth();
        screen.textFont(bitcell);
        screen.endDraw();
        buff = (int)(SCALE * 3/2f); //for mouseOver stuff;

//        refresh();

    }


    public void show() {
        if(game.STATE.equals("MENU")) {
            MenuScreen.show(screen);
        } else if(game.STATE.equals("OPTIONS")) {
            OptionsScreen.show(screen);
        } else if(game.STATE.equals("PLAYING")) {
            PlayScreen.show(screen);
        } else if(game.STATE.equals("PAUSED")) {
            PausedScreen.show(screen);
        } else if(game.STATE.equals("DEAD")) {
            DeadScreen.show(screen);
        } else if(game.STATE.equals("NEWGAME")) {
            NewGameScreen.show(screen);
        } else if(game.STATE.equals("LOAD")) {
            LoadScreen.show(screen);
        } else if(game.STATE.equals("CREDITS")) {
            CreditsScreen.show(screen);
        } else if(game.STATE.equals("TEST")) {
            TestScreen.show(screen);
        }
        else {
            game.setState("MENU");
        }
    }

    public void handleKeyInput(char key) {
        if(game.STATE.equals("MENU")) {
            MenuScreen.handleKeyInput(key);
        } else if(game.STATE.equals("OPTIONS")) {
            OptionsScreen.handleKeyInput(key);
        } else if(game.STATE.equals("PAUSED")) {
            PausedScreen.handleKeyInput(key);
        } else if(game.STATE.equals("PLAYING")) {
            PlayScreen.handleKeyInput(key);
        } else if(game.STATE.equals("DEAD")) {
            DeadScreen.handleKeyInput(key);
        } else if(game.STATE.equals("LOAD")) {
            LoadScreen.handleKeyInput(key);
        } else if(game.STATE.equals("NEWGAME")) {
            NewGameScreen.handleKeyInput(key);
        } else if(game.STATE.equals("CREDITS")) {
            CreditsScreen.handleKeyInput(key);
        }

        else if(game.STATE.equals("TEST")) {
            TestScreen.handleMouseReleased();
        }
    }

    public void handleMouseReleased() {
        //------Main Buttons
        if(game.STATE.equals("MENU")) {
            MenuScreen.handleMouseReleased();
        } else if(game.STATE.equals("OPTIONS")) {
            OptionsScreen.handleMouseReleased();
        } else if(game.STATE.equals("PAUSED")) {
            PausedScreen.handleMouseReleased();
        } else if(game.STATE.equals("PLAYING")) {
            PlayScreen.handleMouseReleased();
        } else if(game.STATE.equals("DEAD")) {
            DeadScreen.handleMouseReleased();
        } else if(game.STATE.equals("LOAD")) {
           LoadScreen.handleMouseReleased();
        } else if(game.STATE.equals("NEWGAME")) {
            NewGameScreen.handleMouseReleased();
        } else if(game.STATE.equals("CREDITS")) {
            CreditsScreen.handleMouseReleased();
        }

        else if(game.STATE.equals("TEST")) {
            TestScreen.handleMouseReleased();
        }
    }

    public void drawMouseOverSprite(float x, float y, PImage sprite) {
        screen.pushMatrix();

        int mouseOverSize = TILE_SIZE + buff * 4;

        if(x + mouseOverSize > screen.width) x = screen.width - mouseOverSize;
        if(y + mouseOverSize > screen.height) y = screen.height - mouseOverSize;

        screen.fill(100);
        screen.rect(x, y, mouseOverSize, mouseOverSize);
        screen.noFill();
        screen.stroke(130);
        screen.rect(x + buff, y + buff, mouseOverSize - buff * 2, mouseOverSize - buff * 2);
        screen.image(sprite, x + buff * 2, y + buff * 2, TILE_SIZE, TILE_SIZE);
        screen.popMatrix();
    }

    public void drawMouseOverText(float x, float y, WrappedText title, WrappedText subtitle, WrappedText description) {
        screen.pushMatrix();
        int mouseOverWidth = 3 * GUI_WIDTH/4;
        int mouseOverHeight = title.textHeight + subtitle.textHeight + description.textHeight + (buff * 5);

        if(x + mouseOverWidth > screen.width) x = screen.width - mouseOverWidth;
        if(y + mouseOverHeight > screen.height) y = screen.height - mouseOverHeight;

        screen.textAlign(game.LEFT, game.TOP);

        screen.fill(100);
        screen.rect(x, y, mouseOverWidth, mouseOverHeight);
        screen.noFill();
        screen.stroke(130);
        screen.rect(x + buff, y + buff, mouseOverWidth - buff * 2, mouseOverHeight - buff * 2);
        screen.line(x + buff, y + title.textHeight + buff/2, x + mouseOverWidth - buff, y + title.textHeight + buff/2);
        screen.line(x + buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2, x + mouseOverWidth - buff, (y + mouseOverHeight) - description.textHeight - 3 * buff/2);

        screen.fill(210);
        screen.textSize(title.textSize);
        screen.textLeading(title.textSize);
        screen.text(title.string, x + buff * 2, y + buff);

        screen.textSize(subtitle.textSize);
        screen.textLeading(subtitle.textSize);
        screen.text(subtitle.string, x + buff * 2, y + title.textHeight + (buff * 2));

        screen.fill(255);
        screen.textSize(description.textSize);
        screen.textLeading(description.textSize);
        screen.text(description.string, x + buff * 2, (y + mouseOverHeight) - description.textHeight - buff);
        screen.popMatrix();
    }

    public void drawMouseOverText(float x, float y, WrappedText description) {
        screen.pushMatrix();
        int mouseOverWidth = description.width != 0 ? (int)description.width : 3 * GUI_WIDTH/4;
        int mouseOverHeight = description.textHeight + (buff * 2);

        if(x + mouseOverWidth > screen.width) x = screen.width - mouseOverWidth;
        if(y + mouseOverHeight > screen.height) y = screen.height - mouseOverHeight;

        screen.textAlign(game.LEFT, game.TOP);

        screen.fill(100);
        screen.rect(x, y, mouseOverWidth, mouseOverHeight);
        screen.noFill();
        screen.stroke(130);
        screen.rect(x + buff, y + buff, mouseOverWidth - buff * 2, mouseOverHeight - buff * 2);

        screen.fill(255);
        screen.textSize(description.textSize);
        screen.textLeading(description.textSize);
        screen.text(description.string, x + buff * 2, y);
        screen.popMatrix();
    }

    public static void refresh() {
        DeadScreen.refresh();
        GUIScreen.refresh();
        LoadingScreen.refresh();
        LoadScreen.refresh();
        MenuScreen.refresh();
        NewGameScreen.refresh();
        OptionsScreen.refresh();
        PausedScreen.refresh();
        PlayScreen.refresh();
        TestScreen.refresh();
        CreditsScreen.refresh();
    }
}