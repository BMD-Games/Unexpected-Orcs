package GUI.Scroll;

import GUI.Slider.Slider;
import processing.core.PGraphics;

import static Utility.Constants.*;

public class SliderElement extends ScrollElement {

    private SliderCallback sliderCallback;
    private Slider slider  = new Slider(0, 0, TILE_SIZE * 2, false);

    public SliderElement(String title, SliderCallback sliderCallback) {
        super(title, "", TILE_SIZE * 2);
        this.sliderCallback = sliderCallback;
    }

    public SliderElement(String title, SliderCallback sliderCallback, float initialPercentage) {
        super(title, "", TILE_SIZE * 2);
        this.sliderCallback = sliderCallback;
        slider.percent = initialPercentage;
    }

    public void show(PGraphics screen, int xpos, int ypos, int w, boolean selected) {
        super.show(screen, xpos, ypos, w, selected);

        updateSlider();
        slider.x = xpos + w/2 - TILE_SIZE;
        slider.y = ypos + h/2;
        slider.show(screen);

        screen.fill(0, 112, 188);
        screen.textSize(TILE_SIZE/2);
        screen.text((int)(slider.percent * 100) + "%", xpos + w/2 + TILE_SIZE * 2, ypos + h/2);
    }

    public void updateSlider() {
        float sliderVal = slider.percent;
        slider.update();

        if(sliderVal != slider.percent) {
            sliderCallback.callback(slider.percent);
        }
    }
}
