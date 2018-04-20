package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.AfterGameMenuState;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MainMenuState;

public class MyGdxGame extends ApplicationAdapter {
	public static final int BAR = 70;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final int BOTTOMBAR = 70;



	public static final String TITLE = "Tic Tac Toe UNLEASHED";
	private GameStateManager gsm;
	private SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 1, 1, 1);
		gsm.push(new MainMenuState(gsm,3));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
