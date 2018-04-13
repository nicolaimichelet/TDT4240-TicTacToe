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
        singleton.setBoardTiles(setBoardTiles(newBoard));
    }

    public ArrayList<Sprite> setBoardTiles(Board board){
        ArrayList<Sprite> boardTiles = new ArrayList<Sprite>();
        ArrayList<Tile> tiles = updateBoard(board, singleton.getTiles());
        updateMarks(tiles);
        for (Tile t : tiles){
            Sprite s = new Sprite(t.getTexture());
            s.setSize(t.getWidth(), t.getHeight());
            s.setPosition(t.getPosition().x , t.getPosition().y);
            boardTiles.add(s);
        }
        return boardTiles;
    }

    public ArrayList<Tile> updateBoard(Board board, ArrayList<Tile> oldTiles){
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        float xFactor = MyGdxGame.WIDTH / board.getRows();
        float yFactor = MyGdxGame.HEIGHT / board.getColumns();
        float xPosition = 0;
        float yPosition = 0;
        int id = 0;
        for (int row = 0; row < board.getRows(); row++){
            for (int column = 0; column < board.getColumns(); column++){
                if (row < board.getRows() - 1){
                    tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, oldTiles.get(column).getId()));
                } else {
                    tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, id));
                }
                yPosition += yFactor;
                id++;
            }
            id++;
            xPosition += xFactor;
            yPosition = 0;
        }
        return tiles;
    }

    public void updateMarks(ArrayList<Tile> tiles){
        ArrayList<TileState> newBoardState = new ArrayList<TileState>();
        for (TileState ts : singleton.getBoardState()){
            for (Tile t : tiles){
                if (ts.getTile().getId() == t.getId()){
                    newBoardState.add(new TileState(t, t.getId()));
                    break;
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
