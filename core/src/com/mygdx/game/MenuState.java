package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by bent on 16.03.18.
 */

public abstract class MenuState extends State {
    private Texture background;
    private Texture heli1;
    public MenuState(GameStateManager gsm){
        super(gsm);
        background = new Texture("white.jpg");
        heli1 = new Texture("heli1.png");
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState((gsm)));
            dispose();
        }
    }
    @Override
    public void update(float dt){
        handleInput();
    }
    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(background, 0,0, MyGdxGame.WIDTH,MyGdxGame.HIGTH);
        sb.draw(heli1, (MyGdxGame.WIDTH/2) - (heli1.getWidth()), MyGdxGame.HIGTH/2);
        sb.end();
    }
    @Override
    public void dispose(){
        background.dispose();
        heli1.dispose();
    }
}