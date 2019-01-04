package com.bmd.App;

import com.bmd.Engine.Engine;
import com.bmd.GUI.GUI;
import com.bmd.Player.Player;
import com.bmd.Sprites.Sprites;
import com.bmd.Util.Util;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;

public class Main extends Application {

    public static final int width = 1080;
    public static final int height = 720;
    public static final String name = "Unexpected Orcs";

    public GraphicsContext gc;
    public static Stage primary;

    public static boolean mousePressed = false;

    public static Engine engine;
    public static GUI gui;

    public static State STATE, PREV_STATE = null;
    public static String loadMessage = "";
    public static String loadedPlayerName = "";
    public static Player[] loadedPlayers;

    private long prevTime = System.currentTimeMillis();;

    float x = 0, y = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Window setup
        primary = primaryStage;
        primary.setTitle(name);

        Group root = new Group();
        Scene scene = new Scene(root);
        primary.setScene(scene);

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) { keyPressed(event.getCode());  }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) { keyReleased(event.getCode());  }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed(event.getX(), event.getY());
                mousePressed = true;
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleased(event.getX(), event.getY());
                mousePressed = false;
            }
        });

        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                mouseWheel(Util.sign((float)event.getDeltaY()));
            }
        });

        gc = canvas.getGraphicsContext2D();

        //Game setup
        setup();

        //Engine setup
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.016), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                long currentTime = System.currentTimeMillis();
                double delta = (currentTime - prevTime) / 1000f;
                draw(delta);
                prevTime = currentTime;
            }
        });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        primary.show();
    }

    private void setup() {
        Sprites.loadAssests();
        setState(State.MENU);

        engine = new Engine(this);
        gui = new GUI();
    }

    private void draw(double delta) {
        //Game Loop
        gc.clearRect(0, 0, width, height);

        gc.drawImage(new Image("com/bmd/assets/sprites/title.png"), 0, 0);

    }

    public static void setState(State newState) {
        PREV_STATE = STATE;
        STATE = newState;
        System.out.println(newState);
        switch (STATE) {
			/*case MENU:
				this.setScreen(new MainMenu(this));
				break;
			case OPTIONS:
				this.setScreen(new OptionsMenu(this));
				break;
			case NEWGAME:
				this.setScreen(new NewGameMenu(this));
				break;
			case LOAD:
				this.setScreen(new LoadGameMenu(this));
				break;
			case PAUSED:
				this.setScreen(new PausedMenu(this));
				break;
			case PLAYING:
				this.setScreen(new PlayGUI(this));
				break;
			case DEAD:
				this.setScreen(new DeadMenu(this));
				break;
			case LOADING:
				this.setScreen(new LoadingScreen(this));
				break;*/
        }

    }

    public static void revertState() {
        setState(PREV_STATE);
    }


    public void mouseWheel(int dir) {
        System.out.println(dir);
    }

    public void mouseReleased(double x, double y) {
        System.out.format("UP %f, %f\n", x, y);

    }

    public void mousePressed(double x, double y) {
        System.out.format("DOWN %f, %f\n", x, y);

    }

    public void keyPressed(KeyCode keyCode) {
        System.out.println("DOWN " + keyCode.toString());
    }

    public void keyReleased(KeyCode keyCode) {
        System.out.println("UP " + keyCode.toString());
    }


}
