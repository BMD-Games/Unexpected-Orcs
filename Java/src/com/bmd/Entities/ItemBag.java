package com.bmd.Entities;

import com.bmd.Items.Item;
import com.bmd.Sprites.Sprites;

public class ItemBag extends Drop {

    public Item[] items = new Item[4];

    public ItemBag(float x, float y, int tier) {
        super(x, y, 0.5f, 60);
        if(tier > 2) tier = 2;
        this.sprite = Sprites.dropSprites.get("BAG_" + tier % 3);
    }

    public Item takeItem(int pos) {
        if(pos >= items.length) return null;
        else {
            Item item = items[pos];
            items[pos] = null;
            checkEmpty();
            return item;
        }
    }

    @Override
    public boolean update(double delta, float px, float py) {
        checkEmpty();
        return super.update(delta, px, py);
    }

    public boolean addItem(Item item) {
        if(item != null) alive = true;
        for(int i = 0; i < items.length; i ++) {
            if(items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    private void checkEmpty() {
        for(int i = 0; i < items.length; i ++) {
            if(items[i] != null) {
                return;
            }
        }
        alive = false;
    }

    public boolean isFull() {
        for(int i = 0; i < items.length; i ++) {
            if(items[i] == null) return false;
        }
        return true;
    }

}