package Items.Weapons;

import Items.Weapon;
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
        this.accuracy = 0.03f;
        this.bulletSpeed = 10;
        this.bulletSprite = Sprites.projectileSprites.get("GREENROD");

    }

    @Override
    public void playSound() {
        // soundFiles.get("WHOOSH").play();
    }
}