package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class ManuallyControlledHelicopter {
    private Vector3 position;
    private Vector3 velocity;

    private Texture helicopter;

    private Random rn = new Random();

    public ManuallyControlledHelicopter(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopter = new Texture("heli1.png");
    }

    public void update(float dt) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            position.set(Gdx.input.getX(), MyGdxGame.HEIGHT - Gdx.input.getY(), 0);
        }
        if (position.x < 0){
            position.set(0, MyGdxGame.HEIGHT - Gdx.input.getY(), 0);
        }
        if (position.x > (MyGdxGame.WIDTH - helicopter.getWidth())){
            position.set(MyGdxGame.WIDTH - helicopter.getWidth(), MyGdxGame.HEIGHT - Gdx.input.getY(), 0);
        }
        if (position.y > (MyGdxGame.HEIGHT - helicopter.getHeight())){
            position.set(Gdx.input.getX(), MyGdxGame.HEIGHT - helicopter.getHeight(), 0);
        }
        if (position.y < 0){
            position.set(Gdx.input.getX(), 0, 0);
        }
    }


/*    public void update(int x, int y){
       position.add(x, y, 0);
        if (position.x > (MyGdxGame.WIDTH - helicopter.getWidth())) {
            position.add(MyGdxGame.WIDTH - helicopter.getWidth(), y, 0);
        }
        if (position.x < 0){
            position.add(0, y, 0);
        }
        if (position.y > (MyGdxGame.HEIGHT - helicopter.getHeight())) {
            position.add(velocity.x, velocity.y, 0);
        }
        if (position.y < 0){
            position.add(x, MyGdxGame.HEIGHT - helicopter.getHeight(), 0);
        }
    }*/

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return helicopter;
    }

    public Vector3 getVelocity() {
        return velocity;
    }
}
