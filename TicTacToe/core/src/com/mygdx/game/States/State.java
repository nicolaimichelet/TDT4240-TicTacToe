
package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public interface State {

    /*
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }
    */


    void handleInput();
    void update(float dt);
    void render(SpriteBatch sb);
    void dispose();
}
