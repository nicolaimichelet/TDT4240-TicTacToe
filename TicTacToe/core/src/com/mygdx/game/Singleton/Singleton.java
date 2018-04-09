package com.mygdx.game.Singleton;

import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 06.02.2018.
 */

public class Singleton {
    private static Singleton mySingletonInstance = null;
    private ArrayList<TileState> boardState;
    private int playerState;

    private Singleton() {
        boardState = new ArrayList<TileState>();
        playerState = 0;
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

    public int getPlayerState() {
        return playerState;
    }

    public void removeBoardState(int tileID){
        int index = 0;
        for (TileState ts : boardState){
            if (ts.getTile().getId() == tileID){
                boardState.remove(index);
                return;
            }
            index++;
        }
    }

    /*public void updateBoardSize(int x, int y){
        Tile[][] newBoard = new Tile[x][y];
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                newBoard[i][j] = boardState[i][j];
            }
        }
        boardState = newBoard;
    }*/
}
