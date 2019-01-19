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

    public int buff = 6; //for mouseOver stuff;

    public GUI() {
        screen = game.createGraphics(width, height);
        screen.beginDraw();
        screen.noSmooth();
        screen.textFont(bitcell);
        screen.endDraw();
    }


    public void show() {
        if(game.STATE =="MENU") {
            MenuScreen.show(screen);
        } else if(game.STATE =="OPTIONS") {
            OptionsScreen.show(screen);
        } else if(game.STATE =="PLAYING") {
            PlayScreen.show(screen);
        } else if(game.STATE =="PAUSED") {
            PausedScreen.show(screen);
        } else if(game.STATE =="DEAD") {
            DeadScreen.show(screen);
        } else if(game.STATE =="NEWGAME") {
            NewGameScreen.show(screen);
        } else if(game.STATE =="LOAD") {
            LoadScreen.show(screen);
        } else {
            game.setState("MENU");
        }
    }

    public void handleMouseReleased() {
        //------Main Buttons
        if (game.STATE == "MENU") {
            MenuScreen.handleMouseReleased();
        } else if (game.STATE == "OPTIONS") {
            OptionsScreen.handleMouseReleased();
        } else if (game.STATE == "PAUSED") {
            PausedScreen.handleMouseReleased();
        } else if (game.STATE == "PLAYING") {
            PlayScreen.handleMouseReleased();
        } else if (game.STATE == "DEAD") {
            DeadScreen.handleMouseReleased();
        } else if (game.STATE == "LOAD") {
           LoadScreen.handleMouseReleased();
        } else if (game.STATE == "NEWGAME") {
            NewGameScreen.handleMouseReleased();
        }
    }

    public void drawMouseOverSprite(float x, float y, PImage sprite) {
        int mouseOverSize = TILE_SIZE + buff * 4;

        if (x + mouseOverSize > screen.width) x = screen.width - mouseOverSize;
        if (y + mouseOverSize > screen.height) y = screen.height - mouseOverSize;

        screen.fill(100);
        screen.rect(x, y, mouseOverSize, mouseOverSize);
        screen.noFill();
        screen.stroke(130);
        screen.rect(x + buff, y + buff, mouseOverSize - buff * 2, mouseOverSize - buff * 2);
        screen.image(sprite, x + buff * 2, y + buff * 2, TILE_SIZE, TILE_SIZE);
    }

    public void drawMouseOverText(float x, float y, WrappedText title, WrappedText subtitle, WrappedText description) {
        int mouseOverWidth = 3 * GUI_WIDTH/4;
        int mouseOverHeight = title.textHeight + subtitle.textHeight + description.textHeight + (buff * 5);

        if (x + mouseOverWidth > screen.width) x = screen.width - mouseOverWidth;
        if (y + mouseOverHeight > screen.height) y = screen.height - mouseOverHeight;

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
    }

    //    public void drawSave() {
//
//        screen.beginDraw();
//        screen.background(c);
//        screen.fill(0, 100);
//        screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
//        back.show(screen);
//
//        screen.endDraw();
//        game.image(screen, 0, 0);
//    }

}