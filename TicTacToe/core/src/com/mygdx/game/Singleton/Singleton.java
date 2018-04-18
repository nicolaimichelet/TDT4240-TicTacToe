package com.mygdx.game.Singleton;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.powerups.ObstaclePowerup;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.powerups.SwapPowerup;
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
    private int playerState, n;
    private Powerup powerupSelected;
    private ArrayList<Player> players;
    private int indexTopowerupToRemove;
    private ArrayList<Powerup> powerups;

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public void updatePowerupList(){
        this.powerups = new ArrayList<Powerup>();
        this.powerups.add(new ObstaclePowerup());
        this.powerups.add(new SwapPowerup());
    }

    public void setPowerup(Powerup powerup){
        powerups.add(powerup);
    }

    private Singleton() {
        boardState = new ArrayList<TileState>();
        boardTiles = new ArrayList<Sprite>();
        powerups = new ArrayList<Powerup>();
        players = new ArrayList<Player>();
        playerState = 0;
        tiles = new ArrayList<Tile>();
        indexTopowerupToRemove = -1;
    }

    public int getN(){
        return n;
    }
    public void setN(int n){
        this.n = n;
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

    public void setBoardTiles(ArrayList<Sprite> boardTiles) {this.boardTiles = boardTiles;}

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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getIndexTopowerupToRemove() {
        return indexTopowerupToRemove;
    }

    public void setIndexTopowerupToRemove(int indexTopowerupToRemove) {
        this.indexTopowerupToRemove = indexTopowerupToRemove;
    }
}
