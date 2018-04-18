package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.domain.GameLogic;
import com.mygdx.game.powerups.ObstaclePowerup;
import com.mygdx.game.powerups.SwapPowerup;
import com.mygdx.game.sprites.Mark;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayState implements State {

    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;
    private GameLogic gameLogic;
    private Board matrix;
    private ArrayList<Powerup> powerups;
    private Random rm = new Random();
    private int currentMoveCount = rm.nextInt(3);

    public PlayState(GameStateManager gsm, int n) {
        matrix = new Board(n, n);
        singleton.setBoard(matrix);
        singleton.setTiles(matrix.generateBoard());
        singleton.setBoardTiles(matrix.setBoardTiles());

        this.gsm = gsm;
        gameLogic = new GameLogic(n);
        // Mock players with powerup
        powerups = new ArrayList<Powerup>();
        powerups.add(new ExpandBoardPowerup());
        powerups.add(new ObstaclePowerup());
        powerups.add(new SwapPowerup());
        ArrayList<Player> players = new ArrayList<Player>();

        ArrayList<Powerup> mocklist = new ArrayList<Powerup>();
        mocklist.add(new SwapPowerup());
        mocklist.add(new ObstaclePowerup());
        mocklist.add(new ExpandBoardPowerup());
        players.add(new Player(0, mocklist));
        players.add(new Player(1, null));
        singleton.setPlayers(players);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            gsm.set(new MainMenuState(gsm));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            gameLogic.expandBoard();
        }
    }


        @Override
        public void update ( float dt){
            ArrayList<Player> players = singleton.getPlayers();
            dispose();
            handleInput();
            for (Tile t : singleton.getTiles()) {
                t.update(dt);
            }
            if (gameLogic.hasWinner()) {
                System.out.println("Vinneren er spiller " + gameLogic.getWinner());
                gsm.set(new AfterGameMenuState(gsm, gameLogic.getWinner()));
                dispose();
            }
            else if (!gameLogic.hasWinner() && gameLogic.getWinner() == 'D') {
                System.out.println("UAVGJORT");
                gsm.set(new MainMenuState(gsm));
                dispose();
            }
            if (players.get(singleton.getPlayerState()).getPowerups() != null){
                for (Powerup pu : players.get(singleton.getPlayerState()).getPowerups()){
                    pu.update(dt);
                }
            }
            if (singleton.getN() > gameLogic.getN()){
                gameLogic.expandBoard();
            }

            if (singleton.getIndexTopowerupToRemove() >= 0){
                players.get(singleton.getPlayerState()).getPowerups().remove(singleton.getIndexTopowerupToRemove());
                singleton.setIndexTopowerupToRemove(-1);
            }
        }

        @Override
        public void render (SpriteBatch sb){
            // Create board tiles
            sb.begin();
            for (Sprite s : singleton.getBoardTiles()) {
                s.draw(sb);
            }

            // render tiles and powerups
            //renderTiles(singleton.getTiles(), sb);
            renderPowerupsOnBoard(singleton.getTiles(), sb);

            // Draw marks when pressed
            renderMarks(sb);

            // Powerups
            Player activePlayer = singleton.getPlayers().get(singleton.getPlayerState());
            if (activePlayer.havePowerupsAvailable()){
                renderPowerups(activePlayer.getPowerups(), sb);
            }

            if (currentMoveCount - gameLogic.getMoveCount() == 0){
                spawnRandomPowerup();
                currentMoveCount = rm.nextInt(3) + 1 + gameLogic.getMoveCount();
            }
            sb.end();
    }

        @Override
        public void dispose () {
            matrix.dispose();
            for (TileState ts : singleton.getBoardState()) {
                ts.getTile().getTexture().dispose();
            }
        }

    public void renderPowerupsOnBoard(ArrayList<Tile> tiles, SpriteBatch sb){
        for (Tile t : tiles){
            if (t.getPowerup() != null) {
                Sprite pu = new Sprite(t.getPowerup().getTexture());
                Vector3 tilePosition = t.getPosition();
                pu.setSize(50, 50);
                float xPosition = (tilePosition.x + (t.getWidth() / 2) - 25);
                float yPosition = (tilePosition.y + (t.getHeight() / 2) - 25);
                pu.setPosition(xPosition, yPosition);
                pu.draw(sb);
            }
        }
    }


    public void renderPowerups(ArrayList<Powerup> powerups, SpriteBatch sb) {
        float factor = powerups.size() > 1 ? MyGdxGame.WIDTH / powerups.size() : MyGdxGame.WIDTH / 2;
        float blankspace = powerups.size() > 1 ? factor / powerups.size() : 0;
        float renderIterator = powerups.size() > 1 ? 0 : MyGdxGame.WIDTH / 2;
        for (Powerup pu : powerups){
            Sprite s = new Sprite(pu.getTexture());
            if (pu.equals(singleton.getPowerupSelected())){
                s.setAlpha(0.5f);
            }
            s.setSize(50, 50);
            s.setPosition(renderIterator + blankspace - 25, Gdx.graphics.getHeight() - MyGdxGame.BAR + 10); // Fix this to appear in own menu
            pu.setPosition(new Vector3(renderIterator + blankspace - 25, Gdx.graphics.getHeight()  - MyGdxGame.BAR + 10 , 0f));
            pu.setHeight(50);
            pu.setWidth(50);
            s.draw(sb);
            renderIterator += factor;
        }
    }

    public void renderMarks(SpriteBatch sb) {
        for (TileState ts : singleton.getBoardState()) {
            Tile tile = ts.getTile();
            Mark m = new Mark(tile, ts.getState());
            if (ts.getState() == 1) {
                gameLogic.Move(tile.getX(), tile.getY(), 'O');
            } else if (ts.getState() == 0) {
                gameLogic.Move(tile.getX(), tile.getY(), 'X');
            }
            Sprite s = new Sprite(m.getTexture());
            s.setPosition(tile.getPosition().x, tile.getPosition().y);
            s.setSize(tile.getWidth(), tile.getHeight());
            s.draw(sb);
        }
    }

    public void spawnRandomPowerup(){
        Tile t = singleton.getTiles().get(rm.nextInt((singleton.getBoard().getColumns() * singleton.getBoard().getRows())));
        Powerup pu = powerups.get(rm.nextInt(powerups.size()));
        boolean canPlacePowerup = true;
        for (TileState tileState : singleton.getBoardState()) {
            if (tileState.getTile().getX() == t.getX() && tileState.getTile().getY() == t.getY()){
                canPlacePowerup = false;
            }
        }
        if (canPlacePowerup){
            pu.setPosition(new Vector3(-10f,-10f,0f)); // powerup needs position to work. Random position for init
            t.setPowerup(pu);
        }
    }
}
