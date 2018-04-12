package com.mygdx.game.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.GameLogic;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Mark;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayState implements State {

    private Singleton singleton = Singleton.getInstance();
    private Board matrix;
    private ArrayList<Tile> tiles;
    private ArrayList<Sprite> boardTiles;
    private GameStateManager gsm;
    private char brett [][];
    private String midBoard;
    private int amountToWin;
    private GameLogic gameLogic;


    public PlayState(GameStateManager gsm,int rows,int cols,int amountToWin) {
        brett = new char[rows][cols];
        matrix = new Board(rows, cols);
        boardTiles = matrix.setBoardTiles();
        this.gsm = gsm;
        this.amountToWin = amountToWin;
        gameLogic = new GameLogic(brett,amountToWin);
        gameLogic.clearBoard(brett);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            System.out.println(gameLogic.printBoard(brett));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Tile t : matrix.getTiles()){
            t.update(dt);
        }
        if (gameLogic.hasWinner()){
            System.out.println("Vinneren er spiller "+gameLogic.getWinner());
            gsm.set(new MenuState(gsm));
            dispose();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //Create board tiles
        ArrayList<TileState> boardState = singleton.getBoardState();
        sb.begin();
        for (Sprite s : boardTiles){
            s.draw(sb);
        }

        // Draw marks when pressed
        for (TileState ts : boardState){
            Tile tile = ts.getTile();
            Mark m = new Mark(tile, ts.getState());
            if (ts.getState() == 1){
                gameLogic.setTile(tile.getX(),tile.getY(),'B');
            }
            else{
                gameLogic.setTile(tile.getX(),tile.getY(),'A');
            }
            Sprite s = new Sprite(m.getTexture());
            s.setPosition(tile.getPosition().x, tile.getPosition().y);
            s.setSize(tile.getWidth(), tile.getHeight());
            s.draw(sb);
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }



}
