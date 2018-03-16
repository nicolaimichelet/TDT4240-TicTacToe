package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by bent on 16.03.18.
 */

public class GameBoard {
    private Texture boardPiece;
    private int sizeX;
    private int sizeY;

    public GameBoard(int x, int y){
        sizeX = x;
        sizeY = y;
        boardPiece = new Texture("bp.png");
    }

    public int getSizeX(){
        return sizeX;
    }
    public int getSizeY(){
        return sizeY;
    }
    public Texture getBoardPiece(){
        return boardPiece;
    }
}
