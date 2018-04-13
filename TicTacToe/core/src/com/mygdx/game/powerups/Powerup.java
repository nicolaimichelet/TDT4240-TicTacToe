package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by eiriksandberg on 10.04.2018.
 */

public interface Powerup {
    void update(float dt);
    Texture getTexture();
    void setPosition(Vector3 position);
    Vector3 getPosition();
    void setHeight(float height);
    float getHeight();
    void setWidth(float width);
    float getWidth();
}
