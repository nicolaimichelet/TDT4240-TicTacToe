package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.powerups.Powerup;

/**
 * Created by eiriksandberg on 12.04.2018.
 */

public abstract class InputHandler {
    private float width;
    private float height;
    private Vector3 position;

    public boolean touchDown(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Vector3 positionClicked = new Vector3(Gdx.input.getX(), MyGdxGame.HEIGHT-Gdx.input.getY(), 0);
            if (isHit(positionClicked, position, width, height)){
                return true;
            }
        }
        return false;
    }

    public boolean isHit(Vector3 positionClicked, Vector3 objPosition, float width, float height){
        float objX = objPosition.x;
        float objY = objPosition.y ;
        float x = positionClicked.x;
        float y = positionClicked.y;
        if (x >= objX && x <= objX + width && y >= objY && y <= objY + height){
            return true;
        } else{
            return false;
        }
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
