package com.bmd.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bmd.game.GUI.*;
import com.bmd.game.Sprites.Sprites;
import com.bmd.game.Util.Util;

public class UnexpectedOrcs extends Game {
	private BitmapFont font;
	private States state, prevState = null;

	
	@Override
	//This is essentially the "setup()" function from Processing
	public void create () {
	    Gdx.graphics.setWindowedMode(1080, 720);
	    Gdx.graphics.setResizable(false);
		Sprites.loadAssests();
		setState(States.MENU);
	}

	@Override
	//This is like the "draw()" loop from Processing
	public void render () {
		super.render();
	}
	
	@Override
	//This is when the progam ends
	public void dispose () {
        System.out.print("We out nibs");
        //saveGame
    }

    public void setState(States newState) {
		prevState = state;
		state = newState;
		System.out.println(newState);
		switch (state) {
			case MENU:
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
				break;
		}

	}

	public void revertState() {
		setState(prevState);
	}
}
