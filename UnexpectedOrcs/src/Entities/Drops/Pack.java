package Entities.Drops;

import Sprites.AnimatedSprite;
import Utility.Util;
import processing.core.PVector;

import static Sprites.Sprites.dropSprites;
import static Utility.Constants.engine;
import static Utility.Constants.statColours;

public class Pack extends Drop{

    private String stat;
    private int tier;
    private float amount;

    private float pickUpRadius = 0.3f;

    private boolean playerWasInRange = false;
    private float vel = 0, acc = 2.5f;

    public Pack(float x, float y, int tier, String stat) {
        super(x, y, 1.5f, 10);
        this.stat = stat;
        this.tier = tier;
        this.sprites = new AnimatedSprite(Util.applyColourToImage(dropSprites.get("PACK").copy(), statColours.get(stat)));
    }


    @Override
    public boolean update(double delta, float px, float py) {
        boolean up = super.update(delta, px, py);
        if(getDist(px, py) < pickUpRadius) {
            engine.player.stats.addPack(stat, tier);
            return false;
        } else if(inRange(px, py)) { //engine.currentLevel.canSee((int)x, (int)y, (int)px, (int)py) &&
            playerWasInRange = true;
        }
        if(playerWasInRange) {
            PVector dir = new PVector(px - x, py - y).normalize();
            dir.mult((float)(vel * delta));
            x += dir.x;
            y += dir.y;
            vel += acc * delta;
            lifeTime += delta;
        } else {
            vel = 0;
        }
        return up;
    }

}
