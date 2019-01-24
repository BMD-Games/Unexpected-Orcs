package GUI.Screens;

import Entities.Player;
import Entities.Text;
import File.GameFile;
import GUI.Button;
import processing.core.PGraphics;

import static GUI.WrappedText.wrapText;
import static Settings.Settings.characterNaming;
import static Utility.Colour.colour;
import static Utility.Constants.*;

public class NewGameScreen extends GUIScreen {


    private static String playerName = "";

    private static Button play = new Button (width/3, height/2 + TILE_SIZE , "PLAY");
    private static Button quick = new Button(width/3 * 2 - 2 * TILE_SIZE, height/2 + TILE_SIZE, "QUICK");
    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");

    public static void show(PGraphics screen) {
        screen.beginDraw();
        clearScreen(screen);
        background(screen);

        characterNaming = true;
        screen.fill(200);
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text("Choose your hero's name!", width/2, height/2 - TILE_SIZE * 2);
        screen.rect(width/2 - TILE_SIZE * 2, height/2 - TILE_SIZE/4 * 5, TILE_SIZE * 4, TILE_SIZE);

        screen.fill(50, 50, 50);
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text(playerName, width/2, height/2 - (TILE_SIZE/8 * 7));

        if(game.frameCount % 60 < 30) {
            screen.rect(width/2 + game.textWidth(playerName)/2 + gui.buff/2, height/2 - TILE_SIZE/6 * 7, gui.buff, TILE_SIZE/3 * 2);
        }


        screen.textSize(TILE_SIZE/2);
        if (checkFileAlreadyExists(playerName)) {
            Text.outlineText(screen, "A hero with that name already exists.", width/2, height/2 + TILE_SIZE/4, colour(150, 0, 0), colour(200));
        }


        play.show(screen);
        quick.show(screen);
        back.show(screen);

        if(quick.pressed()) {
            gui.drawMouseOverText(game.mouseX, game.mouseY, wrapText("Quick games DO NOT GET SAVED!", TILE_SIZE * 2, TILE_SIZE/2));
        }

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(back.pressed()) {
            game.setState("MENU");
            playerName = "";
            characterNaming = false;
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
                guestMode = false;
                characterNaming = false;
                engine.reset();
                engine.setPlayer(new Player());
                GameFile.saveGame();
                game.setState("PLAYING");
                playerName = "";
            }
        } else if (Character.isLetter(key) && playerName.length() < 10) {
            playerName = playerName + key;
        } else if (key == game.BACKSPACE && playerName.length() > 0) {
            playerName = playerName.substring(0, playerName.length() - 1);
        }
    }

    private static Boolean checkFileAlreadyExists(String fileName) {
        GameFile.checkSavesFolderExists();
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