package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

/**
 * Created by eiriksandberg on 23.01.2018.
 */

public class AnimatedHelicopter {

    private Vector3 position;
    private Vector3 velocity;

    private Texture helicopter;
    private Animation helicopterAnimation;
    private int id;

    private Random rn = new Random();

    public AnimatedHelicopter(int x, int y, int id){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopter = new Texture("heli1.png");
        helicopterAnimation = new Animation(4, 0.1f);
        this.id = id;
    }

    public void update(float dt){
        helicopterAnimation.update(dt);
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

    public TextureRegion getTexture() {
        return helicopterAnimation.getFrame();
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public int getId() {
        return id;
    }

    public void setPosition(float x, float y, float z) {
        position.add(x,y,z);
    }

    public void setVelocity(float x, float y, float z) {
        velocity.set(x,y,z);
    }
}
