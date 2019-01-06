package com.bmd.Enemies;

import com.bmd.App.Graphics;
import com.bmd.App.Main;
import com.bmd.Entities.Projectile;
import com.bmd.Levels.Level;
import com.bmd.Sprites.Sprites;
import com.bmd.Stats.Stats;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract public class StandardEnemy implements Enemy {

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
    public BufferedImage sprite;
    private BufferedImage damageSprite;
    public Stats stats = new Stats();

    public ArrayList<String> typeList = new ArrayList();

    public StandardEnemy(float x, float y, int tier, BufferedImage sprite) {
        this.tier = tier;
        this.x = x;
        this.y = y;

        this.sprite = sprite;
        this.damageSprite = Util.applyColourToImage(sprite, Util.color(200, 0, 0));

        if(this instanceof RectangleObject) radius = (float)Math.max(((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight()) / 2;
    }

    /* Enemies need to update on tics */
    public boolean update(double delta) {
        angle = (float)Math.atan2(Main.engine.player.y - y, Main.engine.player.x - x);
        active = Util.dist(x, y, Main.engine.player.x, Main.engine.player.y) < range;
        stats.update(delta);
        if(tookDamage) {
            damageTime += delta;
        }
        return stats.health > 0;
    }

    /* Displays enemy to screen */
    public void show(Canvas canvas, PVector renderOffset) {
        GraphicsContext screen = canvas.getGraphicsContext2D();
        if(!Main.engine.currentLevel.visited((int)x, (int)y)) return;
        screen.save();
        screen.translate(x * Tiles.TILE_SIZE - renderOffset.x, y * Tiles.TILE_SIZE - renderOffset.y);

        // display status effects of the enemy
        int i = 0;
        BufferedImage statusSprite;
        for (String status : stats.statusEffects.keySet()) {
            statusSprite = Sprites.statusSprites.get(status);
            Graphics.image(screen, statusSprite, radius / 2 + Tiles.TILE_SIZE * i / 4, Sprites.SPRITE_SIZE / 2, statusSprite.getWidth(), statusSprite.getHeight());
            i++;
        }

        BufferedImage currSprite = sprite;
        if(tookDamage) {
            currSprite = damageSprite;
            tookDamage = damageTime < damageMax;
        }

        if((angle < Math.PI/2) && (angle > -Math.PI/2)) {
            screen.rotate(angle);
            Graphics.image(screen, currSprite, -sprite.getWidth() * Util.SCALE/2, -sprite.getHeight() * Util.SCALE/2, sprite.getWidth() * Util.SCALE, sprite.getHeight() * Util.SCALE);
        } else {
            screen.scale(-1.0, 1.0);
            screen.rotate(Math.PI-angle);
            Graphics.image(screen, currSprite, sprite.getWidth() * Util.SCALE/2, -sprite.getHeight() * Util.SCALE/2, -sprite.getWidth() * Util.SCALE, sprite.getHeight() * Util.SCALE);
        }
        screen.restore();
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
            Main.engine.addText(String.valueOf(damage), x, y - radius, 0.5f, Util.color(200, 0, 0));
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
            /*if(drawDebug) {
                debugScreen.beginDraw();
                debugScreen.noFill();
                debugScreen.stroke(255);
                debugScreen.line(Engine.tileToScreenCoordX(lineX1), Engine.tileToScreenCoordY(lineY1), Engine.tileToScreenCoordX(lineX2), Engine.tileToScreenCoordY(lineY2));
                debugScreen.rect(Engine.tileToScreenCoordX(x-w/2), Engine.tileToScreenCoordY(y-h/2), w * Tiles.TILE_SIZE, h * Tiles.TILE_SIZE);
                debugScreen.endDraw();
            }*/
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
        }
    }
}