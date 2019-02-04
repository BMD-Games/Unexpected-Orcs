package Enemies;

import Engine.Engine;
import Entities.Projectile;
import Levels.Level;
import Stats.Stats;
import Utility.Collision.*;
import Utility.Pair;
import Utility.Util;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static Utility.Colour.*;
import static Utility.Constants.*;
import static Sprites.Sprites.*;

public abstract class StandardEnemy implements Enemy {

    public int tier;

    public float x, y, knockbackX, knockbackY;
    public String type;

    protected int range = 10;
    protected float radius = 0.5f;
    protected boolean active = false;

    protected boolean hasSeen = false;

    private boolean tookDamage = false;
    private float damageTime = 0;
    private float damageMax = 0.25f;

    protected float angle;
    public PImage sprite;
    private PImage damageSprite;
    public Stats stats = new Stats();

    public ArrayList<String> typeList = new ArrayList();

    public StandardEnemy(float x, float y, int tier, PImage sprite) {
        this.tier = tier;
        this.x = x;
        this.y = y;

        this.sprite = sprite;
        this.damageSprite = Util.applyColourToImage(sprite, colour(200, 0, 0));

        if(this instanceof RectangleObject) radius = game.max(((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight()) / 2;
    }

    /* Enemies need to update on tics */
    public boolean update(double delta) {
        angle = game.atan2(engine.player.y - y, engine.player.x - x);
        active = Util.distance(x, y, engine.player.x, engine.player.y) < range;
        stats.update(delta);
        if(tookDamage) {
            damageTime += delta;
        }
        return stats.health > 0;
    }

    /* Displays enemy to screen */
    public void show(PGraphics screen, PVector renderOffset) {
        if(!engine.currentLevel.visited((int)x, (int)y)) return;
        screen.pushMatrix();
        screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);

        // display status effects of the enemy
        int i = 0;
        PImage statusSprite;
        for (String status : stats.statusEffects.keySet()) {
            statusSprite = statusSprites.get(status);
            screen.image(statusSprite, radius / 2 + TILE_SIZE * i / 4, SPRITE_SIZE / 2, statusSprite.width, statusSprite.height);
            i++;
        }

        PImage currSprite = sprite;
        if(tookDamage) {
            currSprite = damageSprite;
            tookDamage = damageTime < damageMax;
        }

        if((angle < game.PI/2) && (angle > -game.PI/2)) {
            screen.rotate(angle);
            screen.image(currSprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
        } else {
            screen.scale(-1.0f, 1.0f);
            screen.rotate(game.PI-angle);
            screen.image(currSprite, sprite.width * SCALE/2, -sprite.height * SCALE/2, -sprite.width * SCALE, sprite.height * SCALE);
        }
        screen.popMatrix();
    }

    /* Takes damage */
    public void damage(int amount, ArrayList<Pair> statusEffects) {
        for (Pair pair : statusEffects) {
            if (typeList.contains(pair.a) || pair.a.equals("ALL")) {
                this.stats.addStatusEffect(pair.b, 1);
            }
        }
        damage(amount);
    }

    public void damage(int amount) {
        int damage = amount - stats.defence;
        if (damage > 0) {
            stats.health -= damage;
            engine.addText(String.valueOf(damage), x, y - radius, 0.5f, colour(200, 0, 0));
        }
        tookDamage = true;
        damageTime = 0;
    }


    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        if(this instanceof RectangleObject) {
            return Rectangle.pointCollides(pointX, pointY, x, y, ((RectangleObject) this).getWidth(), ((RectangleObject) this).getHeight());
        } else if(this instanceof CircleObject) {
            return Circle.pointCollides(x, y, pointX, pointY, ((CircleObject) this).getRadius());
        }
        return false;
    }

    /* Checks collision with line */
    public boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2) {
        if(this instanceof RectangleObject) {
            float w = ((RectangleObject) this).getWidth();
            float h = ((RectangleObject) this).getHeight();
            if(game.drawDebug) {
                game.debugScreen.beginDraw();
                game.debugScreen.noFill();
                game.debugScreen.stroke(255);
                game.debugScreen.line(Engine.tileToScreenCoordX(lineX1), Engine.tileToScreenCoordY(lineY1), Engine.tileToScreenCoordX(lineX2), Engine.tileToScreenCoordY(lineY2));
                game.debugScreen.rect(Engine.tileToScreenCoordX(x-w/2), Engine.tileToScreenCoordY(y-h/2), w * TILE_SIZE, h * TILE_SIZE);
                game.debugScreen.endDraw();
            }
            return Rectangle.lineCollides(lineX1, lineY1,lineX2, lineY2, x - w/2, y - h/2, w, h);
        } else if(this instanceof CircleObject) {
            return Circle.lineCollides(lineX1, lineY1,lineX2, lineY2, x, y, ((CircleObject) this).getRadius());
        }
        return false;
    }

    /* Checks collision with area  */
    public boolean AABBCollides(AABB box) {
        return false;
    }

    public boolean validPosition(Level level, float xPos, float yPos) {
        if(this instanceof RectangleObject) {
            return Rectangle.validPosition(level, xPos, yPos, ((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight());
        } else if(this instanceof CircleObject) {
            return Circle.validPosition(level, xPos, yPos, radius);
        }
        return true;
    }

    public void knockback(Projectile projectile) {
        if(stats.health > 0) {
            knockbackX = projectile.direction.x * projectile.damage / stats.defence;
            knockbackY = projectile.direction.y * projectile.damage / stats.defence;
            System.out.println(knockbackY);
            System.out.println(knockbackX);
        }
    }

    public void knockback(Projectile projectile, float knockBackMultiplier) {
        if (stats.health > 0) {
            knockbackX += projectile.direction.x * projectile.damage * knockBackMultiplier / stats.defence;
            knockbackY += projectile.direction.y * projectile.damage * knockBackMultiplier / stats.defence;
            System.out.println(knockbackX);
            System.out.println(knockbackY);

        }

    }
}