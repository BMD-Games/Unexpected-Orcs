package com.bmd.Player;

import com.bmd.App.Graphics;
import com.bmd.App.Main;
import com.bmd.Input.Input;
import com.bmd.Items.*;
import com.bmd.Sprites.Sprites;
import com.bmd.Stats.PlayerStats;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.AABB;
import com.bmd.Util.PVector;
import com.bmd.Util.Util;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;

public class Player {

    public float x, y, w = 0.5f, h = 0.5f, knockbackX, knockbackY, dirX = 0, dirY = 0;
    public float ang;

    public float cooldownTimer = 0, textTimer;

    private BufferedImage headSprite;
    private BufferedImage bodySprite;
    private String[] headSprites = {"FACE_BACK", "FACE_RIGHT", "FACE_FRONT", "FACE_LEFT"};
    private String[] bodySprites = {"BODY_FRONT", "BODY_BACK", "BODY_LEFT", "BODY_RIGHT"};

    public PlayerStats stats = new PlayerStats();

    public Inventory inv = new Inventory();

    private AABB bound;

    public Player(float x, float y) {
        this.x = x; //in tile space, not screen space;
        this.y = y;
        bound = new AABB(x, y, w, h);
    }

    public void move(double delta, int[] neighbours) {
        PVector movement = new PVector(getDX(delta, neighbours), getDY(delta, neighbours));
        movement.normalize();
        movement.mult(stats.getSpeed());
        dirX = movement.x;
        dirY = movement.y;
        movement.mult((float)delta);
        x += movement.x;
        y += movement.y;
        getMoving(movement);
    }

    private float getDX(double delta, int[] neighbours) {
        float dx = (float)((Input.keys[Util.right] - Input.keys[Util.left]) * (delta * stats.getSpeed()));
        int xpos = (int)x;
        int ypos = (int)y;
        if (dx < 0 && neighbours[Util.left] <= Tiles.WALL) {
            AABB tile = new AABB(xpos - 1, ypos, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        } else if (dx > 0 && neighbours[Util.right] <= Tiles.WALL) {
            AABB tile = new AABB(xpos + 1, ypos, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        }
        return dx;
    }

    private float getDY(double delta, int[] neighbours) {
        float dy = (float)((Input.keys[Util.down] - Input.keys[Util.up]) * (delta * stats.getSpeed()));
        int xpos = (int)x;
        int ypos = (int)y;
        if (dy < 0 && neighbours[Util.up] <= Tiles.WALL) {
            AABB tile = new AABB(xpos, ypos - 1, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        } else if (dy > 0 && neighbours[Util.down] <= Tiles.WALL) {
            AABB tile = new AABB(xpos, ypos + 1, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        }
        return dy;
    }

    public void damage(int amount) {
        if (amount > stats.getDefence()) {
            stats.health -= amount - stats.getDefence();
        }
    }

    public void update(double delta, int[] neighbours) {
        ang = (float)Math.atan2(Input.mouseY() - Main.height/2, Input.mouseX() - (Main.width/2 + Util.GUI_WIDTH/2));

        if (inv.active[1] != null ) {
            updateCooldown(delta);
        }

        ability();
        getFacing();
        move(delta, neighbours);
        updateBound();

        stats.update(delta);
    }

    public void show(Canvas canvas, PVector renderOffset) {
        GraphicsContext screen = canvas.getGraphicsContext2D();

        screen.save();
        screen.translate(x * Tiles.TILE_SIZE - renderOffset.x, y * Tiles.TILE_SIZE - renderOffset.y);
        Graphics.image(screen, headSprite, -headSprite.getWidth()/2, -headSprite.getHeight()/2, headSprite.getWidth(), headSprite.getHeight());
        Graphics.image(screen, bodySprite, -bodySprite.getWidth()/2, -bodySprite.getHeight()/2, bodySprite.getWidth(), bodySprite.getHeight());
        screen.restore();
    }

    private void updateBound() {
        bound.x = x - bound.w/2;
        bound.y = y - bound.h/2;
    }

    private void ability() {
        if (Input.keys[Util.ability] == 1 && inv.currentAbility() != null && inv.currentAbility().ability()) {
            inv.currentAbility().makeText();
        }
    }

    public void updateCooldown(double delta) {

        cooldownTimer -= delta;
        textTimer += delta;
    }

    public float getPercentCooldown() {
        return cooldownTimer/inv.currentAbility().cooldown;
    }

    private int getFacing() {
        //gets the direction that the player is looking in
        float dir = ang + (3 * (float)Math.PI/4);
        while (dir < 0) dir += Math.PI * 2;
        int face = (int)(dir/(Math.PI/2)) % 4;
        headSprite = Sprites.charSprites.get(headSprites[face]);
        return face;
    }

    private void getMoving(PVector direction) {
        bodySprite = Sprites.charSprites.get(bodySprites[0]);
        if (direction.y == 0) {
            if (direction.x > 0) {
                bodySprite = Sprites.charSprites.get(bodySprites[3]);
            } else if (direction.x < 0) {
                bodySprite = Sprites.charSprites.get(bodySprites[2]);
            }
        } else if (direction.y < 0) {
            bodySprite = Sprites.charSprites.get(bodySprites[1]);
        }
    }

    public AABB getAABB() {
        return bound;
    }

    public Weapon currentWeapon() {
        return inv.currentWeapon();
    }
    public Ability currentSpecial() {
        return inv.currentAbility();
    }
    public Armour currentArmour() {
        return inv.currentArmour();
    }

    public Item[] active() {
        return inv.active();
    }
    public Item[] inv() {
        return inv.inv();
    }
}