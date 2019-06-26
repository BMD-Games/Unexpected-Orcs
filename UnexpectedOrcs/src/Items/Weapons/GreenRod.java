package Items.Weapons;

import Items.Weapon;
import Sound.SoundManager;
import Utility.Pair;
import Sprites.Sprites;

import java.util.ArrayList;

public class GreenRod  extends Weapon {

    public GreenRod() {
        super("GREENROD", "Green Rod", new ArrayList<Pair>());
        this.damage = 5;
        this.fireRate = 0.5f;
        this.numBullets = 1;
        this.range = 8;
        this.spread = 0.03f;
        this.bulletSpeed = 10;
        this.bulletSprite = Sprites.projectileSprites.get("GREENROD");
    }

    @Override
    public void playSound() {
        SoundManager.playSound("WEAPON_FLYBY");
    }
}