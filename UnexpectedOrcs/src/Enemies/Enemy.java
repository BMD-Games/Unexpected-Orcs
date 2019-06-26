package Enemies;

import Entities.Projectile;
import Levels.Level;
import Utility.Collision.AABB;
import Utility.Pair;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;

public interface Enemy {

    /* Enemies need to update on tics */
    boolean update(double delta);

    /* Displays enemy to screen */
    void show(PGraphics screen, PVector renderOffset);

    /* This mob takes damage */
    void damage(int amount, ArrayList<Pair> statusEffects);

    void damage(int amount);

    /* Drop what on death */
    void onDeath();

    /* Checks collision with point */
    boolean pointCollides(float pointX, float pointY);

    /* Checks collision with point */
    boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2);

    /* Checks collision with area  */
    boolean AABBCollides(AABB box);

    /* Checks if mob collides with any walls */
    boolean validPosition(Level level, float xPos, float yPos);

    void knockback(Projectile projectile, float knockBackMultiplier);

}