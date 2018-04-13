package com.mygdx.game.States;

import com.badlogic.gdx.Game;
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
import com.mygdx.game.domain.GameLogic;
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
    private char brett [][];
    private GameLogic gameLogic;
    private String midBoard;
    private Board matrix;

    public PlayState(GameStateManager gsm, int rows, int cols, int amountToWin) {
        brett = new char[rows][cols];
        matrix = new Board(rows,cols);
        singleton.setBoard(new Board(3, 3));
        singleton.setTiles(matrix.generateBoard(singleton.getBoard()));
        singleton.setBoardTiles(matrix.setBoardTiles());
        this.gsm = gsm;
        this.amountToWin = amountToWin;
        gameLogic = new GameLogic(brett,amountToWin);

        // Mock players with powerup
        ArrayList<Powerup> powerups = new ArrayList<Powerup>();
        powerups.add(new ExpandBoardPowerup());
        players.add(new Player(0, powerups));
        players.add(new Player(1, powerups));

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

    @Override
    public void update(float dt) {
        handleInput();
        for (Tile t : singleton.getTiles()){
            t.update(dt);
        }
        if (gameLogic.hasWinner()){
            System.out.println("Vinneren er spiller "+gameLogic.getWinner());
            gsm.set(new MenuState(gsm));
            dispose();
        }
        else if (!gameLogic.hasWinner() && gameLogic.getWinner() == 'D'){
            gsm.set(new MenuState(gsm));
            dispose();
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
                if (ts.getState() == 1){
                    //gameLogic.setTile(tile.getX(),tile.getY(),'O');
                    gameLogic.Move(tile.getX(),tile.getY(),'O');
                }
                else{
                    //gameLogic.setTile(tile.getX(),tile.getY(),'X');
                    gameLogic.Move(tile.getX(),tile.getY(),'X');
                }
                Sprite s = new Sprite(m.getTexture());
                s.setPosition(tile.getPosition().x, tile.getPosition().y);
                s.setSize(tile.getWidth(), tile.getHeight());
                s.draw(sb);
        }
    }
}
