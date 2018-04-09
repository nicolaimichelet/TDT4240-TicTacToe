package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Helicopter;
import com.mygdx.game.sprites.Mark;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayState implements State {

    private Singleton singleton = Singleton.getInstance();
    private Board board;
    private ArrayList<Tile> tiles;
    private ArrayList<Sprite> boardTiles;
    private GameStateManager gsm;

    public PlayState(GameStateManager gsm) {
        board = new Board(3, 3);
        tiles = new ArrayList<Tile>();
        boardTiles = setBoardTiles();
        this.gsm = gsm;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Tile t : tiles){
            t.update(dt);
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

    public ArrayList<Sprite> setBoardTiles(){
        ArrayList<Sprite> boardTiles = new ArrayList<Sprite>();
        generateBoard(board);
        for (Tile t : tiles){
            Sprite s = new Sprite(t.getTexture());
            s.setSize(t.getWidth(), t.getHeight());
            s.setPosition(t.getPosition().x , t.getPosition().y);
            boardTiles.add(s);
        }
        return boardTiles;
    }

    public void generateBoard(Board board){
        float xFactor = MyGdxGame.WIDTH / board.getRows();
        float yFactor = MyGdxGame.HEIGHT / board.getColumns();
        float xPosition = 0;
        float yPosition = 0;
        int id = 0;
        for (int row = 0; row < board.getRows(); row++){
            for (int column = 0; column < board.getColumns(); column++){
                tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, id));
                yPosition += yFactor;
                id++;
            }
            id++;
            xPosition += xFactor;
            yPosition = 0;
        }
    }
}
