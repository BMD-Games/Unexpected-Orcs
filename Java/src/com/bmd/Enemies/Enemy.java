package com.bmd.Enemies;

import com.bmd.Entities.Projectile;
import com.bmd.Levels.Level;
import com.bmd.Util.AABB;
import com.bmd.Util.PVector;
import com.bmd.Util.Pair;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public interface Enemy {

    /* Enemies need to update on tics */
    public boolean update(double delta);

    /* Displays enemy to screen */
    public void show(Canvas canvas, PVector renderOffset);

    /* This mob takes damage */
    public void damage(int amount, ArrayList<Pair> statusEffects);

    public void damage(int amount);

    /* Drop what on death */
    public void onDeath();

    /* Checks collision with point */
    public boolean pointCollides(float pointX, float pointY);

    /* Checks collision with point */
    public boolean lineCollides(float lineX1, float lineY1, float lineX2, float lineY2);

    /* Checks collision with area  */
    public boolean AABBCollides(AABB box);

    /* Checks if mob collides with any walls */
    public boolean validPosition(Level level, float xPos, float yPos);

    public void knockback(Projectile projectile);
}