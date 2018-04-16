package com.mygdx.game.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class SwapPowerup extends InputHandler implements com.mygdx.game.powerups.Powerup {
    private Texture swapIcon;
    private Tile selectedTile1;
    private Tile selectedTile2;
    private Singleton singleton = Singleton.getInstance();

    public SwapPowerup() {
        this.swapIcon = new Texture("swap.png");
    }

    public void swap(Tile t1, Tile t2){
        ArrayList<TileState> boardState = singleton.getBoardState();
        System.out.println("Swapped");
    }
    public void update(float dt){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

        }
    }

    public void setSelectedTile1(Tile selectedTile1) {
        this.selectedTile1 = selectedTile1;
    }

    public void setSelectedTile2(Tile selectedTile2) {
        this.selectedTile2 = selectedTile2;
    }

    public Tile getSelectedTile1() {
        return selectedTile1;
    }

    public Tile getSelectedTile2() {
        return selectedTile2;
    }

    public Texture getTexture() {
        return swapIcon;
    }

}
