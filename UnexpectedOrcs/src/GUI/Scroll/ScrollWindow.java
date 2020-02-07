package GUI.Scroll;

import Utility.Util;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class ScrollWindow {

    private int x, y, w, h;
    public int scrollPosition = 0;
    public int maxScrollPosition;
    private int buffer = 10;
    public ScrollElement[] scrollElements;
    private boolean mousePressedInScroll = false;
    private boolean mousePressedInPrev = false;
    public int selectedElement = -1;

    public ScrollWindow(int x, int y, int w, int h, ScrollElement[] scrollElements) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.scrollElements = scrollElements;

        calcMaxHeight();
    }

    private void calcMaxHeight() {

        int maxHeight = 0;
        for (ScrollElement scrollElement : scrollElements) {
            maxHeight += scrollElement.h + buffer;
        }
        this.maxScrollPosition = maxHeight - this.h + buffer;
    }


    public void setScrollElements(ScrollElement[] elements) {
        this.scrollElements = elements;
        calcMaxHeight();
    }


    public void show(PGraphics screen) {

        int currentHeight = buffer;

        for (int i = 0; i < scrollElements.length; i++) {
            ScrollElement scrollElement = scrollElements[i];
            if (currentHeight + scrollElement.h >= scrollPosition || currentHeight <= scrollPosition) {
                scrollElement.show(screen, x + buffer, y + currentHeight - scrollPosition, w - 4 * buffer, i == selectedElement);
            }
            currentHeight += scrollElement.h + buffer;
        }

        screen.noStroke();
        screen.fill(0, 0);
        screen.blendMode(game.REPLACE);
        screen.rect(0, 0, screen.width, y + buffer);
        screen.rect(0, 0, x, screen.height);
        screen.rect(0, y + h - buffer, screen.width, screen.height - y - h + 2 * buffer);
        screen.rect(x + w, 0, screen.width - x - w, screen.height);
        screen.blendMode(game.BLEND);

        screen.noStroke();
        screen.fill(100);
        screen.rect(x + w - 2 * buffer, y, 2 * buffer, h);

        float barHeight =  this.h * this.h/ (float)(this.h + maxScrollPosition);
        if (barHeight > h) {
            barHeight = h;
        }
        screen.fill(200);
        screen.rect(x+ w - 2 * buffer, y + scrollPosition / (float)maxScrollPosition * (h - barHeight), 2 * buffer, barHeight);

        screen.noFill();
        screen.stroke(88);
        screen.strokeWeight(4);
        screen.rect(x, y, w, h);
        screen.strokeWeight(1);
    }

    public void changeScrollPosition(int scrollCount) {
        if (maxScrollPosition > 0) {
            scrollPosition = game.constrain(scrollPosition + scrollCount, 0, maxScrollPosition);
        }
    }

    public void update() {
        if ((game.mousePressed && Util.pointInBox(game.mouseX, game.mouseY, x + w - 2 * buffer, y, 2 * buffer, h) && !mousePressedInPrev)) {
            mousePressedInScroll = true;
        }
        if (mousePressedInScroll) {
            float barHeight =  this.h * this.h/ (float)(this.h + maxScrollPosition);
            scrollPosition = (int)game.map(game.mouseY - y - barHeight/2, 0, h - barHeight, 0, maxScrollPosition);
            // scrollPosition = (int)((mouseY - h) * h / (float)maxScrollPosition);
            scrollPosition = game.constrain(scrollPosition, 0, maxScrollPosition);
        }
        if (!game.mousePressed) {
            mousePressedInScroll = false;
        }
        mousePressedInPrev = game.mousePressed;
    }

    public void handleMouse() {

        if (Util.pointInBox(game.mouseX, game.mouseY, x, y, w, h)) {

            for (int i = 0; i < scrollElements.length; i++) {
                ScrollElement selected = scrollElements[i];
                if(!showConfirmation && Util.pointInBox(game.mouseX, game.mouseY, selected.x, selected.y, selected.w, selected.h)) {
                    selectedElement = i;
                    selected.handleMouse();
                    break;
                }
            }
        }
    }
}