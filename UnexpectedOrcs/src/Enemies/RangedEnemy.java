package Enemies;

import Entities.Projectile;
import Utility.Collision.Circle;
import Utility.Collision.CircleObject;
import Utility.Collision.Rectangle;
import Utility.Collision.RectangleObject;
import Utility.Util;
import processing.core.PImage;
import processing.core.PVector;

import static Sprites.Sprites.*;
import static Utility.Constants.*;

public abstract class RangedEnemy extends StandardEnemy implements Enemy {

    protected PImage projectileSprite = projectileSprites.get("WAND");
    protected float shotWaitTime = 1;
    protected float shootDistance = 3.2f;
    protected float retreatDistance = 2.7f;
    protected float accuracy = 0;
    protected boolean predictAim = false;
    protected float bulletSpeed = 10;

    public RangedEnemy(float x, float y, int tier, PImage sprite) {
        super(x, y, tier, sprite);
        type = "RANGED";
    }

    public boolean update(double delta) {
        boolean alive = super.update(delta);
        if(predictAim) {
            float timeAway = Util.distance(x, y, engine.player.x, engine.player.y) / bulletSpeed;
            float playerX = engine.player.x + engine.player.dirX * timeAway;
            float playerY = engine.player.y + engine.player.dirY * timeAway;
            angle = game.atan2(playerY - y, playerX - x);
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
        float playerDistance = Util.distance(x, y, engine.player.x, engine.player.y);
        if(playerDistance > shootDistance) {
            moveX = game.cos(angle);
            moveY = game.sin(angle);
        } else if(playerDistance < retreatDistance) {
            moveX = -game.cos(angle);
            moveY = -game.sin(angle);
        }
        moveX = (moveX * stats.getSpeed() + knockbackX) * (float)delta;
        moveY = (moveY * stats.getSpeed() + knockbackY) * (float)delta;
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

    protected void attack() {
        if((stats.fireTimer > shotWaitTime * stats.getFireRate()) && (engine.currentLevel.canSee((int)x, (int)y, (int)engine.player.x, (int)engine.player.y))) {
            stats.fireTimer = 0;
            float shotAccuracy = game.randomGaussian() * accuracy;
            engine.enemyProjectiles.add(new Projectile(x, y, new PVector(game.cos(angle + shotAccuracy), game.sin(angle + shotAccuracy)), bulletSpeed, range, stats.attack, projectileSprite));
        }
    }
}