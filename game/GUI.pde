class GUI {
  /**
  This class is used for drawing and handeling all UI related screens and elements  
  **/
   
  private Button play, back, options, menu, pause;
  private PGraphics screen;
  
  GUI() {
    //need to set buttons and whatnot here
    play = new Button (width/2 - TILE_SIZE, height/2 - TILE_SIZE * 2, 2, 1, "PLAY");
    options = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 0, 2, 1, "OPTIONS");
    menu = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 1, 2, 1, "MENU");
    back = new Button (width/2 - TILE_SIZE, height/2 + TILE_SIZE * 2, 2, 1, "BACK");
    pause = new Button(width - 2 * TILE_SIZE, TILE_SIZE, 1, 1, "PAUSE");
    screen = createGraphics(width, height);
  }
  
  public void clearScreen() {
    screen.background(0, 0);
  }
  
  public void drawMenu() {
    //Draws the main menu
    screen.beginDraw();
    screen.background(255);
    play.show(screen);
    options.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }
  
  public void drawOptions() {
    //Draws the options menu
    screen.beginDraw();
    screen.background(255);
    back.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }
  
  public void drawPaused() {
    //Draws the paused overlay
    screen.beginDraw();
    screen.fill(255, 5);
    screen.rect(-TILE_SIZE, -TILE_SIZE, width + TILE_SIZE, height + TILE_SIZE);
    menu.show(screen);
    options.show(screen);
    play.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }
  
  public void drawPause() {
    //Draws the pause button during gameplay
    screen.beginDraw();
    clearScreen();
    pause.show(screen);
    screen.endDraw();
    image(screen, 0, 0);
  }
  
  public void handleMouseReleased() {
    //used for checking input
    if(play.pressed(mouseX, mouseY) && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("PLAYING");
    }else if(options.pressed(mouseX, mouseY) && (STATE == "MENU" || STATE == "PAUSED")) {
      setState("OPTIONS");
    }else if(menu.pressed(mouseX, mouseY) && STATE == "PAUSED") {
      setState("MENU");
    }else if(pause.pressed(mouseX, mouseY) && STATE == "PLAYING") {
      setState("PAUSED");
    } else if(back.pressed(mouseX, mouseY)) {
      revertState();
    }
  }
  
}

class Button {
  
  private float x, y, w, h;
  private String sprite;
  
  Button(float x, float y, float w, float h, String sprite) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.sprite = sprite;
  }
  
  public boolean pressed(float mX, float mY) {
    return pointInBox(mX, mY, x, y, w * TILE_SIZE, h * TILE_SIZE);
  }
  
  public void show(PGraphics screen) {
    screen.image(sprites.get(sprite), x, y, w * TILE_SIZE, h * TILE_SIZE);
  }
  
}
