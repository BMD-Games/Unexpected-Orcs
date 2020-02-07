package Entities;

import File.GameFile;
import Items.*;
import Sound.SoundManager;
import Stats.PlayerStats;
import Tiles.Tile;
import Utility.Collision.AABB;
import Sprites.Sprites;
import Utility.Util;
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

    private PImage[] tintedBodySprites = new PImage[4];

    public PlayerStats stats = new PlayerStats();

    public Inventory inv = new Inventory();

    private AABB bound;

    private String prevArmour = "";

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

    public void move(double delta, boolean[] neighbours, float speedMod) {
        PVector knockback = new PVector(knockbackX, knockbackY);
        if (knockback.mag() > 15) {
            knockback.normalize();
            knockback.mult(15);
        }
        knockbackX -= knockback.x;
        knockbackY -= knockback.y;

        PVector movement = new PVector(getDX(delta, neighbours, knockback), getDY(delta, neighbours, knockback));
        movement.normalize();
        movement.mult(stats.getSpeed() * speedMod);
        dirX = movement.x;
        dirY = movement.y;
        movement.mult((float)delta);
        x += movement.x;
        y += movement.y;
        getMoving(movement);
    }

    private float getDX(double delta, boolean[] neighbours, PVector knockback) {
        float dx = (float)(delta) * ((keys[right] - keys[left]) * stats.getSpeed() + knockback.x);
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

    private float getDY(double delta, boolean[] neighbours, PVector knockback) {
        float dy = (float)(delta) * ((keys[down] - keys[up]) * stats.getSpeed() + knockback.y);
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

    public void damage(Projectile projectile) {
        if (projectile.damage > stats.getDefence()) {
            stats.health -= projectile.damage - stats.getDefence();
            SoundManager.playSound("PLAYER_HURT" + game.str(game.ceil(game.random(3))));
            knockbackX += projectile.direction.x * projectile.damage / stats.defence;
            knockbackY += projectile.direction.y * projectile.damage / stats.defence;
            engine.cameraShake(0.075f, 0.02f);
        } else {
            SoundManager.playSound("ENEMY_HIT_NO_DAMAGE");
        }
    }

    public void update(double delta, boolean[] neighbours, float speedMod) {
        ang = game.atan2(game.mouseY - game.height/2, game.mouseX - (game.width/2 + GUI_WIDTH/2));

        String name = currentArmour() == null ? "" : currentArmour().name;

        if(!name.equals(prevArmour)) {
            prevArmour = name;
            if (!name.equals("")) {
                int avg = Util.averageColour(currentArmour().sprite);

                tintedBodySprites = new PImage[4];
                tintedBodySprites[0] = Util.replaceColour(Sprites.charSprites.get(bodySprites[0]), game.color(255, 255, 255), avg);
                tintedBodySprites[1] = Util.replaceColour(Sprites.charSprites.get(bodySprites[1]), game.color(255, 255, 255), avg);
                tintedBodySprites[2] = Util.replaceColour(Sprites.charSprites.get(bodySprites[2]), game.color(255, 255, 255), avg);
                tintedBodySprites[3] = Util.replaceColour(Sprites.charSprites.get(bodySprites[3]), game.color(255, 255, 255), avg);
            }
        }
        if (inv.active[1] != null ) {
            updateCooldown(delta);
        }

        ability();
        getFacing();
        move(delta, neighbours, speedMod);
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
        bodySprite = prevArmour.equals("") ? Sprites.charSprites.get(bodySprites[0]): tintedBodySprites[0];
        if (direction.y == 0) {
            if (direction.x > 0) {
                bodySprite = prevArmour.equals("") ? Sprites.charSprites.get(bodySprites[3]): tintedBodySprites[3];
            } else if (direction.x < 0) {
                bodySprite = prevArmour.equals("") ? Sprites.charSprites.get(bodySprites[2]): tintedBodySprites[2];
            }
        } else if (direction.y < 0) {
            bodySprite = prevArmour.equals("") ? Sprites.charSprites.get(bodySprites[1]): tintedBodySprites[1];
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
