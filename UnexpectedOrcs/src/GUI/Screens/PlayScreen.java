package GUI.Screens;

import Enemies.Enemy;
import Enemies.StandardEnemy;
import Entities.Drops.Portal;
import GUI.DisplayBar;
import GUI.WrappedText;
import Items.Inventory;
import Utility.Util;
import GUI.Button;
import processing.core.PGraphics;
import processing.core.PImage;

import static Sprites.Sprites.guiSprites;
import static Sprites.Sprites.playerStatusSprites;
import static Utility.Colour.*;
import static Utility.Constants.*;

public class PlayScreen extends GUIScreen {

    private static final int invBuff = 5, invScale = 2, itemOffset = 1, invSize = SPRITE_SIZE * invScale + 2 * itemOffset;
    public static final int invX = (GUI_WIDTH - ((invSize * Inventory.WIDTH) + (invBuff * Inventory.WIDTH + itemOffset)))/2, invY = 7 * TILE_SIZE/2;

    private static Button pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, "PAUSE");
    private static Button enterPortal = new Button(GUI_WIDTH/2 - TILE_SIZE, 14 * TILE_SIZE/2, "BLANK_2x1");

    private static DisplayBar healthBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, TILE_SIZE/2 - invBuff, colour(230, 100, 100));
    private static DisplayBar manaBar = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, 2 * TILE_SIZE/2, colour(153, 217, 234));

    private static boolean showingPortal = false;

    public static void show(PGraphics screen) {
        //Draws the GUI during gameplay

        healthBar.updateBar(engine.player.stats.health, engine.player.stats.healthMax);
        manaBar.updateBar(engine.player.stats.mana, engine.player.stats.manaMax);

        screen.beginDraw();
        clearScreen(screen);
        screen.fill(217);
        screen.rect(0, 0, GUI_WIDTH, height);

        pause.show(screen);
        screen.textAlign(game.CENTER);
        screen.fill(50, 50, 50);
        screen.textSize(TILE_SIZE / 2);
        screen.text(loadedPlayerName, GUI_WIDTH / 2, 20);
        healthBar.show(screen);
        manaBar.show(screen);
        showStatusEffects(screen);
        drawQuest(screen);
        renderMiniMap(screen);
        drawPortal(screen);
        //renderInv();
        engine.player.inv.show(screen, invX, invY);
        engine.player.inv.drawCooldown(screen, invX, invY);

        engine.player.stats.show(screen, GUI_WIDTH * 2 / 5 - TILE_SIZE * 9 / 8, 73 + TILE_SIZE / 2);

        screen.endDraw();
        game.image(screen, 0, 0);

        if (Util.pointInBox(game.mouseX, game.mouseY, 0, 0, GUI_WIDTH, height)) {
            inMenu = true;
        } else {
            inMenu = false;
        }
    }

    public static void handleMouseReleased() {
        if(showingPortal && enterPortal.pressed()) {
            engine.enterClosestPortal();
        } else if(pause.pressed()) {
            game.setState("PAUSED");
        }
    }

    private static void drawPortal(PGraphics screen) {
        Portal portal = engine.getClosestPortal();
        if (portal == null) {
            showingPortal = false;
            return;
        }
        showingPortal = true;
        enterPortal.show(screen);
        screen.textAlign(game.CENTER, game.CENTER);
        screen.text("Enter " + portal.name, enterPortal.x, enterPortal.y, enterPortal.w, enterPortal.h);
    }

    private static void renderMiniMap(PGraphics screen) {

        float vw = GUI_WIDTH - (2 * invBuff); //width of the view
        float vh = vw * 0.8f;

        int minScale = game.max(game.ceil(vw/engine.currentLevel.w), game.ceil(vh/engine.currentLevel.h));
        int scale = game.max((int)((vw/engine.currentLevel.w) * miniMapZoom), minScale);

        int sx = (int)((engine.player.x * scale) - vw/2); //get the x-cord to start
        int sy = (int)((engine.player.y * scale) - vh/2); //get the y-cord to start

        //when you get close to the edges - stop centering on the player
        if (sx < 0) sx = 0;
        if (sy < 0) sy = 0;
        if (sx > (engine.currentLevel.w * scale) - vw) sx = (int)((engine.currentLevel.w * scale) - vw);
        if (sy > (engine.currentLevel.h * scale) - vh) sy = (int)((engine.currentLevel.h * scale) - vh);

        PImage map = Util.scaleImage(engine.currentLevel.getMiniMap().get(), (int)scale);
        PImage over = Util.scaleImage(engine.currentLevel.getOverlay().get(), (int)scale);

        screen.fill(150);
        screen.rect(0, height - vh - invBuff * 2, vw + invBuff * 2, vh + invBuff * 2);
        screen.fill(0);
        screen.rect(invBuff, height - vh - invBuff, vw, vh);
        screen.image(map.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
        screen.image(over.get(sx, sy, (int)vw, (int)vh), invBuff, height - vh - invBuff, vw, vh);
    }

    private static void showStatusEffects(PGraphics screen) {
        int i = 0;
        String mouseOverEffect = "";
        for (String effect : engine.player.stats.statusEffects.keySet()) {
            i++;
            screen.image(playerStatusSprites.get(effect), screen.width - i * TILE_SIZE, screen.height - TILE_SIZE, TILE_SIZE, TILE_SIZE);
            if (Util.pointInBox(game.mouseX, game.mouseY, screen.width - i * TILE_SIZE, screen.height - TILE_SIZE, TILE_SIZE, TILE_SIZE)) {
                mouseOverEffect = effect;
            }
        }

        if (!mouseOverEffect.equals("")) {
            int mouseOverWidth = 3 * GUI_WIDTH/4;
            WrappedText title = WrappedText.wrapText(mouseOverEffect, mouseOverWidth - gui.buff * 4, TILE_SIZE/2);
            WrappedText subtitle = WrappedText.wrapText(Util.roundTo(engine.player.stats.statusEffects.get(mouseOverEffect), 10) + "s remaining", mouseOverWidth - gui.buff * 4, TILE_SIZE/2);
            WrappedText description = WrappedText.wrapText("", mouseOverWidth - gui.buff * 4, 0);
            gui.drawMouseOverText(game.mouseX, game.mouseY, title, subtitle, description);
        }
    }

    private static void drawQuest(PGraphics screen) {
        float x = (width - GUI_WIDTH)/2 + GUI_WIDTH;
        float y = height/2;
        float r = game.min(x, y) - TILE_SIZE/2;
        PImage sprite = null;
        for (Enemy boss : engine.currentLevel.bosses) {
            float bx = ((StandardEnemy)boss).x;
            float by = ((StandardEnemy)boss).y;
            if (engine.currentLevel.visited((int)bx, (int)by) && game.dist(bx, by, engine.player.x, engine.player.y) < game.min(x, y)/TILE_SIZE) continue;
            float ang = game.atan2(by - engine.player.y, bx - engine.player.x);
            float dx = x + game.cos(ang) * r;
            float dy = y + game.sin(ang) * r;
            screen.pushMatrix();
            screen.translate(dx, dy);
            screen.rotate(ang);
            screen.image(guiSprites.get("QUEST"), -TILE_SIZE/4, -TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
            screen.popMatrix();
            if (game.dist(game.mouseX, game.mouseY, dx, dy) < TILE_SIZE/2) {
                sprite = ((StandardEnemy)boss).sprite;
            }
        }
        if (sprite != null) {
            gui.drawMouseOverSprite(game.mouseX, game.mouseY, sprite);
        }
    }

}
