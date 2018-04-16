package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 10.04.2018.
 */

public class ExpandBoardPowerup extends InputHandler implements com.mygdx.game.powerups.Powerup {

    private Texture expandIcon;
    private Singleton singleton = Singleton.getInstance();

    public ExpandBoardPowerup() {
        this.expandIcon = new Texture("expand.png"); // change this later to right png
    }

    public void expand(Board currentBoard){
        Board newBoard = new Board(currentBoard.getRows() + 1, currentBoard.getColumns() + 1);
        ArrayList<Tile> newTiles = newBoard.generateBoard();
        singleton.setTiles(newTiles);
        updateMarks(newTiles);
        singleton.setBoardTiles(newBoard.setBoardTiles());
    }

    public void updateMarks(ArrayList<Tile> newTiles){
        ArrayList<TileState> newBoardState = new ArrayList<TileState>();
        for (Tile t : newTiles) {
            for (TileState ts : singleton.getBoardState()) {
                if (t.getX() == ts.getTile().getX() && t.getY() == ts.getTile().getY()){
                    TileState newTileState = new TileState(t, ts.getState());
                    newBoardState.add(newTileState);
                }
            }
        }
        singleton.setBoardState(newBoardState);
    }

    @Override
    public void update(float dt) {
        if (touchDown()){
            expand(singleton.getBoard());
            //TODO: Remove powerup from player. Used
        }
    }

    @Override
    public Texture getTexture() {
        return expandIcon;
    }

}