package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by bent on 16.03.18.
 */

public class GameBoard {
    private Texture[][] board;
    private int sizeX;
    private int sizeY;

    public GameBoard(int x, int y){
        sizeX = x;
        sizeY = y;
        board = new Texture[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++){
            for(int j =0; j < sizeY; j++){
                board[i][j] = new Texture("bp.png");
            }
        }
    }

    public int getSizeX(){
        return sizeX;
    }
    public int getSizeY(){
        return sizeY;
    }
    public Texture getBoardPiece(int x, int y){
        return board[x][y];
    }

    // Example function for changing the board state
    public void setBoardPiece(int x, int y, String status){
        board[x][y]= new Texture(status);
    }
}
