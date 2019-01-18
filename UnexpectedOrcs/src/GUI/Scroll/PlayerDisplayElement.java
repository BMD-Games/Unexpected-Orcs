package GUI.Scroll;

import GUI.Button;
import Items.Inventory;
import Stats.PlayerStats;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class PlayerDisplayElement extends ScrollElement {

    private PlayerStats stats;
    private Inventory inv;

    public PlayerDisplayElement(String name, PlayerStats stats, Inventory inv) {
        super(name, "", 200);

        this.stats = stats;
        this.inv = inv;
    }

    @Override
    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);
        inv.show(screen, x + w/2, y + SPRITE_SIZE);
        stats.show(screen, x + w/4, y + SPRITE_SIZE);
    }
}
