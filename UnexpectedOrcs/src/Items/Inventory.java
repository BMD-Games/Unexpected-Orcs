package Items;

import Entities.Drops.ItemBag;
import GUI.WrappedText;
import Items.Abilities.*;
import Items.Scrolls.DebuffScroll;
import Items.Weapons.GreenRod;
import Utility.Util;
import processing.core.PGraphics;

import java.io.Serializable;

import static Utility.Constants.*;
import static Utility.Constants.game;

public class Inventory implements Serializable {

    private int MAX_SIZE = 12;
    public Item[] active = new Item[4];
    public Item[] inv = new Item[MAX_SIZE];

    private final int invBuff = 5, invScale = 2, itemOffset = 1, invSize = SPRITE_SIZE * invScale + 2 * itemOffset;
    private boolean prevSelection = false, currSelection = false;
    private int b1Type, b2Type, menuType; // if inv box is in active or not
    private int b1 = -1, b2 = -1, itemOver, ACTIVE = 0, INV = 1, BAG = 2, OUT = 3;//inv box 1 and 2 for drag and swap
    private Item mouseOverItem;

    public final static int WIDTH = 4;

  /*
         _____________________
  ACTIVE:|Weap|Abil|Arm |Scrl|
         _____________________
  INVENT:|____|____|____|____|
         |____|____|____|____|
         |____|____|____|____|
  */


    public Inventory() {
        active[0] = new GreenRod();
        active[1] = new SwiftBoots();
        active[2] = new Armour("LEATHER_ARMOUR", "Plain Leather Armour", 5);
        active[3] = new DebuffScroll(new String[]{"SLOWED"});

        inv[0] = itemFactory.createRandomWeapon(4);
        inv[1] = new FireBomb();
        inv[2] = new SpellBomb();
        inv[3] = new Telescope();
        inv[4] = new Teleport();
    }

    public void swapItemsInv(int i, int j) {
        try {
            Item save = inv[i];
            inv[i] = inv[j];
            inv[j] = save;
        } catch (Exception e) {
            //rip
        }
    }

    public void swapItemsActive(int act, int in) {
        try {
            if (inv[in] != null) {
                if (act == 0 && inv[in].type != "Weapon") return;
                if (act == 1 && inv[in].type != "Ability") return;
                if (act == 2 && inv[in].type != "Armour") return;
                if (act == 3 && inv[in].type != "Scroll") return;
            }
            Item save = active[act];
            active[act] = inv[in];
            inv[in] = save;
        } catch (Exception e) {
            //rip
        }
    }

    public Item addItemInv(Item item, int pos) {
        Item old = inv[pos];
        inv[pos] = item;
        return old;
    }

    public Item addItemActive(Item item, int pos) {
        if (item != null) {
            if (pos == 0 && item.type != "Weapon") return item;
            if (pos == 1 && item.type != "Ability") return item;
            if (pos == 2 && item.type != "Armour") return item;
            if (pos == 3 && item.type != "Scroll") return item;
        }
        Item old = active[pos];
        active[pos] = item;
        return old;
    }

    public Item[] active() {
        return active;
    }

    public Item[] inv() {
        return inv;
    }

    public Weapon currentWeapon() {
        return (Weapon) active[0];
    }

    public Ability currentAbility() {
        return (Ability) active[1];
    }

    public Armour currentArmour() {
        return (Armour) active[2];
    }

    public Scroll currentScroll() {
        return (Scroll) active[3];
    }

