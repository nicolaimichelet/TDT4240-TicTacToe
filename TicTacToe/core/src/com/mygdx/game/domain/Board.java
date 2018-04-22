package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.sprites.Mark;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class Board {
    private int rows;
    private int columns;
    private ArrayList<Tile> tiles;
    private Singleton singleton = Singleton.getInstance();
    private int updateMoveCount;
    private Random rm = new Random();


    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public int getColumns() {
        return columns;
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
        float yFactor = (Gdx.graphics.getHeight()- MyGdxGame.BAR-MyGdxGame.BOTTOMBAR) / getRows();
      
        float xPosition = 0;
        float yPosition = MyGdxGame.BOTTOMBAR;
        for (int row = 0; row < getRows(); row++){
            for (int column = 0; column < getColumns(); column++){
                Tile t = new Tile(xPosition, yPosition, xFactor, yFactor, row,column);
                tiles.add(t);
                xPosition += xFactor;
            }
            yPosition += yFactor;
            xPosition = 0;
        }
        return tiles;
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
            s.setPosition(renderIterator + blankspace - 25, /*Gdx.graphics.getHeight() - MyGdxGame.BAR + 10*/10); // Fix this to appear in own menu
            pu.setPosition(new Vector3(renderIterator + blankspace - 25, /*Gdx.graphics.getHeight()  - MyGdxGame.BAR + 10*/ 10, 0f));
            pu.setHeight(50);
            pu.setWidth(50);
            s.draw(sb);
            renderIterator += factor;
        }
    }

    public void renderMarks(SpriteBatch sb,GameLogic gameLogic) {
        gameLogic.clearBoard();
        gameLogic.setZeroMoveCount();
        for (TileState ts : singleton.getBoardState()) {
            Tile tile = ts.getTile();
            Mark m = new Mark(tile, ts.getState());
            if (ts.getState() == 1) {
                gameLogic.Move(tile.getX(), tile.getY(), 'O');
            } else if (ts.getState() == 0) {
                gameLogic.Move(tile.getX(), tile.getY(), 'X');
            }
            else if (ts.getState()==-1){
                gameLogic.Move(tile.getX(), tile.getY(),'T');
            }
            Sprite s = new Sprite(m.getTexture());
            s.setPosition(tile.getPosition().x + tile.getWidth()/2 - (tile.getWidth())/3, tile.getPosition().y + tile.getHeight()/2 - (tile.getHeight())/3);
            s.setSize(tile.getWidth()/(float)1.5, tile.getHeight()/(float)1.5);
            s.draw(sb);
        }
        if (gameLogic.getMoveCount()>updateMoveCount){
            updateMoveCount=gameLogic.getMoveCount();
            System.out.println(gameLogic.printBoard());
        }
    }

    public void spawnRandomPowerup(GameLogic gameLogic){
        Tile t = singleton.getTiles().get(rm.nextInt((singleton.getBoard().getColumns() * singleton.getBoard().getRows())));
        Powerup pu = gameLogic.getMoveCount() > 2 ? singleton.getPowerups().get(rm.nextInt(singleton.getPowerups().size())) : singleton.getPowerups().get(rm.nextInt(singleton.getPowerups().size() - 1));
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

    public void renderPowerupsOnBoard(ArrayList<Tile> tiles, SpriteBatch sb){
        for (Tile t : tiles){
            if (t.getPowerup() != null) {
                Sprite pu = new Sprite(t.getPowerup().getTexture());
                Vector3 tilePosition = t.getPosition();
                pu.setSize(t.getWidth()-t.getWidth()/3, t.getHeight()-t.getHeight()/(float)2.5);
                float xPosition = (tilePosition.x + t.getWidth()/2 - (t.getWidth())/(float)3);
                float yPosition = (tilePosition.y + t.getHeight()/2 - (t.getHeight())/(float)3);
                pu.setPosition(xPosition, yPosition);
                pu.draw(sb);
            }
        }
    }


    public void dispose(){
        for (Sprite s: setBoardTiles()
             ) {
            s.getTexture().dispose();
        }
    }

}
