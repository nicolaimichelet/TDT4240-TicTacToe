package com.mygdx.game.domain;

import com.mygdx.game.sprites.Tile;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class TileState {
    private Tile tile;
    private int state;

    public TileState(Tile tile, int state) {
        this.tile = tile;
        this.state = state;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
