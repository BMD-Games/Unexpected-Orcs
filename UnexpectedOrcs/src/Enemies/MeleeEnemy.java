package Enemies;

import Entities.Projectile;
import Utility.Collision.Circle;
import Utility.Collision.CircleObject;
import Utility.Collision.Rectangle;
import Utility.Collision.RectangleObject;
import Utility.Util;
import processing.core.PImage;
import processing.core.PVector;

import static Utility.Constants.*;

public abstract class MeleeEnemy extends StandardEnemy {

    protected float attackWaitTime = 0.8f;

    public MeleeEnemy(float x, float y, int tier, PImage sprite) {
        super(x, y, tier, sprite);
        type = "MELEE";
    }

    public boolean update(double delta) {
        if (Util.distance(x, y, engine.player.x, engine.player.y) < range) {
            if (pointCollides(engine.player.x, engine.player.y)) {
                attack();
            } else if (!pointCollides(lastPlayerX, lastPlayerY)) {
                move(delta, angle);
            }
        }
        return super.update(delta);
    }

    protected void attack() {
        if (stats.fireTimer > attackWaitTime * stats.getFireRate()) {
            stats.fireTimer = 0;
            engine.player.damage(new Projectile(x, y, new PVector(game.cos(angle), game.sin(angle)), 0, 0, stats.attack, null));
        }
    }

    private void move(double delta, float targetAngle) {
        float moveX;
        float moveY;
        int knockbackMax = 15;
        PVector knockback = new PVector(knockbackX, knockbackY);
        if (knockback.mag() > knockbackMax) {
            knockback.setMag(knockbackMax);
            moveX = (stats.getSpeed() * game.cos(targetAngle) + knockback.x) * (float) delta;
            moveY = (stats.getSpeed() * game.sin(targetAngle) + knockback.y) * (float) delta;
            knockbackX -= knockback.x;
            knockbackY -= knockback.y;
        } else {
            moveX = (stats.getSpeed() * game.cos(targetAngle) + knockbackX) * (float) delta;
            moveY = (stats.getSpeed() * game.sin(targetAngle) + knockbackY) * (float) delta;
            knockbackX = knockbackY = 0;
        }
        float[] coords;
        if(this instanceof RectangleObject) {
            coords = Rectangle.adjust(engine.currentLevel, x, y, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight(), moveX, moveY);
        } else if(this instanceof CircleObject) {
            coords = Circle.adjust(engine.currentLevel, x, y, radius, moveX, moveY);
        } else {
            coords = new float[]{x + moveX, y + moveY};
        }
        x = coords[0];
        y = coords[1];
        lastMoveSpeed = Util.distance(x, y, coords[0], coords[1]) / (float)delta;
    }
}
