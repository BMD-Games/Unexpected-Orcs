class GUI {
  /**
  This class is used for drawing and handeling all UI related screens and elements  
  **/
   
  private Button play, back, options, menu, pause;
  
  GUI() {
    //need to set buttons and whatnot here
    play = new Button (width/2 - TILE_SIZE * 2, height/2 - TILE_SIZE * 4, TILE_SIZE * 4, TILE_SIZE * 2, "Play!", 255, 50);
    back = new Button (width/2 - TILE_SIZE * 2, height/2 + TILE_SIZE * 5, TILE_SIZE * 4, TILE_SIZE * 2, "Back", 255, 50);
    menu = new Button (width/2 - TILE_SIZE * 2, height/2 + TILE_SIZE * 2, TILE_SIZE * 4, TILE_SIZE * 2, "Menu", 255, 50);
    options = new Button (width/2 - TILE_SIZE * 2, height/2 - TILE_SIZE, TILE_SIZE * 4, TILE_SIZE * 2, "Options", 255, 50);
    pause = new Button(width - 3 * TILE_SIZE, TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2, "||", 255, 50);
  }
  
  public void drawMenu() {
    //Draws the main menu
    background(255);
    play.show();
    options.show();
  }
  
  public void drawOptions() {
    //Draws the options menu
    background(255);
    back.show();
  }
  
  public void drawPaused() {
    //Draws the paused overlay
    background(50, 50);
    menu.show();
    options.show();
    play.show();
  }
  
  public void drawPause() {
    //Draws the pause button during gameplay
    pause.show();
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
  private String txt;
  private color bg, line;
  
  Button(float x, float y, float w, float h, String txt, color bg, color line) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.txt = txt;
    this.bg = bg;
    this.line = line;
  }
  
  public boolean pressed(float mX, float mY) {
    return pointInBox(mX, mY, x, y, w, h);
  }
  
  public void show() {
    fill(bg);
    stroke(line);
    rect(x, y, w, h);
    noStroke();
    fill(line);
    text(txt, x + w/2, y + h/2);
  }
  
}
