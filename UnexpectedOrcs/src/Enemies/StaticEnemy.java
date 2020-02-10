package Enemies;

import Engine.Engine;
import Entities.Projectile;
import Levels.Level;
import Sprites.AnimatedSprite;
import Utility.Collision.*;
import Utility.StatusEffect;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static Utility.Constants.*;

public abstract class StaticEnemy extends Enemy {

    protected AnimatedSprite sprites;

    public boolean interactWithBullets;

    public StaticEnemy(float x, float y, AnimatedSprite sprites, boolean interactWithBullets) {
        super(x, y);
        this.sprites = sprites;
        this.interactWithBullets = interactWithBullets;
    }


    @Override
    public boolean update(double delta) {
        return true;
    }

    @Override
    public void show(PGraphics screen, PVector renderOffset) {
        if(!engine.currentLevel.visited((int)x, (int)y)) return;
        screen.pushMatrix();
        screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
        PImage sprite = sprites.getCurrentSprite();
        screen.image(sprite, -sprite.width * SCALE / 2, -sprite.height * SCALE / 2, sprite.width * SCALE, sprite.height * SCALE);
        screen.popMatrix();
    }

    @Override
    public abstract void damage(int amount, ArrayList<StatusEffect> statusEffects);

    @Override
    public abstract void damage(int amount);

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY) {
        if(!interactWithBullets) return false;
        if(this instanceof RectangleObject) {
            return Rectangle.pointCollides(pointX, pointY, x, y, ((RectangleObject) this).getWidth(), ((RectangleObject) this).getHeight());
        } else if(this instanceof CircleObject) {
            return Circle.pointCollides(x, y, pointX, pointY, ((CircleObject) this).getRadius());
        }
        return false;
    }

    /* Checks collision with line */
    public boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2) {
        if(!interactWithBullets) return false;
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

    @Override
    public boolean validPosition(Level level, float xPos, float yPos) {
        return false;
    }

    @Override
    public void knockback(Projectile projectile, float knockBackMultiplier) {

    }
}
