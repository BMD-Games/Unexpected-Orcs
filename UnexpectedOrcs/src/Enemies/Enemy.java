package Enemies;

import Entities.Projectile;
import Levels.Level;
import Utility.Collision.AABB;
import Utility.StatusEffect;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;

public abstract class Enemy {

    public float x, y;

    Enemy(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /* Enemies need to update on tics */
    public abstract boolean update(double delta);

    /* Displays enemy to screen */
    public abstract void show(PGraphics screen, PVector renderOffset);

    /* This mob takes damage */
    public abstract void damage(int amount, ArrayList<StatusEffect> statusEffects);

    public abstract void damage(int amount);

    /* Drop what on death */
    public abstract void onDeath();

    /* Checks collision with point */
    public abstract boolean pointCollides(float pointX, float pointY);

    /* Checks collision with point */
    public abstract boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2);

    /* Checks collision with area  */
    public abstract boolean AABBCollides(AABB box);

    /* Checks if mob collides with any walls */
    public abstract boolean validPosition(Level level, float xPos, float yPos);

    public abstract void knockback(Projectile projectile, float knockBackMultiplier);

}