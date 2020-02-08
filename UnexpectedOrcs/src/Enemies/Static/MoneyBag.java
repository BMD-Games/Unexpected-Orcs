package Enemies.Static;

import Enemies.StaticEnemy;
import Entities.Drops.StatOrb;
import Sound.SoundManager;
import Sprites.AnimatedSprite;
import Stats.PlayerStats;
import Stats.StatType;
import Utility.Collision.CircleObject;
import Utility.Pair;

import java.util.ArrayList;

import static Sprites.Sprites.dropSprites;
import static Utility.Constants.engine;
import static Utility.Constants.game;

public class MoneyBag extends StaticEnemy implements CircleObject {


    private boolean opened = false;

    public MoneyBag(float x, float y) {
        super(x, y, new AnimatedSprite(dropSprites.get("POT"), dropSprites.get("POT_OPEN")), true);
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
        StatType stat = StatType.randomStat();
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
