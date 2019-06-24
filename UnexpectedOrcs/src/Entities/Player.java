package Entities;

import File.GameFile;
import Items.*;
import Sound.SoundManager;
import Stats.PlayerStats;
import Utility.Collision.AABB;
import Sprites.Sprites;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import static Tiles.Tiles.*;
import static Utility.Constants.*;

public class Player {
    public float x, y, w = 0.5f, h = 0.5f, knockbackX, knockbackY, dirX = 0, dirY = 0;
    public float ang;

    public float cooldownTimer = 0, textTimer;

    private PImage headSprite;
    private PImage bodySprite;
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

    public Player() {
        this.x = 0; //in tile space, not screen space;
        this.y = 0;
        bound = new AABB(x, y, w, h);
    }

    public void move(double delta, boolean[] neighbours) {
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

    private float getDX(double delta, boolean[] neighbours) {
        float dx = (float)((keys[right] - keys[left]) * (delta * stats.getSpeed()));
        int xpos = (int)x;
        int ypos = (int)y;
        if (dx < 0 && neighbours[left]) {
            AABB tile = new AABB(xpos - 1, ypos, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        } else if (dx > 0 && neighbours[right]) {
            AABB tile = new AABB(xpos + 1, ypos, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        }
        return dx;
    }

    private float getDY(double delta, boolean[] neighbours) {
        float dy = (float)((keys[down] - keys[up]) * (delta * stats.getSpeed()));
        int xpos = (int)x;
        int ypos = (int)y;
        if (dy < 0 && neighbours[up]) {
            AABB tile = new AABB(xpos, ypos - 1, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        } else if (dy > 0 && neighbours[down]) {
            AABB tile = new AABB(xpos, ypos + 1, 1, 1);
            if (bound.collidesWith(tile)) return 0;
        }
        return dy;
    }

    public void damage(int amount) {
        if (amount > stats.getDefence()) {
            stats.health -= amount - stats.getDefence();
            SoundManager.playSound("PLAYER_HURT" + game.str(game.ceil(game.random(3))));
        } else {
            SoundManager.playSound("ENEMY_HIT_NO_DAMAGE");
        }
    }

    public void update(double delta, boolean[] neighbours) {
        ang = game.atan2(game.mouseY - game.height/2, game.mouseX - (game.width/2 + GUI_WIDTH/2));

        if (inv.active[1] != null ) {
            updateCooldown(delta);
        }

        ability();
        getFacing();
        move(delta, neighbours);
        updateBound();

        inv.update();
        stats.update(delta);
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.pushMatrix();
        screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
        screen.image(headSprite, -headSprite.width * SCALE/2, -headSprite.height * SCALE/2, headSprite.width * SCALE, headSprite.height * SCALE);
        screen.image(bodySprite, -bodySprite.width * SCALE/2, -bodySprite.height * SCALE/2, bodySprite.width * SCALE, bodySprite.height * SCALE);
        screen.popMatrix();
    }

    public void updateBound() {
        bound.x = x - bound.w/2;
        bound.y = y - bound.h/2;
    }

    private void ability() {
        if (keys[ability] == 1 && inv.currentAbility() != null && inv.currentAbility().ability()) {
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
        float dir = ang + (3 * game.PI/4);
        while (dir < 0) dir += game.TAU;
        int face = (int)(dir/(game.PI/2)) % 4;
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
    public  Armour currentArmour() {
        return inv.currentArmour();
    }
    public Scroll currentScroll() { return inv.currentScroll(); }

    public Item[] active() {
        return inv.active();
    }
    public Item[] inv() {
        return inv.inv();
    }

    public void onDeath() {
        inv = new Inventory();
        GameFile.saveGame();
        stats.onDeath();
    }
}
