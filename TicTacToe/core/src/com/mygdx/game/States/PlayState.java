package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.sprites.Mark;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayState implements State {

    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Table table = new Table();

    public PlayState(GameStateManager gsm) {
        singleton.setBoard(new Board(3, 3));
        singleton.setTiles(generateBoard(singleton.getBoard()));
        singleton.setBoardTiles(setBoardTiles());
        this.gsm = gsm;

        // Mock players with powerup
        ArrayList<Powerup> powerups = new ArrayList<Powerup>();
        powerups.add(new ExpandBoardPowerup());
        players.add(new Player(0, powerups));
        players.add(new Player(1, powerups));


        // Add table
        table.add();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            table.setVisible(true);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Tile t : singleton.getTiles()){
            t.update(dt);
        }
        /*for (Powerup pu : players.get(singleton.getPlayerState()).getPowerups()){
            pu.update(dt);
        }*/
    }

    @Override
    public void render(SpriteBatch sb) {
        // Create board tiles
        sb.begin();
        for (Sprite s : singleton.getBoardTiles()){
            s.draw(sb);
        }

        // Draw marks when pressed
        renderMarks(sb);

        // Powerups
       /* Player activePlayer = players.get(singleton.getPlayerState());
        if (activePlayer.havePowerupsAvailable()){
            renderPowerups(activePlayer.getPowerups(), sb);
        }*/
        sb.end();
    }

    @Override
    public void dispose() {

    }

    public ArrayList<Sprite> setBoardTiles(){
        ArrayList<Sprite> boardTiles = new ArrayList<Sprite>();
        for (Tile t : singleton.getTiles()){
            Sprite s = new Sprite(t.getTexture());
            s.setSize(t.getWidth(), t.getHeight());
            s.setPosition(t.getPosition().x , t.getPosition().y);
            boardTiles.add(s);
        }
        return boardTiles;
    }

    public ArrayList<Tile> generateBoard(Board board){
        ArrayList<Tile> tiles = new ArrayList<Tile>();
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
        return tiles;
    }

    public void renderPowerups(ArrayList<Powerup> powerups, SpriteBatch sb){
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        float i = 50;
        for (Powerup pu : powerups){
            Sprite s = new Sprite(pu.getTexture());
            s.setPosition(i, 50); // Fix this to appear in own menu
            pu.setPosition(new Vector3(i, 50f, 0f));
            s.setSize(50, 50);
            pu.setHeight(50);
            pu.setWidth(50);
            s.draw(sb);
            i += 50;
        }
    }

    public void renderMarks(SpriteBatch sb){
        for (TileState ts : singleton.getBoardState()){
                Tile tile = ts.getTile();
                Mark m = new Mark(tile, ts.getState());
                Sprite s = new Sprite(m.getTexture());
                s.setPosition(tile.getPosition().x, tile.getPosition().y);
                s.setSize(tile.getWidth(), tile.getHeight());
                s.draw(sb);
        }
    }
}
