package GUI.Screens;

import File.GameFile;
import GUI.Button;
import GUI.PopUp.ConfirmDelete;
import GUI.PopUp.ConfirmationCallback;
import GUI.Scroll.ScrollElement;
import GUI.Scroll.ScrollWindow;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class LoadScreen extends GUIScreen {

    private static Button play = new Button(game.width/4 * 3, game.height/2 + TILE_SIZE * 3, "PLAY");
    private static Button back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");
    private static Button deleteSave = new Button(game.width/4 - TILE_SIZE/2, game.height/2 + TILE_SIZE * 3, "DELETE");

    public static ScrollWindow loadScroll = new ScrollWindow(game.width/8, game.height/4, game.width/4 * 3, game.height/2, new ScrollElement[0]);

    static ConfirmationCallback confirm = (name) -> { showConfirmation = false; GameFile.deleteSave((String)name[0]); loadScroll.setScrollElements(GameFile.loadAllSaves()); };
    static ConfirmationCallback cancel = (name) -> { showConfirmation = false; };
    private static ConfirmDelete deleteChar = new ConfirmDelete("Delete", "This will permenantly delete your character. You will not be able to get it back.",
            game.width/4, game.height/4, game.width/2, game.height/2, confirm, cancel, null);


    public static void show(PGraphics screen) {
        screen.beginDraw();

        clearScreen(screen);
        game.image(game.title, 0, 0, game.width, game.height);


        game.fill(0, 60);
        game.rect(-TILE_SIZE, -TILE_SIZE, game.width + TILE_SIZE, game.height + TILE_SIZE);

        loadScroll.update();
        loadScroll.show(screen);

        back.show(screen);
        play.show(screen);
        deleteSave.show(screen);

        if(showConfirmation) {
            if(loadScroll.selectedElement != -1) {
                Object[] params = {loadScroll.scrollElements[loadScroll.selectedElement].title};
                deleteChar.title = "Delete \"" + params[0] + "\"";
                deleteChar.setParams(params);
            }
            deleteChar.display(screen);
        }

        if(GameFile.numSaves() != loadScroll.scrollElements.length) {
            loadScroll.setScrollElements(GameFile.loadAllSaves());
        }

        screen.endDraw();
        game.image(screen, 0, 0);
    }

    public static void handleMouseReleased() {
        if(play.pressed()) {
            if(loadScroll.selectedElement != -1) {
                guestMode = false;
                engine.reset(false);
                engine.setPlayer(loadedPlayers[loadScroll.selectedElement]);
                loadedPlayerName = loadScroll.scrollElements[loadScroll.selectedElement].title;
                game.setState("PLAYING");
            }
        } else if(deleteSave.pressed()) {
            if(loadScroll.selectedElement != -1) {
                showConfirmation = true;
            }
        } else if(back.pressed()) {
            game.revertState();
        }

        loadScroll.handleMouse();
    }

    public static void refresh() {
        play = new Button(game.width/4 * 3, game.height/2 + TILE_SIZE * 3, "PLAY");
        back = new Button (game.width/2 - TILE_SIZE, game.height/2 + TILE_SIZE * 3, "BACK");
        deleteSave = new Button(game.width/4 - TILE_SIZE/2, game.height/2 + TILE_SIZE * 3, "DELETE");

        loadScroll = new ScrollWindow(game.width/8, game.height/4, game.width/4 * 3, game.height/2, new ScrollElement[0]);

        ConfirmationCallback confirm = (name) -> { showConfirmation = false; GameFile.deleteSave((String)name[0]); loadScroll.setScrollElements(GameFile.loadAllSaves()); };
        ConfirmationCallback cancel = name -> { showConfirmation = false; };
        ConfirmDelete deleteChar = new ConfirmDelete("Delete", "This will permenantly delete your character. You will not be able to get it back.",
                game.width/4, game.height/4, game.width/2, game.height/2, confirm, cancel, null);
    }
}