package com.mygdx.game.Singleton;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 06.02.2018.
 */

public class Singleton {
    private static Singleton mySingletonInstance = null;
    private Board board;
    private ArrayList<TileState> boardState;
    private ArrayList<Sprite> boardTiles;
    private ArrayList<Tile> tiles;
    private int playerState;
    private Powerup powerupSelected;

    private Singleton() {
        boardState = new ArrayList<TileState>();
        boardTiles = new ArrayList<Sprite>();
        playerState = 0;
        tiles = new ArrayList<Tile>();
    }

    public void resetSingleton(){
        mySingletonInstance = new Singleton();
    }

    public static Singleton getInstance(){
        if(mySingletonInstance == null){
            mySingletonInstance = new Singleton();
        }
        return mySingletonInstance;
    }

    public ArrayList<TileState> getBoardState(){
        return this.boardState;
    }


    public void setBoardState(ArrayList<TileState> boardState) {
        this.boardState = boardState;
    }

    public void addBoardState(TileState state){
        boardState.add(state);
    }

    public void changePlayerState(){
        if (playerState == 0){
            playerState = 1;
        } else {
            playerState = 0;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Sprite> getBoardTiles() {
        return boardTiles;
    }

    public void setBoardTiles(ArrayList<Sprite> boardTiles) {
        this.boardTiles = boardTiles;
    }

    public Powerup getPowerupSelected() {
        return powerupSelected;
    }

    public void setpowerupSelected(Powerup powerupSelected) {
        this.powerupSelected = powerupSelected;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public int getPlayerState() {
        return playerState;
    }


}
