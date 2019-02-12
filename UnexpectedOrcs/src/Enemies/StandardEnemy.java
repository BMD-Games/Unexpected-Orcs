package Enemies;

import Engine.Engine;
import Entities.Drops.Blood;
import Entities.Drops.Pack;
import Entities.Projectile;
import Levels.Level;
import Sound.SoundManager;
import Sprites.AnimatedSprite;
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
    boolean active = false;

    protected boolean hasSeen = false;

    private boolean tookDamage = false;
    private float damageTime = 0;
    protected float damageTimeMax = 0.25f;

    protected float angle;
    public PImage sprite;
    protected AnimatedSprite animatedSprite = null;
    protected PImage damageSprite;
    public Stats stats = new Stats();

    public ArrayList<String> typeList = new ArrayList();

    public StandardEnemy(float x, float y, int tier, PImage sprite) {
        this.tier = tier;
        this.x = x;
        this.y = y;

        this.setSprite(sprite);


        if(this instanceof RectangleObject) radius = game.max(((RectangleObject)this).getWidth(), ((RectangleObject)this).getHeight()) / 2;
    }

    /* Enemies need to update on tics */
    public boolean update(double delta) {
        float angleDiff = game.atan2(engine.player.y - y, engine.player.x - x) - angle;
        float maxRotation = 1.8f * ((float)delta);
        if (Math.abs(angleDiff) < (1.1 * delta) || Math.abs(angleDiff) > 2 * Math.PI - maxRotation) {
            angle += angleDiff;
        } else if(Math.abs(angleDiff) < Math.PI) {
            angle += Util.sign(angleDiff) * maxRotation;
        } else {
            angle -= Util.sign(angleDiff) * maxRotation;
        }
        active = Util.distance(x, y, engine.player.x, engine.player.y) < range;
        stats.update(delta);
        animatedSprite.update(delta);
        if(tookDamage) {
            damageTime += delta;
        }
        sprite = animatedSprite.getCurrentSprite();
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
            tookDamage = damageTime < damageTimeMax;
        }

        if((angle < game.PI/2) && (angle > -game.PI/2)) {
            screen.rotate(angle);
            try {
                screen.image(currSprite, -sprite.width * SCALE / 2, -sprite.height * SCALE / 2, sprite.width * SCALE, sprite.height * SCALE);
            } catch (NullPointerException e) {
                System.out.println(this.getClass().toString());
            }
        } else {
            screen.scale(-1.0f, 1.0f);
            screen.rotate(game.PI-angle);
            try {
                screen.image(currSprite, sprite.width * SCALE / 2, -sprite.height * SCALE / 2, -sprite.width * SCALE, sprite.height * SCALE);
            } catch (NullPointerException e) {
                System.out.println(this.getClass().toString());
            }
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
            if(stats.health > 0) {
                SoundManager.playSound("ENEMY_HIT_DAMAGE");
            }
        } else {
            SoundManager.playSound("ENEMY_HIT_NO_DAMAGE");
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
        }
    }


    public void onDeath() {
        if (game.random(1) < 0.08) {
            engine.addDrop(new Pack(x, y, tier, "HEALTH"));
        }
        if (game.random(1) < 0.04) {
            engine.addDrop(new Pack(x, y, tier, "MANA"));
        }


        //int num = (int)(game.random(tier + 1) + 1);
        //for(int i = 0; i < num; i ++ ) {
            //engine.addDrop(new Blood(x + game.random(radius), y + game.random(radius)));
        //}
        engine.addDrop(new Blood(x + game.random(radius), y + game.random(radius)));

        SoundManager.playSound("ENEMY_DEATH");
        engine.cameraShake(0.1f);
    }

    public void knockback(Projectile projectile, float knockBackMultiplier) {
        if (stats.health > 0) {
            knockbackX += projectile.direction.x * projectile.damage * knockBackMultiplier / stats.defence;
            knockbackY += projectile.direction.y * projectile.damage * knockBackMultiplier / stats.defence;
        }

    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
        this.damageSprite = Util.applyColourToImage(this.sprite, colour(200, 0, 0));
    }
}