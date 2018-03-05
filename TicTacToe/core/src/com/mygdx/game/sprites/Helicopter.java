package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class Helicopter {
    private Vector3 position;
    private Vector3 velocity;

    private Texture helicopter;

    private Random rn = new Random();

    public Helicopter(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopter = new Texture("heli1.png");
    }

    public void update(float dt){
        if (velocity.isZero()){
            velocity.set(200, 200, 0);
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        if (position.x > (MyGdxGame.WIDTH - helicopter.getWidth()) || position.x < 0){
            velocity.set(-velocity.x, velocity.y, 0);
            position.add(velocity.x, velocity.y, 0);
        }
        if (position.y > (MyGdxGame.HEIGHT - helicopter.getHeight()) || position.y < 0){
            velocity.set(velocity.x, -velocity.y, 0);
            position.add(velocity.x, velocity.y, 0);
        }
        velocity.scl(1/dt);
    }

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
