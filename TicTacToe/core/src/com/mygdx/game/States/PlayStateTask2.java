package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.sprites.ManuallyControlledHelicopter;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayStateTask2 implements State {

    private ManuallyControlledHelicopter helicopter;
    private Sprite sprite;
    String position = "";
    BitmapFont font = new BitmapFont();
    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;

    public PlayStateTask2(GameStateManager gsm) {
        this.gsm = gsm;
        helicopter = new ManuallyControlledHelicopter(20, 20);

    }

    @Override
    public  void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);
    }


    @Override
    public void render(SpriteBatch sb) {
        sprite = new Sprite(helicopter.getTexture());
        sprite.setPosition(helicopter.getPosition().x, helicopter.getPosition().y);
        sb.begin();
        if (helicopter.getVelocity().x > 0){
            //sb.draw(helicopter.getTexture(), helicopter.getPosition().x, helicopter.getPosition().y);
            sprite.flip(true, false);
            sprite.draw(sb);
        } else {
            sprite.flip(false, false);
            sprite.draw(sb);
            //sb.draw(helicopter.getTexture(), helicopter.getPosition().x, helicopter.getPosition().y, 0, 0, 0, 0, 0, 0, true, false);
        }
        font.draw(sb, writePosition(), 10, MyGdxGame.HEIGHT-30);
        sb.end();
    }

    @Override
    public void dispose() {

    }

    public String writePosition(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            position =  "Position: " + Gdx.input.getX() + ", " + Gdx.input.getY();
        }
        return position;
    }
}
