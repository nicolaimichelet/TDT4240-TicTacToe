package com.mygdx.game.domain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class Board {
    private int rows;
    private int columns;
    private ArrayList<Tile> tiles;


    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new ArrayList<Tile>();
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public ArrayList<Sprite> setBoardTiles(){
        ArrayList<Sprite> boardTiles = new ArrayList<Sprite>();
        generateBoard(this);
        for (Tile t : tiles){
            Sprite s = new Sprite(t.getTexture());
            s.setSize(t.getWidth(), t.getHeight());
            s.setPosition(t.getPosition().x , t.getPosition().y);
            boardTiles.add(s);
        }
        return boardTiles;
    }

    public void generateBoard(Board board){
        float xFactor = MyGdxGame.WIDTH / board.getColumns();
        float yFactor = MyGdxGame.HEIGHT / board.getRows();
        float xPosition = 0;
        float yPosition = 0;
        int id = 0;
        for (int row = 0; row < board.getRows(); row++){
            for (int column = 0; column < board.getColumns(); column++){
                tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, id,row,column));
                xPosition += xFactor;
                id++;
            }
            id++;
            yPosition += yFactor;
            xPosition = 0;
        }
    }

}
