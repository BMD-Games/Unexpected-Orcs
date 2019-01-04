package com.bmd.Entities;

import com.bmd.Tiles.Tiles;
import com.bmd.Util.PVector;
import com.bmd.Util.Pair;
import com.bmd.Util.Util;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile {

    public PVector direction;
    private float startX, startY, speed, range;
    public float x, y, px, py;
    public int damage;
    private BufferedImage sprite;
    public ArrayList<Pair> statusEffects = new ArrayList();


    public Projectile(float x, float y, PVector direction, float speed, float range, int damage, BufferedImage sprite, ArrayList<Pair> statusEffects) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this. direction = direction;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.sprite = sprite;
        this.statusEffects = statusEffects;
    }

    public Projectile(float x, float y, PVector direction, float speed, float range, int damage, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this. direction = direction;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.sprite = sprite;
        this.statusEffects = new ArrayList();
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
        screen.translate(x * Tiles.TILE_SIZE - renderOffset.x, y * Tiles.TILE_SIZE - renderOffset.y);
        screen.rotate(Math.atan2(direction.y, direction.x));
        screen.image(sprite, -sprite.getWidth() * Util.SCALE/2, -sprite.getHeight() * Util.SCALE/2, sprite.getWidth() * Util.SCALE, sprite.getHeight() * Util.SCALE);
        screen.popMatrix();
    }

    public boolean alive() {
        if(Util.dist(startX, startY, x, y) >= range) return false;
        return true;
    }

}