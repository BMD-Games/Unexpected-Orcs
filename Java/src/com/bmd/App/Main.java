package com.bmd.App;

import com.bmd.Engine.Engine;
import com.bmd.File.GameFile;
import com.bmd.GUI.GUI;
import com.bmd.Input.Input;
import com.bmd.Player.Player;
import com.bmd.Settings.Settings;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int width = 1080;
    public static final int height = 720;
    public static final String name = "Unexpected Orcs";

    public static Canvas canvas;
    public static GraphicsContext gc;
    public static Stage primary;

    public static boolean mousePressed = false;

    public static Engine engine;
    public static GUI gui;

    public static State STATE, PREV_STATE = null;
    public static String loadMessage = "";
    public static String loadedPlayerName = "";
    public static Player[] loadedPlayers;

    private long prevTime = System.currentTimeMillis();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Window setup
        primary = primaryStage;
        primary.setTitle(name);

        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        primary.setScene(scene);

        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        setup();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();

                if (Settings.remapNextKey) Settings.remapKey(Settings.remapAction, keyCode);
                if (keyCode == Settings.controls.UP_KEY) Input.keys[Util.up] = 1;
                if (keyCode == Settings.controls.LEFT_KEY) Input.keys[Util.left] = 1;
                if (keyCode == Settings.controls.DOWN_KEY) Input.keys[Util.down] = 1;
                if (keyCode == Settings.controls.RIGHT_KEY) Input.keys[Util.right] = 1;
                if (keyCode == Settings.controls.ABILITY_KEY) Input.keys[Util.ability] = 1;
                if(Settings.characterNaming) gui.keyPressed(event.getText().charAt(0));
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();

                if (keyCode == Settings.controls.UP_KEY) Input.keys[Util.up] = 0;
                if (keyCode == Settings.controls.LEFT_KEY) Input.keys[Util.left] = 0;
                if (keyCode == Settings.controls.DOWN_KEY) Input.keys[Util.down] = 0;
                if (keyCode == Settings.controls.RIGHT_KEY) Input.keys[Util.right] = 0;
                if (keyCode == Settings.controls.ABILITY_KEY) Input.keys[Util.ability] = 0;
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed = true;
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Input.mouseX = (int)event.getX();
                Input.mouseY = (int)event.getY();
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed = false;
                gui.handleMouseReleased();
            }
        });

        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                int dir = Util.sign((float)event.getDeltaY());
                if(STATE == State.PLAYING){
                    gui.miniMapZoom -= dir;
                    gui.miniMapZoom = Util.constrain(gui.miniMapZoom, gui.zoomMin, gui.zoomMax);
                } else if(STATE == State.LOAD) {
                    gui.loadScroll.changeScrollPosition(dir);
                }
            }
        });

        gc = canvas.getGraphicsContext2D();

        //Game setup

        //Engine setup


        primary.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double delta = (now - prevTime) / 1000f;
                draw(delta);
                prevTime = now;
            }
        };
        timer.start();
    }

    @Override
    public void stop() {
        quitGame();
    }

    private void setup() {
        Settings.loadSettings();
        Sprites.loadAssests();
        setState(State.MENU);

        engine = new Engine(this);
        gui = new GUI();
    }

    private void draw(double delta) {
        //Game Loop
        gc.clearRect(0, 0, width, height);

        gc.drawImage(new Image("com/bmd/assets/sprites/title.png"), 0, 0);

        switch(STATE) {
            case LOADING:
                gui.drawLoading();
                break;
            case MENU:
                gui.drawMenu();
                break;
            case OPTIONS:
                gui.drawOptions();
                break;
            case PLAYING:
                //thread("update");
                engine.update(delta);
                engine.show();
                gui.drawPlay(engine.player);
                /*if(drawDebug) {
                    image(debugScreen, 0, 0);
                    debugScreen.beginDraw();
                    debugScreen.clear();
                    debugScreen.endDraw();
                }*/
                break;
            case PAUSED:
                engine.show();
                gui.drawPaused();
                break;
            case DEAD:
                gui.drawDead();
                break;
            case NEWGAME:
                gui.drawNewGame();
                break;
            case LOAD:
                gui.drawLoad();
                break;
        }


    }

    public static void setState(State newState) {
        PREV_STATE = STATE;
        STATE = newState;
        Util.println(newState);
    }

    public static void revertState() {
        setState(PREV_STATE);
    }

    public static void quitGame() {
        GameFile.saveGame();
        Platform.exit();
    }
}
