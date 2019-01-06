package com.bmd.Engine;

import com.bmd.App.Graphics;
import com.bmd.App.Main;
import com.bmd.App.State;
import com.bmd.Enemies.Enemy;
import com.bmd.Enemies.StandardEnemy;
import com.bmd.Entities.*;
import com.bmd.File.GameFile;
import com.bmd.Items.Item;
import com.bmd.Items.Weapon;
import com.bmd.Levels.Dungeons.Cave;
import com.bmd.Levels.Level;
import com.bmd.Player.Player;
import com.bmd.Tiles.Tiles;
import com.bmd.Util.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Engine {
    /**
     This class is used to update and track all the game objects (eg player, monsters, levels)
     **/
    public Level currentLevel;

    public ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
    public ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();

    private ArrayList<Drop> drops = new ArrayList<Drop>();
    private ArrayList<Text> text = new ArrayList<Text>();

    public int closestBag = -1;
    public int closestPortal = -1;
    private float closestBagDist = (float)Double.POSITIVE_INFINITY;
    private float closestPortalDist = (float)Double.POSITIVE_INFINITY;

    private static PVector camera = new PVector(0, 0);

    private Canvas canvas;
    private GraphicsContext screen;

    private static int width, height;

    public Player player;

    public Main main;

    public Engine(Main main) {
        this.main = main;
        //Can initialise stuff here (eg generate the first cave)
        currentLevel = new Cave(); //Cave();//CircleDungeon(); //GrassDungeon(); //CellarDungeon(); //

        player = new Player(currentLevel.start.x + 0.5f, currentLevel.start.y + 0.5f);

        width = Main.width - Util.GUI_WIDTH;
        height = Main.height;

        canvas = new Canvas(width, height);
        screen = canvas.getGraphicsContext2D();
    }

    public void update(double delta) {
        //updates all game objects

        if (Main.mousePressed && !Main.gui.inMenu) handleMouse();

        player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
        if (player.stats.getHealth() <= 0) {
            //absolutely get hack3d loser
            //setState("DEAD");
        }
        updateCamera(player.x, player.y);
        currentLevel.update(canvas, camera.x, camera.y);

        updateProjectiles(delta);
        updateEnemies(delta, player.x, player.y);
        updateDrops(delta, player.x, player.y);

        updateText(delta);
    }

    public void show() {
        currentLevel.show(canvas, getRenderOffset());

        for (Drop drop : drops) {
            drop.show(canvas, getRenderOffset());
        }

        ArrayList<Integer> chunks = currentLevel.getChunks((int)player.x, (int)player.y);
        for (int i = 0; i < chunks.size(); i ++) {
            for (int j = 0; j < currentLevel.enemies[chunks.get(i)].size(); j ++) {
                currentLevel.enemies[chunks.get(i)].get(j).show(canvas, getRenderOffset());
            }
        }

        for (Projectile projectile : enemyProjectiles) {
            projectile.show(canvas, getRenderOffset());
        }
        for (Projectile projectile : playerProjectiles) {
            projectile.show(canvas, getRenderOffset());
        }

        player.show(canvas, getRenderOffset());

        for (Text txt : text) {
            txt.show(canvas, getRenderOffset());
        }
        Graphics.image(Main.gc, Util.getImage(canvas), Util.GUI_WIDTH, 0);
    }

    public static PVector getRenderOffset() {
        //gets the position of the camera relative to the centre of the screen
        return new PVector(camera.x * Tiles.TILE_SIZE - (width/2), camera.y * Tiles.TILE_SIZE - (height/2));
    }

    public void handleMouse() {
        //handle mouse released
        //need to do something like add(player.getCurrentWeapon().newProjectile());
        // PVector.fromAngle(player.ang)
        Weapon weapon = player.currentWeapon();
        if (weapon != null) {

            if (player.stats.fireTimer >= weapon.fireRate * player.stats.getFireRate()) {
                weapon.playSound();

                ArrayList<Pair> projectileStats = weapon.statusEffects == null ? new ArrayList<Pair>() : weapon.statusEffects;
                projectileStats.addAll(player.inv.currentScroll().statusEffects);

                for (int i = 0; i < weapon.numBullets; i++) {
                    playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + Util.random(-weapon.accuracy, weapon.accuracy)),
                            weapon.bulletSpeed, weapon.range, weapon.damage + player.stats.getAttack(), weapon.bulletSprite, projectileStats));
                }
                player.stats.fireTimer = 0;
            }
        }
    }

    private void updateCamera(float x, float y) {
        camera.x = x;
        camera.y = y;
    }

    /*private void showCamera() {
        fill(0, 0, 255);
        ellipse(camera.x * Tiles.TILE_SIZE - getRenderOffset().x, camera.y * Tiles.TILE_SIZE - getRenderOffset().y, 5, 5);
    }*/

    private void updateProjectiles(double delta) {
        //---enemy projectiles
        Projectile projectile;
        for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {
            projectile = enemyProjectiles.get(i);
            projectile.update(delta);
            if (Rectangle.lineCollides(projectile.x, projectile.y, projectile.px, projectile.py, player.x, player.y, player.w, player.h)) {
                player.damage(projectile.damage);
                enemyProjectiles.remove(i);
                continue;
            }
            if (projectileIsDead(projectile)) { //if the projectile is dead
                enemyProjectiles.remove(i); //remove projectile
            }
        }

        //---player projectiles
        for (int i = playerProjectiles.size() - 1; i >= 0; i--) {
            projectile = playerProjectiles.get(i);
            projectile.update(delta);
            if (projectileIsDead(projectile)) { //if the projectile is dead
                playerProjectiles.remove(i); //remove projectile
                break;
            }
            int chunk = currentLevel.getChunk((int)projectile.x, (int)projectile.y);
            for (Enemy enemy : currentLevel.enemies[chunk]) {
                if (enemy.lineCollides(projectile.x, projectile.y, projectile.px, projectile.py)) {
                    enemy.damage(projectile.damage, projectile.statusEffects);
                    enemy.knockback(projectile);
                    playerProjectiles.remove(i);
                    break;
                }
            }
        }
    }

    private void updateEnemies(double delta, float px, float py) {
        ArrayList<Integer> chunks = currentLevel.getChunks((int)player.x, (int)player.y);
        for (int i = 0; i < chunks.size(); i ++) {
            for (int j = currentLevel.enemies[chunks.get(i)].size() - 1; j >= 0; j --) {
                StandardEnemy enemy = (StandardEnemy)currentLevel.enemies[chunks.get(i)].get(j);
                if (!enemy.update(delta)) { //if update function returns false, the enemy is dead
                    enemy.onDeath();
                    currentLevel.enemies[chunks.get(i)].remove(j); //remove enemy
                } else if (currentLevel.getChunk((int)enemy.x, (int)enemy.y)  != chunks.get(i)) {
                    currentLevel.enemies[chunks.get(i)].remove(j);
                    currentLevel.addEnemy(enemy);
                }
            }
        }
    }

    private boolean projectileIsDead(Projectile proj) {
        boolean hitWall = currentLevel.getTile((int)proj.x, (int)proj.y) <= Tiles.WALL;
        return !proj.alive() || hitWall;
    }

    private void updateDrops(double delta, float px, float py) {
        closestBag = -1;
        closestPortal = -1;
        closestBagDist = (int)Double.POSITIVE_INFINITY;
        closestPortalDist = (int)Double.POSITIVE_INFINITY;

        for (int i = drops.size() - 1; i >= 0; i --) {
            //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
            if (!drops.get(i).update(delta, player.x, player.y)) { //if update function returns false, the drop is dead
                drops.remove(i); //remove drop
                continue;
            } else if (drops.get(i) instanceof ItemBag) {
                float dist = drops.get(i).getDist(px, py);
                if (drops.get(i).inRange(px, py) && dist < closestBagDist) {
                    closestBag = i;
                    closestBagDist = dist;
                }
            } else if (drops.get(i) instanceof Portal) {
                float dist = drops.get(i).getDist(px, py);
                if (drops.get(i).inRange(px, py) && dist < closestPortalDist) {
                    closestPortal = i;
                    closestPortalDist = dist;
                }
            }
        }
    }

    private void updateText(double delta) {
        for (int i = text.size() - 1; i >= 0; i --) {
            if (!text.get(i).update(delta)) {
                text.remove(i);
            }
        }
    }


    public void addDrop(Drop drop) {
        drops.add(drop);
    }

    public void clearDrops() {
        drops = new ArrayList<Drop>();
    }

    public void addText(String cooldown, float xp, float yp, float life, Color c) {
        text.add( new Text(cooldown, xp, yp, life, c));
    }

    public Item[] getClosestBagItems() {
        if (closestBag >= 0) try {
            return ((ItemBag)drops.get(closestBag)).items;
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public ItemBag getClosestBag() {
        if (closestBag >= 0) try {
            return (ItemBag)drops.get(closestBag);
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public Portal getClosestPortal() {
        if (closestPortal >= 0) try {
            return (Portal)drops.get(closestPortal);
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public void enterClosestPortal() {
        //empty drops, enemies etc
        Main.setState(State.LOADING);
        Main.loadMessage = "Generating " + getClosestPortal().name;
        //thread("loadClosestPortal");
        loadClosestPortal();
    }

    public void loadClosestPortal() {
        currentLevel = getClosestPortal().getLevel();
        clearDrops();
        player.x = currentLevel.start.x;
        player.y = currentLevel.start.y;
        GameFile.saveStats(Main.loadedPlayerName);
        Main.setState(State.PLAYING);
    }

    public static PVector screenToTileCoords(float x, float y) {
        x -= Util.GUI_WIDTH; //remove gui offset
        PVector renderOff = Main.engine.getRenderOffset();
        return new PVector((x + renderOff.x)/Tiles.TILE_SIZE, (y + renderOff.y)/Tiles.TILE_SIZE);
    }

    public static float screenToTileCoordX(float x) {
        x -= Util.GUI_WIDTH; //remove gui offset
        PVector renderOff = getRenderOffset();
        return (x + renderOff.x)/Tiles.TILE_SIZE;
    }

    public static float screenToTileCoordY(float y) {
        PVector renderOff = getRenderOffset();
        return (y + renderOff.y)/Tiles.TILE_SIZE;
    }

    public static float tileToScreenCoordX(float x) {
        PVector renderOff = getRenderOffset();
        return (x  * Tiles.TILE_SIZE) - renderOff.x + Util.GUI_WIDTH;
    }

    public static float tileToScreenCoordY(float y) {
        PVector renderOff = getRenderOffset();
        return (y  * Tiles.TILE_SIZE) - renderOff.y;
    }
}