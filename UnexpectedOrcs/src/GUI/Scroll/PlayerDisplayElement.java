package GUI.Scroll;

import GUI.Bars.DisplayBar;
import Items.Inventory;
import Stats.PlayerStats;
import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Colour.colour;
import static Utility.Constants.*;

public class PlayerDisplayElement extends ScrollElement {

    private PlayerStats stats;
    private Inventory inv;

    private DisplayBar hp;
    private DisplayBar mp;

    public PlayerDisplayElement(String name, PlayerStats stats, Inventory inv) {
        super(name, "", 200);

        this.stats = stats;
        this.inv = inv;

        hp = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, TILE_SIZE/2 - gui.buff, colour(230, 100, 100));
        mp = new DisplayBar(GUI_WIDTH/2 - TILE_SIZE * 1.5f + 4, 2 * TILE_SIZE/2, colour(153, 217, 234));

        hp.updateBar(stats.health, stats.healthMax);
        mp.updateBar(stats.mana, stats.manaMax);
    }

    @Override
    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);
        inv.show(screen, x + w/2 + TILE_SIZE * 3/2, y + SPRITE_SIZE);
        stats.show(screen, x + w/4 + TILE_SIZE * 3/4, y + TILE_SIZE * 3/4);

        hp.move(x + gui.buff,y + TILE_SIZE);
        hp.show(screen);

        mp.move(x + gui.buff,y + TILE_SIZE * 7/4);
        mp.show(screen);

        if (selected) {
            screen.strokeWeight(4);
            screen.stroke(200);
            screen.noFill();
            screen.rect(x, y, w, h);
            screen.image(guiSprites.get("TICK"), x + w - TILE_SIZE/2 - gui.buff, y + gui.buff, TILE_SIZE/2, TILE_SIZE/2);
        } else {
            screen.image(guiSprites.get("BLANK_1x1"), x + w - TILE_SIZE/2 - gui.buff, y + gui.buff, TILE_SIZE/2, TILE_SIZE/2);
        }
    }
}
