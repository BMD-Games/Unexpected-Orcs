package com.bmd.Enemies;

import com.bmd.App.Main;
import com.bmd.Util.*;

import java.awt.image.BufferedImage;

abstract public class MeleeEnemy extends StandardEnemy implements Enemy {

    protected float attackWaitTime = 0.8f;

    public MeleeEnemy(float x, float y, int tier, BufferedImage sprite) {
        super(x, y, tier, sprite);
        type = "MELEE";
    }

    public boolean update(double delta) {
        if (Util.dist(x, y, Main.engine.player.x, Main.engine.player.y) < range) {
            if (pointCollides(Main.engine.player.x, Main.engine.player.y)) {
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
            Main.engine.player.damage(stats.attack * 2);
        }
    }

    protected void move(double delta) {
        float moveX = (float)((stats.getSpeed() * Math.cos(angle) + knockbackX) * delta);
        float moveY = (float)((stats.getSpeed() * Math.sin(angle) + knockbackY) * delta);
        knockbackX = knockbackY = 0;
        float[] coords;
        if(this instanceof RectangleObject) {
            coords = Rectangle.adjust(Main.engine.currentLevel, x, y, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight(), moveX, moveY);
        } else if(this instanceof CircleObject) {
            coords = Circle.adjust(Main.engine.currentLevel, x, y, radius, moveX, moveY);
        } else {
            coords = new float[] {x + moveX, y + moveY};
        }
        x = coords[0];
        y = coords[1];
    }
}