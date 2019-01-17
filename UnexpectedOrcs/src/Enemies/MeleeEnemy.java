package Enemies;

import Utility.Collision.Circle;
import Utility.Collision.CircleObject;
import Utility.Collision.Rectangle;
import Utility.Collision.RectangleObject;
import Utility.Util;
import processing.core.PImage;

import static Utility.Constants.*;

public abstract class MeleeEnemy extends StandardEnemy implements Enemy {

    protected float attackWaitTime = 0.8f;

    public MeleeEnemy(float x, float y, int tier, PImage sprite) {
        super(x, y, tier, sprite);
        type = "MELEE";
    }

    public boolean update(double delta) {
        if (Util.distance(x, y, engine.player.x, engine.player.y) < range) {
            if (pointCollides(engine.player.x, engine.player.y)) {
                attack();
            } else {
                move(delta);
            }
        }
        return super.update(delta);
    }

    protected void attack() {
        if (stats.fireTimer > attackWaitTime * stats.getFireRate()) {
            stats.fireTimer = 0;
            engine.player.damage(stats.attack * 2);
        }
    }

    protected void move(double delta) {
        float moveX = (stats.getSpeed() * game.cos(angle) + knockbackX) * (float)delta;
        float moveY = (stats.getSpeed() * game.sin(angle) + knockbackY) * (float)delta;
        knockbackX = knockbackY = 0;
        float[] coords;
        if(this instanceof RectangleObject) {
            coords = Rectangle.adjust(engine.currentLevel, x, y, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight(), moveX, moveY);
        } else if(this instanceof CircleObject) {
            coords = Circle.adjust(engine.currentLevel, x, y, radius, moveX, moveY);
        } else {
            coords = new float[] {x + moveX, y + moveY};
        }
        x = coords[0];
        y = coords[1];
    }
}
