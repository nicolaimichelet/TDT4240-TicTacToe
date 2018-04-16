package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
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
        generateBoard();
        for (Tile t : tiles){
            Sprite s = new Sprite(t.getTexture());
            s.setSize(t.getWidth(), t.getHeight());
            s.setPosition(t.getPosition().x , t.getPosition().y);
            boardTiles.add(s);
        }
        return boardTiles;
    }

    public ArrayList<Tile> generateBoard(){
        this.tiles = new ArrayList<Tile>();
        float xFactor = Gdx.graphics.getWidth() / getColumns();
        float yFactor = (Gdx.graphics.getHeight()- MyGdxGame.BAR) / getRows();
        float xPosition = 0;
        float yPosition = 0;
        for (int row = 0; row < getRows(); row++){
            for (int column = 0; column < getColumns(); column++){
                tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, row,column));
                xPosition += xFactor;
            }
            yPosition += yFactor;
            xPosition = 0;
        }
        return tiles;
    }

}
