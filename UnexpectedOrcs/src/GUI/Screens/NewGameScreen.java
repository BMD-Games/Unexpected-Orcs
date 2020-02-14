package GUI.Screens;

import App.GameState;
import Entities.Player;
import Entities.Text;
import File.GameFile;
import GUI.Button;
import GUI.PopUp.ConfirmationCallback;
import GUI.PopUp.StringInput;
import processing.core.PGraphics;
import java.util.Date;

import static GUI.WrappedText.wrapText;
import static Utility.Colour.colour;
import static Utility.Constants.*;

public class NewGameScreen extends GUIScreen {


    private static String playerName = "";

    private static Button play = new Button (game.width/3, game.height/2 + TILE_SIZE , "PLAY");
    private static Button quick = new Button(game.width/3 * 2 - 2 * TILE_SIZE, game.height/2 + TILE_SIZE, "QUICK");
    private static Button back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");
    private static Button seed = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE, "SEED");

    private static boolean nameAlreadyExists = false;

    private static boolean seedDialogOpen = false;
    private static Long seedValue;

    private static ConfirmationCallback setSeed = (string) -> {
        seedDialogOpen = false;
        try {
            seedValue = Long.valueOf((String)string[0]);
        } catch(Exception e) {
            seedDialogOpen = true;
        }
    };
    private static StringInput seedDialog = new StringInput("Set seed", "Set the random seed used for level generation",
                                                        game.width/4, game.height/3, game.width/2, game.height/2, setSeed);


    public static void show(PGraphics screen) {
        screen.beginDraw();
        clearScreen(screen);
        background(screen);
        gui.keyInput = true;

        screen.fill(200);
        screen.noStroke();
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text("Choose your hero's name!", game.width/2, game.height/2 - TILE_SIZE * 2);
        screen.rect(game.width/2 - TILE_SIZE * 2, game.height/2 - TILE_SIZE/4 * 5, TILE_SIZE * 4, TILE_SIZE);

        screen.fill(50);
        screen.noStroke();
        screen.textSize(TILE_SIZE);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text(playerName, game.width/2, game.height/2 - (TILE_SIZE/8 * 7));

        if(game.frameCount % 60 < 30) {
            screen.rect(game.width/2 + game.textWidth(playerName)/2 + gui.buff/2, game.height/2 - TILE_SIZE/6 * 7, gui.buff, TILE_SIZE/3 * 2);
        }


        screen.textSize(TILE_SIZE/2);
        if (checkFileAlreadyExists(playerName)) {
            Text.outlineText(screen, "A hero with that name already exists.", game.width/2, game.height/2 + TILE_SIZE/4, colour(150, 0, 0), colour(200));
        }


        if(nameAlreadyExists) screen.text("That name already exists", game.width/2, game.height/2 - TILE_SIZE * 2);

        play.show(screen);
        quick.show(screen);
        back.show(screen);
        seed.show(screen);

        if(seedDialogOpen) {
            if(seedDialog.show(screen) == CANCEL) {
                seedDialogOpen = false;
            }
        } else if(quick.pressed()) {
            gui.drawMouseOverText(game.mouseX, game.mouseY, wrapText("Quick games DO NOT GET SAVED!", TILE_SIZE * 3, TILE_SIZE/2));
        } else if(seed.pressed()) {
            gui.drawMouseOverText(game.mouseX, game.mouseY, wrapText("Set seed for player" + (seedValue != null ? " " + seedValue.toString() : ""), TILE_SIZE * 3, TILE_SIZE/2));
        }



        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(seedDialogOpen) return;
        if(back.pressed()) {
            game.setState(GameState.MENU);
            playerName = "";
            gui.keyInput = false;
        } else if (play.pressed()) {
            if (playerName.length() > 0 && !checkFileAlreadyExists(playerName)) {
                loadedPlayerName = playerName;
                guestMode = false;
                gui.keyInput = false;
                engine.reset(true);
                engine.setPlayer(new Player());
                engine.player.stats.setSeed((seedValue == null ? new Date().getTime() : seedValue));
                game.randomSeed(engine.player.stats.getSeed());
                GameFile.saveGame();
                game.setState(GameState.PLAYING);
                playerName = "";
            }
        } else if (quick.pressed()) {
            loadedPlayerName = "GUEST";
            guestMode = true;
            gui.keyInput = false;
            engine.reset(true);
            engine.setPlayer(new Player());
            engine.player.stats.setSeed((seedValue == null ? new Date().getTime() : seedValue));
            game.randomSeed(engine.player.stats.getSeed());
            game.setState(GameState.PLAYING);
            playerName = "";
        } else if(seed.pressed()) {
            seedDialogOpen = true;
        }
    }

    public static void handleKeyInput(char key) {
        if(seedDialogOpen) {
            seedDialog.handleKeyInput(key);
            return;
        }
        if (key == game.ENTER || key == game.RETURN && playerName.length() > 0) {
            if (checkFileAlreadyExists(playerName)) {
                nameAlreadyExists = true;
            } else {
                nameAlreadyExists = false;
                loadedPlayerName = playerName;
                guestMode = false;
                gui.keyInput = false;
                engine.reset(true);
                engine.setPlayer(new Player());
                GameFile.saveGame();
                game.setState(GameState.PLAYING);
                playerName = "";
            }
        } else if (Character.isLetterOrDigit(key) && playerName.length() < 10) {
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

    public static void refresh() {
        playerName = "";

        play = new Button (game.width/3, game.height/2 + TILE_SIZE , "PLAY");
        quick = new Button(game.width/3 * 2 - 2 * TILE_SIZE, game.height/2 + TILE_SIZE, "QUICK");
        back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");

        nameAlreadyExists = false;
    }
}