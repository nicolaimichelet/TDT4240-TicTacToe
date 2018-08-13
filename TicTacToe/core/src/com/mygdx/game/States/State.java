
package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public interface State {

    void handleInput();
    void update(float dt);
    void render(SpriteBatch sb);
    void dispose();
}
