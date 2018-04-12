package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class MenuState implements State {
    BitmapFont font = new BitmapFont();
    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;
    private Texture background = new Texture("white.png");


    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            gsm.set(new PlayState(gsm,3,3,3));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,MyGdxGame.WIDTH,MyGdxGame.HEIGHT);
        font.draw(sb, "Press 1 on the keyboard for fun", MyGdxGame.WIDTH/4, MyGdxGame.HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {}

}
