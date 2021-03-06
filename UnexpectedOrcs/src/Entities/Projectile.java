package Entities;

import Utility.StatusEffect;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static Utility.Constants.*;

public class Projectile {

    public PVector direction;
    private float startX, startY, speed;
    public float x, y, px, py, range;
    public int damage;
    private PImage sprite;
    public ArrayList<StatusEffect> statusEffects;
    public float knockBackMultiplier = 1;


    public Projectile(float x, float y, PVector direction, float speed, float range, int damage, PImage sprite, ArrayList<StatusEffect> statusEffects) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.direction = direction;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.sprite = sprite;
        this.statusEffects = statusEffects;
    }

    public Projectile(float x, float y, PVector direction, float speed, float range, int damage, PImage sprite) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.direction = direction;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.sprite = sprite;
        this.statusEffects = new ArrayList<StatusEffect>();
    }

    public Projectile(float x, float y, PVector direction, float speed, float range, int damage, PImage sprite, ArrayList<StatusEffect> statusEffects, float knockBackMultiplier) {
        this(x, y, direction, speed, range, damage, sprite, statusEffects);
        this.knockBackMultiplier = knockBackMultiplier;
    }

    public void update(double delta) {
        //need to add speed * direction * delta to the x and y
        px = x;
        py = y;
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;
    }

    public void show(PGraphics screen, PVector renderOffset) {
        screen.pushMatrix();
        screen.translate(x * TILE_SIZE - renderOffset.x, y * TILE_SIZE - renderOffset.y);
        screen.rotate(game.atan2(direction.y, direction.x));
        screen.image(sprite, -sprite.width * SCALE/2, -sprite.height * SCALE/2, sprite.width * SCALE, sprite.height * SCALE);
        screen.popMatrix();
    }

    public boolean alive() {
        if(game.dist(startX, startY, x, y) >= range) return false;
        return true;
    }

}