    public void update() {
        prevSelection = currSelection;
        currSelection = game.mousePressed;

        ItemBag itemBag = engine.getClosestBag();

        if (itemOver != -1 || b1 != -1) {
            if (currSelection && !prevSelection) {
                b1 = itemOver;
                b1Type = menuType;
            }
            if (!currSelection && prevSelection) {
                b2 = itemOver;
                b2Type = menuType;
                if (b1Type == ACTIVE && b2Type == INV) { //----INV/ACTIVE
                    engine.player.inv.swapItemsActive(b1, b2);
                } else if (b2Type == ACTIVE && b1Type == INV) {
                    engine.player.inv.swapItemsActive(b2, b1);
                } else if (b1Type == INV && b2Type == INV) {
                    engine.player.inv.swapItemsInv(b1, b2);
                } else if (b1Type == INV && b2Type == BAG) { //----INV/BAG
                    Item bagItem = itemBag.takeItem(b2);
                    itemBag.addItem(engine.player.inv.addItemInv(bagItem, b1));
                } else if (b1Type == BAG && b2Type == INV) {
                    Item bagItem = itemBag.takeItem(b1);
                    itemBag.addItem(engine.player.inv.addItemInv(bagItem, b2));
                } else if (b1Type == ACTIVE && b2Type == BAG) { //-----ACTIVE/BAG
                    Item bagItem = itemBag.takeItem(b2);
                    itemBag.addItem(engine.player.inv.addItemActive(bagItem, b1));
                } else if (b1Type == BAG && b2Type == ACTIVE) {
                    Item bagItem = itemBag.takeItem(b1);
                    itemBag.addItem(engine.player.inv.addItemActive(bagItem, b2));
                } else if (b1Type == ACTIVE && b2Type == OUT && !inMenu) { //----ACTIVE/GROUND
                    if (itemBag == null || itemBag.isFull()) {
                        ItemBag newBag = new ItemBag(engine.player.x, engine.player.y, 0);
                        newBag.addItem(engine.player.inv.addItemActive(null, b1));
                        engine.addDrop(newBag);
                    } else {
                        itemBag.addItem(engine.player.inv.addItemActive(null, b1));
                    }
                } else if (b1Type == INV && b2Type == OUT && !inMenu) { //----INV/GROUND
                    if (itemBag == null || itemBag.isFull()) {
                        ItemBag newBag = new ItemBag(engine.player.x, engine.player.y, 0);
                        newBag.addItem(engine.player.inv.addItemInv(null, b1));
                        engine.addDrop(newBag);
                    } else {
                        itemBag.addItem(engine.player.inv.addItemInv(null, b1));
                    }
                }
                b1 = -1;
                b2 = -1;
            }
        } else {
            if (!currSelection && prevSelection) {
                b1 = -1;
                b2 = -1;
            }
        }
    }

