package Entities.Drops;

import Items.Item;
import Sprites.AnimatedSprite;

import static Sprites.Sprites.dropSprites;

public class Chest extends Drop {

    public Item[] items = new Item[4];
    public boolean opened = false; //Will be set to false at the beginning of each new life. Once true, items will only be able to be added.

    public Chest(float x, float y) {
        super(x, y, 0.5f, 60);
        this.sprites = new AnimatedSprite(dropSprites.get("CHEST"));
        this.activeSprite = dropSprites.get("CHEST_OPEN");
    }

    public boolean update(double delta, float px, float py) {
        this.lifeTime += delta;
        return super.update(delta, px, py);
    }

    public void addItem(Item item) {
        if(!isFull()) {
            for(int i = 0; i < items.length; i ++) {
                if(items[i] == null) {
                    items[i] = item;
                    return;
                }
            }
        } else {
            for(int i = 0; i < items.length - 1; i ++) {
                items[i] = items[i + 1];
                items[items.length-1] = item;
            }
        }
    }

    public Item takeItem(int pos) {
        if(opened || pos >= items.length) return null;
        Item item = items[pos];
        items[pos] = null;
        settleItems();
        return item;
    }

    private void settleItems() {
        //this function ensures all empty slots are at the end of the items array
        for(int i = 0; i < items.length - 1; i ++) {
            if(items[i] == null) {
                items[i] = items[i + 1];
                items[i + 1] = null;
            }
        }
    }

    public boolean isFull() {

        for(int i = 0; i < items.length; i ++) {
            if(items[i] == null) return false;
        }
        return true;
    }
}
