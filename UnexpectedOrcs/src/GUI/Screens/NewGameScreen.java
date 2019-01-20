package GUI.Screens;

import Engine.Engine;
import Entities.Player;
import File.GameFile;
import GUI.Button;
import Utility.Constants;
import processing.core.PGraphics;

import static Settings.Settings.*;
import static Utility.Constants.*;

public class NewGameScreen extends GUIScreen {


    private static String playerName = "";

    private static Button play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, "PLAY");
    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");
    private static Button quick = new Button(width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, "QUICK");

    public static void show(PGraphics screen) {
        screen.beginDraw();
        clearScreen(screen);
        background(screen);

        characterNaming = true;
        screen.fill(200, 200, 200);
        screen.rect(width/2 - TILE_SIZE * 2, height/2 - TILE_SIZE / 4 * 3, TILE_SIZE * 4, TILE_SIZE);
        screen.fill(50, 50, 50);
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER);
        screen.text(playerName, width/2, height/2);
        screen.textSize(TILE_SIZE/2);
        if (checkFileAlreadyExists(playerName)) {
            screen.text("A hero with that name already exists.", width/2, height/2 + TILE_SIZE);
        }

        play.show(screen);
        quick.show(screen);
        back.show(screen);

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.setState("MENU");
            playerName = "";
        } else if (play.pressed()) {
            if (playerName.length() > 0 && !checkFileAlreadyExists(playerName)) {
                loadedPlayerName = playerName;
                guestMode = false;
                characterNaming = false;
                engine.reset();
                engine.setPlayer(new Player());
                GameFile.saveGame();
                game.setState("PLAYING");
                playerName = "";
            }
        } else if (quick.pressed()) {
            loadedPlayerName = "GUEST";
            guestMode = true;
            characterNaming = false;
            engine.reset();
            engine.setPlayer(new Player());
            game.setState("PLAYING");
            playerName = "";
        }
    }

    public static void keyPressed(PGraphics screen, char key) {
        if (key == game.ENTER || key == game.RETURN && playerName.length() > 0) {
            if (checkFileAlreadyExists(playerName)) {
                screen.text("That name already exists", width/2, height/2 - TILE_SIZE);
            } else {
                loadedPlayerName = playerName;
                game.setState("PLAYING");
                characterNaming = false;
            }
        } else if (Character.isLetter(key) && playerName.length() < 10) {
            playerName = playerName + key;
        } else if (key == game.BACKSPACE && playerName.length() > 0) {
            playerName = playerName.substring(0, playerName.length() - 1);
        }
    }

    private static Boolean checkFileAlreadyExists(String fileName) {

        java.io.File folder = new java.io.File(game.sketchPath() + "/saves/");
        String[] listOfFiles = folder.list();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}