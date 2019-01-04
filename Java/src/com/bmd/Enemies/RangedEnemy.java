package com.bmd.Enemies;

import com.bmd.App.Main;
import com.bmd.Entities.Projectile;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.*;

import java.awt.image.BufferedImage;

public abstract class RangedEnemy extends StandardEnemy implements Enemy {

    protected BufferedImage projectileSprite = Sprites.projectileSprites.get("WAND");
    protected float shotWaitTime = 1;
    protected float shootDistance = 3.2f;
    protected float retreatDistance = 2.7f;
    protected float accuracy = 0;
    protected boolean predictAim = false;
    protected float bulletSpeed = 10;

    public RangedEnemy(float x, float y, int tier, BufferedImage sprite) {
        super(x, y, tier, sprite);
        type = "RANGED";
    }

    public boolean update(double delta) {
        boolean alive = super.update(delta);
        if(predictAim) {
            float timeAway = Util.dist(x, y, Main.engine.player.x, Main.engine.player.y) / bulletSpeed;
            float playerX = Main.engine.player.x + Main.engine.player.dirX * timeAway;
            float playerY = Main.engine.player.y + Main.engine.player.dirY * timeAway;
            angle = (float)Math.atan2(playerY - y, playerX - x);
        }
        if(active) {
            move(delta);
            attack();
        }
        return alive;
    }

    protected void move(double delta) {
        float moveX = 0;
        float moveY = 0;
        float playerDistance = Util.dist(x, y, Main.engine.player.x, Main.engine.player.y);
        if(playerDistance > shootDistance) {
            moveX = (float)Math.cos(angle);
            moveY = (float)Math.sin(angle);
        } else if(playerDistance < retreatDistance) {
            moveX = -(float)Math.cos(angle);
            moveY = -(float)Math.sin(angle);
        }
        moveX = (moveX * stats.getSpeed() + knockbackX) * (float)delta;
        moveY = (moveY * stats.getSpeed() + knockbackY) * (float)delta;
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

    protected void attack() {
        if((stats.fireTimer > shotWaitTime * stats.getFireRate()) && (Main.engine.currentLevel.canSee((int)x, (int)y, (int)Main.engine.player.x, (int)Main.engine.player.y))) {
            stats.fireTimer = 0;
            float shotAccuracy = Util.randomGaussian() * accuracy;
            Main.engine.enemyProjectiles.add(new Projectile(x, y, new PVector((float)Math.cos(angle + shotAccuracy), (float)Math.sin(angle + shotAccuracy)), bulletSpeed, range, stats.attack, projectileSprite));
        }
    }

}