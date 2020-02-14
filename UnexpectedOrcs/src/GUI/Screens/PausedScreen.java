package GUI.Screens;

import App.GameState;
import File.GameFile;
import GUI.Button;
import Utility.Util;
import processing.core.PGraphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static GUI.WrappedText.wrapText;
import static Utility.Constants.*;

public class PausedScreen extends GUIScreen {

    private static Button play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
    private static Button load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 1, "LOAD");
    private static Button options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
    private static Button menu = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "MENU");

    public static void show(PGraphics screen) {
        //Draws the paused overlay
        PlayScreen.show(screen);
        screen.beginDraw();
        //clearScreen();
        screen.fill(0, 100);
        screen.rect(-TILE_SIZE, -TILE_SIZE, game.width + TILE_SIZE, game.height + TILE_SIZE);
        menu.show(screen);
        options.show(screen);
        play.show(screen);

        screen.fill(255);
        screen.textAlign(game.CENTER, game.CENTER);
        if(engine.player.stats.getSeed() != null) {
            screen.text("SEED: " + engine.player.stats.getSeed(), game.width/2, game.height - TILE_SIZE);

            float width = game.textWidth("SEED: " + engine.player.stats.getSeed());
            if(Util.pointInBox(game.mouseX, game.mouseY, game.width/2 - width/2, game.height - TILE_SIZE, width, TILE_SIZE/4)) {
                gui.drawMouseOverText(game.mouseX, game.mouseY, wrapText("Click to copy seed", TILE_SIZE * 3.2f, TILE_SIZE/2));
            }
        }



        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(play.pressed()) {
            game.setState(GameState.PLAYING);
            engine.updateMillis();
        } else if(options.pressed()) {
            game.setState(GameState.OPTIONS);
        } else if (load.pressed()) {
            game.setState(GameState.LOAD);
        } else if(menu.pressed()) {
            game.setState(GameState.MENU);
            GameFile.saveGame();
            engine.initiateDrops();
        } else if(engine.player.stats.getSeed() != null && Util.pointInBox(game.mouseX, game.mouseY, game.width/2 - game.textWidth("SEED: " + engine.player.stats.getSeed())/2,
                game.height - TILE_SIZE, game.textWidth("SEED: " + engine.player.stats.getSeed()), TILE_SIZE/4)) {
            //copy the seed to the clipboard
            StringSelection selection = new StringSelection(String.valueOf(engine.player.stats.getSeed()));
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            clip.setContents(selection, selection);
        }
    }

    public static void refresh() {
        play = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 2, "PLAY");
        load = new Button (game.width/2 - TILE_SIZE, game.height/2 - TILE_SIZE * 1, "LOAD");
        options = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 1, "OPTIONS");
        menu = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 2, "MENU");
    }

}
