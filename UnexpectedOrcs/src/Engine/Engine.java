package Engine;

import Enemies.Enemy;
import Enemies.StandardEnemy;
import Entities.Drops.*;
import Entities.Player;
import Entities.Projectile;
import Entities.Text;
import Items.Item;
import Items.Weapon;
import Levels.Dungeons.Cave;
import Levels.Dungeons.TutorialDungeon;
import Levels.Level;
import Utility.Collision.Rectangle;
import Utility.Pair;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;

import java.util.ArrayList;
import java.util.HashMap;

import static Tiles.Tiles.WALL;
import static Utility.Constants.*;
import static processing.core.PConstants.P2D;

public class Engine {
    /**
     This class is used to update and track all the game objects (eg player, monsters, levels)
     **/
    private double lastUpdate = 0;

    public Level currentLevel;

    public ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
    public ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();

    private HashMap<Short, ArrayList<Drop>> drops = new HashMap<>();
    private final short ORBS = 0, BAGS = 1, PORTALS = 2, BLOOD = 3, OTHER = 4;

    private ArrayList<Text> text = new ArrayList<Text>();

    public int closestBag = -1;
    public int closestPortal = -1;
    private float closestBagDist = (float)Double.POSITIVE_INFINITY;
    private float closestPortalDist = (float)Double.POSITIVE_INFINITY;

    private PVector camera = new PVector(0, 0);

    private PGraphics screen;

    public Player player;

    private float cameraShakeDuration = 0, cameraShakeAmp = 0.05f;

    public Engine() {
        //Can initialise stuff here (eg generate the first cave)
        currentLevel = new Cave(); //Cave();//CircleDungeon();
        // GrassDungeon
        // ();// CellarDungeon(); //

        player = new Player(currentLevel.start.x + 0.5f, currentLevel.start.y + 0.5f);

        initiateScreen();
        initiateDrops();

    }

    public void update() {
        //updates all game objects

        double delta = (game.millis() - lastUpdate)/1000; //seconds passed since last update
        if (game.mousePressed && !inMenu) handleMouse();

        player.update(delta, currentLevel.getNeighbours((int)player.x, (int)player.y));
        if (player.stats.getHealth() <= 0) {
            //absolutely get hack3d loser
            //game.setState("DEAD");
            //player.onDeath();
        }
        updateCamera(delta, player.x, player.y);
        currentLevel.update(screen, camera.x, camera.y);

        updateProjectiles(delta);
        updateEnemies(delta, player.x, player.y);
        updateDrops(delta, player.x, player.y);

        updateText(delta);

        if(getClosestPortal() != null && keys[interact] == 1) {
            enterClosestPortal();
        }

        lastUpdate = game.millis();
    }

    public void updateMillis() {
        lastUpdate = game.millis();
    }

    public void show() {
        outlineShader.set("scale", SCALE);
        outlineShader.set("colour", 0f, 0f, 0f);
        screen.beginDraw();
        currentLevel.show(screen, getRenderOffset());
        //screen.background(0, 0);
        screen.shader(outlineShader);

        showDrops();

        screen.resetShader();
        for (Projectile projectile : playerProjectiles) {
            projectile.show(screen, getRenderOffset());
        }

        screen.shader(outlineShader);
        for (Projectile projectile : enemyProjectiles) {
            projectile.show(screen, getRenderOffset());
        }

        ArrayList<Integer> chunks = currentLevel.getChunks((int)player.x, (int)player.y);
        for (int i = 0; i < chunks.size(); i ++) {
            for (int j = 0; j < currentLevel.enemies[chunks.get(i)].size(); j ++) {
                currentLevel.enemies[chunks.get(i)].get(j).show(screen, getRenderOffset());
            }
        }

        player.show(screen, getRenderOffset());


        screen.resetShader();
        for (Text txt : text) {
            txt.show(screen, getRenderOffset());
        }
        screen.endDraw();
        game.image(screen, GUI_WIDTH, 0);
    }

