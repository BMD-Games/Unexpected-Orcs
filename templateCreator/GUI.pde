int buff = 5;
int columns = 3;

int guiItemSize = (GUI_WIDTH - (buff * (columns + 1)))/columns;

float scrollOffset = 0;

ArrayList<Button> buttons = new ArrayList<Button>();

class Button {
  
  int row, col, sprite;
  
  Button(int row, int col, int sprite) {
    this.row = row;
    this.col = col;
    this.sprite = sprite;
  }
  
  void show() {
    pg.image(tileSprites.get(tileNames[sprite]), getColPos(col), getRowPos(row), guiItemSize, guiItemSize);
  }
  
  void pressed() {
    if(mouseInBox(getColPos(col), getRowPos(row), guiItemSize, guiItemSize)) {
      tile = sprite;
    }
  }  
}

void initMenu() {
  int row = 0;
  int col = 0;
  for(int i = 0; i < tileNames.length; i ++) {
    buttons.add(new Button(row, col, i));
    col ++;
    if(col >= columns) {
      col = 0;
      row ++;
    }
  }
}

void checkMenu() {
  for(Button button : buttons) {
    button.pressed();
  }
}
  

void drawMenu() {
  for(Button button : buttons) {
    button.show();
  }  
  pg.image(tileSprites.get(tileNames[tile]), (width - GUI_WIDTH) + 10, 10, SPRITE_SIZE * MAX_SCALE, SPRITE_SIZE * MAX_SCALE);
  pg.fill(255);
  pg.text(tileNames[tile], width - GUI_WIDTH + 10, SPRITE_SIZE * MAX_SCALE + 25);
  if(tileSolid.get(tileNames[tile])) {
    pg.text("Wall tile", width - GUI_WIDTH + 10, SPRITE_SIZE * MAX_SCALE + 35);
  } else {
    pg.text("Floor tile", width - GUI_WIDTH + 10, SPRITE_SIZE * MAX_SCALE + 35);
  }
}

float getRowPos(int row) {
  return row * guiItemSize + ((row + 1) * buff) + SPRITE_SIZE * MAX_SCALE + 40;
}

float getColPos(int col) {
  return col * guiItemSize + ((col + 1) * buff) + (width - GUI_WIDTH);
}