    public void show(PGraphics screen, float invX, float invY) {

        Item[] items = engine.getClosestBagItems();

        drawBack(screen,items != null, items, invX, invY);

        for (int i = 0; i < engine.player.active().length; i++) {
            if (engine.player.active()[i] != null) {
                if (currSelection && b1Type == ACTIVE && b1 == i) {
                    screen.image(engine.player.active()[i].sprite, game.mouseX - invSize/2+ itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                } else {
                    screen.image(engine.player.active()[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, invBuff + invY+ itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                }
            }
        }
        int j = 0;
        for (int i = 0; i < engine.player.inv().length; i++) {
            j = (int)(i/Inventory.WIDTH);
            if (engine.player.inv()[i] != null) {
                if (currSelection && b1Type == INV && b1 == i) {
                    screen.image(engine.player.inv()[i].sprite, game.mouseX - invSize/2 + itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                } else {
                    screen.image(engine.player.inv()[i].sprite, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff) + itemOffset, 3 * invBuff + invSize + invY + j * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                }
            }
        }

        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    if (currSelection && b1Type == BAG && b1 == i) {
                        screen.image(items[i].sprite, game.mouseX - invSize/2 + itemOffset, game.mouseY - invSize/2 + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                    } else {
                        screen.image(items[i].sprite, invBuff + invX + i * (invSize + invBuff) + itemOffset, 3 * invBuff + invY + 4 * (invSize + invBuff) + itemOffset, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale);
                    }
                }
            }
        }
        if (itemOver != -1 && mouseOverItem != null) {
            mouseOverItem(game.mouseX, game.mouseY, mouseOverItem);
        }
    }

    private void drawBack(PGraphics screen, boolean showBag, Item[] items, float invX, float invY) {

        menuType = OUT;
        screen.fill(51);
        if (showBag) {
            screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, (Inventory.WIDTH + 1) * (invSize + invBuff) + invBuff * 3);
        } else {
            screen.rect(invX, invY, Inventory.WIDTH * (invSize + invBuff) + invBuff, Inventory.WIDTH * (invSize + invBuff) + invBuff * 2);
        }

        itemOver = -1;
        for (int i = 0; i < engine.player.active().length; i++) {
            screen.fill(150);
            screen.rect(invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize);
            if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + i * (invSize + invBuff), invBuff + invY, invSize, invSize)) {
                itemOver = i;
                mouseOverItem = engine.player.active()[i];
                menuType = ACTIVE;
            }
        }
        int j = 0;
        for (int i = 0; i < engine.player.inv().length; i++) {
            j = (int)(i/Inventory.WIDTH);
            screen.fill(150);
            screen.rect(invBuff + invX + (i%4) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize);
            if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + (i%Inventory.WIDTH) * (invSize + invBuff), 3 * invBuff + invSize + invY + j * (invSize + invBuff), invSize, invSize)) {
                itemOver = i;
                mouseOverItem = engine.player.inv()[i];
                menuType = INV;
            }
        }
        if (showBag) {
            for (int i = 0; i < engine.player.active().length; i++) {
                screen.fill(150);
                screen.rect(invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize);
                if (Util.pointInBox(game.mouseX, game.mouseY, invBuff + invX + i * (invSize + invBuff), 3 * invBuff + invY + 4 * (invSize + invBuff), invSize, invSize)) {
                    itemOver = i;
                    mouseOverItem = items[i];
                    menuType = BAG;
                }
            }
        }
    }

    private void mouseOverItem(float x, float y, Item item) {

        String desc = "";
        String type = item.type;
        if (type == "Weapon") {
            int fireRate = (int)(60 / ((Weapon)item).fireRate);
            desc += "Fire rate: " + fireRate + "\n";
            desc += "Range: " + ((Weapon)item).range + "\n";
            float accuracy = 1 - ((Weapon)item).accuracy;
            desc += "Accuracy: " + accuracy + "\n";
            desc += "Damage: " + ((Weapon)item).damage + "\n";
        } else if (type == "Ability") {
            desc += "Mana cost: " + ((Ability)item).manaCost + "\n";
            desc += "Cooldown: " + ((Ability)item).cooldown + "s\n";
        } else if (type == "Armour") {
            desc += "Defence: " + ((Armour)item).defence + "\n";
        } else if (type == "Scroll") {
            desc += ((Scroll)item).description;
        }
        int mouseOverWidth = 3 * GUI_WIDTH/4;
        WrappedText title = WrappedText.wrapText(item.name, mouseOverWidth - gui.buff * 4, TILE_SIZE/2);
        WrappedText subtitle = WrappedText.wrapText("Tier " + item.tier + " " + type, mouseOverWidth - gui.buff * 4, TILE_SIZE/3);
        WrappedText description = WrappedText.wrapText(desc, mouseOverWidth - gui.buff * 4, TILE_SIZE/3);

        gui.drawMouseOverText(x, y, title, subtitle, description);
    }

    public void drawCooldown(PGraphics screen, float invX, float invY) {

        if (engine.player.inv.currentAbility() != null ) {
            float percentFull = engine.player.getPercentCooldown();
            screen.fill(0, 100);
            screen.noStroke();
            screen.arc(invBuff + invX + (invSize + invBuff) + itemOffset + SPRITE_SIZE * invScale/2, invBuff + invY + itemOffset + SPRITE_SIZE * invScale/2, SPRITE_SIZE * invScale, SPRITE_SIZE * invScale, -game.PI/2, 2 * game.PI * percentFull - game.PI/2, game.PIE);
        }
    }

}
