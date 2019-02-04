package Enemies.CreepyCrawlies;

import Enemies.Enemy;
import Enemies.MeleeEnemy;
import Entities.Drops.ItemBag;
import Entities.Drops.StatOrb;
import Items.Scrolls.DebuffScroll;
import Utility.Collision.RectangleObject;
import processing.core.PImage;

import static Utility.Constants.*;
import static Sprites.Sprites.*;

public class Bat extends MeleeEnemy implements Enemy, RectangleObject {

    private float w = 0.5f, h = 0.5f;
    private float animationTime = 0;
    private int lastAnim = 1;
    protected PImage[] sprites = {charSprites.get("BAT_SPREAD"), charSprites.get("BAT_FLAPPING")};

    public Bat(float x, float y, int tier) {
        super(x, y, tier, charSprites.get("BAT_SPREAD"));
        stats.speed = 2.2f + 0.3f * tier;
        stats.attack = 8 + 12 * tier;
        stats.defence = 4 * tier;
        stats.health = 25 + 25 * tier;
        stats.vitality = 2;
        attackWaitTime = 0.5f;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public boolean update(double delta) {
        animationTime += delta + game.random((float)delta / 2);
        animationTime %= 0.3;
        int currentAnim = (int)(animationTime / 0.1);
        if((currentAnim == 0) && (lastAnim != 0)) {
            y += 0.05;
            sprite = sprites[1];
        } else if((currentAnim != 0) && (currentAnim != lastAnim)) {
            y -= 0.025;
            sprite = sprites[0];
        }
        lastAnim = currentAnim;
        return super.update(delta);
    }

    public void onDeath() {
        super.onDeath();
        engine.addDrop(new StatOrb(x, y, tier, "SPEED"));
        ItemBag itembag = new ItemBag(x, y, tier);
        if(game.random(1) < 0.1) {
            itembag.addItem(new DebuffScroll(new String[] {"DAZED"}));
        }
        engine.addDrop(itembag);
    }

    protected void attack() {
        super.attack();
        engine.player.stats.addStatusEffect("DAZED", 5);
    }

}
