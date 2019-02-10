package GUI.Scroll;

import GUI.Button;
import javafx.util.Pair;
import processing.core.PGraphics;

import static Sprites.Sprites.guiSprites;
import static Utility.Constants.TILE_SIZE;
import static Utility.Constants.game;

public class TabbedScroll {

    private Button[] tabButtons;
    private ScrollWindow[] tabs;
    private String[] tabNames;

    private int currentTab = 0;

    private int x, y, w, h;

    public TabbedScroll(int x, int y, int w, int h, Pair<String, ScrollElement[]>... tabInfo) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        tabs = new ScrollWindow[tabInfo.length];
        tabNames = new String[tabInfo.length];
        tabButtons = new Button[tabInfo.length];


        for(int i = 0; i < tabInfo.length; i ++) {
            tabs[i] = new ScrollWindow(x, y, w, h, tabInfo[i].getValue());
            tabNames[i] = tabInfo[i].getKey();
            tabButtons[i] = new Button(x + (i * TILE_SIZE * 2), y - TILE_SIZE, "TAB");
        }
    }

    public void show(PGraphics screen) {
        tabs[currentTab].show(screen);

        screen.textSize(TILE_SIZE/2);
        screen.textAlign(game.CENTER, game.CENTER);
        for(int i = 0; i < tabButtons.length; i ++) {
            tabButtons[i].show(screen);
            screen.fill(0, 112, 188);
            screen.text(tabNames[i], tabButtons[i].x + tabButtons[i].w/2, tabButtons[i].y + tabButtons[i].h/2);

            if(i != currentTab) {
                screen.fill(100, 50);
                screen.noStroke();
                screen.rect(tabButtons[i].x, tabButtons[i].y, tabButtons[i].w, tabButtons[i].h);
            }
        }

    }

    public void update() {
        tabs[currentTab].update();
    }

    public void handleMouse() {
        tabs[currentTab].handleMouse();

        for(int i = 0; i < tabButtons.length; i ++) {
            if(tabButtons[i].pressed()) {
                currentTab = i;
                break;
            }
        }
    }

    public void changeScrollPosition(int scrollCount) {
        tabs[currentTab].changeScrollPosition(scrollCount);
    }

}
