package GUI.Screens;

import Engine.Engine;
import File.GameFile;
import GUI.Button;
import GUI.PopUp.ConfirmDelete;
import GUI.PopUp.ConfirmationCallback;
import GUI.Scroll.ScrollElement;
import GUI.Scroll.ScrollWindow;
import Utility.Constants;
import processing.core.PGraphics;

import static Utility.Constants.*;
import static Utility.Constants.game;

public class LoadScreen extends GUIScreen {

    private static Button play = new Button(width/4 * 3, height/2 + TILE_SIZE * 3, "PLAY");
    private static Button back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 3, "BACK");
    private static Button deleteSave = new Button(width/4 - TILE_SIZE/2, height/2 + TILE_SIZE * 3, "DELETE");

    public static ScrollWindow loadScroll = new ScrollWindow(width/8, height/4, width/4 * 3, height/2, new ScrollElement[0]);

    static ConfirmationCallback confirm = (name) -> { showConfirmation = false; GameFile.deleteSave((String)name[0]); loadScroll.setScrollElements(GameFile.loadAllSaves()); };
    static ConfirmationCallback cancel = name -> { showConfirmation = false; };
    private static ConfirmDelete deleteChar = new ConfirmDelete("Delete", "This will permenantly delete your character. You will not be able to get it back."
                                           , width/4, height/4, width/2, height/2, confirm, cancel, null);


    public static void show(PGraphics screen) {
        screen.beginDraw();

        clearScreen(screen);
        game.image(game.title, 0, 0, width, height);

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
}