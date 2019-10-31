package Enemies;

import Entities.Drops.StatOrb;
import Sound.SoundManager;
import Sprites.AnimatedSprite;
import Stats.PlayerStats;
import Utility.Collision.CircleObject;
import Utility.Pair;

import java.util.ArrayList;

import static Sprites.Sprites.dropSprites;
import static Utility.Constants.engine;
import static Utility.Constants.game;

public class MoneyBag extends StaticEnemy implements CircleObject {


    private boolean opened = false;

    public MoneyBag(float x, float y) {
        super(x, y, new AnimatedSprite(dropSprites.get("MONEY_BAG_CLOSED"), dropSprites.get("MONEY_BAG_OPEN")), true);
    }


    @Override
    public void damage(int amount, ArrayList<Pair> statusEffects) {
        damage(amount);
    }

    @Override
    public void damage(int amount) {
        sprites.setCurrentSprite(1);
        if(!opened) {
            openBag();
            opened = true;
            interactWithBullets = false;
        }
    }

    private void openBag() {
        SoundManager.playSound("COIN");
        String stat = PlayerStats.STATS[(int)game.random(PlayerStats.STATS.length)];
        engine.addDrop(new StatOrb(x, y, engine.player.stats.getMedianTeir(stat), stat));
    }

    @Override
    public void onDeath() {
    }

    @Override
    public float getRadius() {
        return 0.25f;
    }
}
