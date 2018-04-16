package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by KasperKBerg on 11.04.2018.
 */

public class Cell {

    private Texture cell;
    private Vector3 pos;
    private boolean isMarked;

    public Cell(){

        cell = new Texture("tile.png");
        isMarked = false;

    }




}