    public PVector getRenderOffset() {
        //gets the position of the camera relative to the centre of the screen
        return new PVector(camera.x * TILE_SIZE - (screen.width/2), camera.y * TILE_SIZE - (screen.height/2));
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
                if(player.currentScroll() != null) {
                    projectileStats.addAll(engine.player.currentScroll().statusEffects);
                }
                for (int i = 0; i < weapon.numBullets; i++) {
                    playerProjectiles.add(new Projectile(player.x, player.y, PVector.fromAngle(player.ang + game.random(-weapon.accuracy, weapon.accuracy)),
                            weapon.bulletSpeed, weapon.range, weapon.damage + player.stats.getAttack(), weapon.bulletSprite, projectileStats));
                }
                player.stats.fireTimer = 0;
            }
        }
    }


    public void resize() {
        initiateScreen();
        currentLevel.resizeCanvas();
    }

    private void initiateScreen() {
        screen = game.createGraphics(game.width - GUI_WIDTH, game.height, P2D);
        screen.beginDraw();
        screen.noSmooth();
        ((PGraphicsOpenGL)screen).textureSampling(3);
        screen.endDraw();
    }

    public void initiateDrops() {
        drops.put(ORBS, new ArrayList<Drop>());
        drops.put(PORTALS, new ArrayList<Drop>());
        drops.put(BAGS, new ArrayList<Drop>());
        drops.put(BLOOD, new ArrayList<Drop>());
        drops.put(OTHER, new ArrayList<Drop>());
    }

    private void updateCamera(double delta, float x, float y) {
        float offX = 0;
        float offY = 0;
        if(cameraShakeDuration > 0) {
            offX = game.random(cameraShakeAmp);
            offY = game.random(cameraShakeAmp);
            cameraShakeDuration -= delta;
        }
        camera.x = x + offX;
        camera.y = y + offY;
    }

    private void showDrops() {
        PVector renderOff = getRenderOffset();

        for(Drop drop : drops.get(OTHER)) {
            drop.show(screen, renderOff);
        }
        for(Drop drop : drops.get(BLOOD)) {
            drop.show(screen, renderOff);
        }
        for(Drop drop : drops.get(PORTALS)) {
            drop.show(screen, renderOff);
        }
        for(Drop drop : drops.get(BAGS)) {
            drop.show(screen, renderOff);
        }
        for(Drop drop : drops.get(ORBS)) {
            drop.show(screen, renderOff);
        }
    }

    public void cameraShake(float duration) {
        if(cameraShakeDuration < duration) {
            cameraShakeDuration = duration;
        }
    }

    private void showCamera() {
        game.fill(0, 0, 255);
        game.ellipse(camera.x * TILE_SIZE - getRenderOffset().x, camera.y * TILE_SIZE - getRenderOffset().y, 5, 5);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.player.x = currentLevel.start.x + 0.5f;
        this.player.y = currentLevel.start.y + 0.5f;
        this.player.updateBound();
    }

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
                    enemy.knockback(projectile, projectile.knockBackMultiplier);
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
        boolean hitWall = currentLevel.getTile((int)proj.x, (int)proj.y) <= WALL;
        return !proj.alive() || hitWall;
    }

    private void updateDrops(double delta, float px, float py) {
        closestBag = -1;
        closestPortal = -1;
        closestBagDist = (int)Double.POSITIVE_INFINITY;
        closestPortalDist = (int)Double.POSITIVE_INFINITY;

        for(short key : drops.keySet()) {
            for (int i = drops.get(key).size() - 1; i >= 0; i--) {
                //---> this might need to be a better datastructure (such as quad tree) to only show necessary enemies
                if (!drops.get(key).get(i).update(delta, player.x, player.y)) { //if update function returns false, the drop is dead
                    drops.get(key).remove(i); //remove drop
                    continue;
                } else if (drops.get(key).get(i) instanceof ItemBag) {
                    float dist = drops.get(key).get(i).getDist(px, py);
                    if (drops.get(key).get(i).inRange(px, py) && dist < closestBagDist) {
                        closestBag = i;
                        closestBagDist = dist;
                    }
                } else if (drops.get(key).get(i) instanceof Portal) {
                    float dist = drops.get(key).get(i).getDist(px, py);
                    if (drops.get(key).get(i).inRange(px, py) && dist < closestPortalDist) {
                        closestPortal = i;
                        closestPortalDist = dist;
                    }
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
        if(drop == null) return;
        if(drop instanceof StatOrb || drop instanceof Pack) {
            drops.get(ORBS).add(drop);
        } else if(drop instanceof Portal) {
            drops.get(PORTALS).add(drop);
        } else if(drop instanceof ItemBag) {
            drops.get(BAGS).add(drop);
        } else if(drop instanceof Blood) {
            drops.get(BLOOD).add(drop);
        } else {
            drops.get(OTHER).add(drop);
        }
    }


    public void addText(String cooldown, float xp, float yp, float life, int c) {
        text.add( new Text(cooldown, xp, yp, life, c));
    }

    public Item[] getClosestBagItems() {
        if (closestBag >= 0) try {
            return ((ItemBag)drops.get(BAGS).get(closestBag)).items;
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public ItemBag getClosestBag() {
        if (closestBag >= 0) try {
            return (ItemBag)drops.get(BAGS).get(closestBag);
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public Portal getClosestPortal() {
        if (closestPortal >= 0) try {
            return (Portal)drops.get(PORTALS).get(closestPortal);
        }
        catch(Exception e) {
            return null;
        }
        return null;
    }

    public void enterClosestPortal() {
        //empty drops, enemies etc
        game.setState("LOADING");
        loadMessage = "Generating " + getClosestPortal().name;
        game.thread("loadClosestPortal");
    }

    public void reset(boolean tutorial) {
        initiateDrops();
        updateMillis();
        if(tutorial) {
            currentLevel = new TutorialDungeon();
        } else {
            currentLevel = new Cave();
        }
    }

    public static float screenToTileCoordX(float x) {
        x -= GUI_WIDTH; //remove gui offset
        PVector renderOff = engine.getRenderOffset();
        return (x + renderOff.x)/TILE_SIZE;
    }

    public static float screenToTileCoordY(float y) {
        PVector renderOff = engine.getRenderOffset();
        return (y + renderOff.y)/TILE_SIZE;
    }

    public static float tileToScreenCoordX(float x) {
        PVector renderOff = engine.getRenderOffset();
        return (x  * TILE_SIZE) - renderOff.x + GUI_WIDTH;
    }

    public static float tileToScreenCoordY(float y) {
        PVector renderOff = engine.getRenderOffset();
        return (y  * TILE_SIZE) - renderOff.y;
    }
}
