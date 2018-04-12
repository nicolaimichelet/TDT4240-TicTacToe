package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.TileState;
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
    private char brett [][];
    private String midBoard;
    private int amountToWin;


    public PlayState(GameStateManager gsm,int rows,int cols,int amountToWin) {
        brett = new char[rows][cols];
        board = new Board(rows, cols);
        tiles = new ArrayList<Tile>();
        boardTiles = setBoardTiles();
        this.gsm = gsm;
        this.amountToWin = amountToWin;
        //this.brett [][] = new char[rows][cols];

        for(int i = 0; i<brett.length; i++){
            for(int j = 0; j<brett[i].length; j++){
                brett[i][j]='-';
            }
        }
        /*
        System.out.println("BOARD:");
        System.out.println("Xo: "+0+" , X1: "+MyGdxGame.WIDTH);
        System.out.println("Yo: "+0+" , Y1: "+MyGdxGame.HEIGHT);
        */
    }

    public String getBoard(){
        return midBoard;
    }


    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            System.out.println(printBoard());
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Tile t : tiles){
            t.update(dt);
        }
        if (isWinner(brett,amountToWin)=='A'){
            System.out.println("Vinneren er A");
            gsm.set(new MenuState(gsm));
            clearBoard(brett);
            dispose();
        }
        else if (isWinner(brett,amountToWin)=='B'){
            System.out.println("Vinneren er B");
            gsm.set(new MenuState(gsm));
            clearBoard(brett);
            dispose();
        }
    }

    private void clearBoard(char[][] board){
        for(int i = 0; i<brett.length; i++){
            for(int j = 0; j<brett[i].length; j++){
                brett[i][j]='-';
            }
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
            if (ts.getState()==1){
                brett[board.getRows()-1-tile.getX()][tile.getY()] = 'B';
            }
            else{
                brett[board.getRows()-1-tile.getX()][tile.getY()] = 'A';
            }
            //System.out.println(tile.getX()+" "+tile.getY());
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

    private String printBoard(){
        midBoard = "\n";
        for (int row = 0; row < board.getRows(); row++){
            for (int column = 0; column < board.getColumns(); column++){
                if(column ==0){
                    midBoard+=" ";
                }
                midBoard += brett[row][column];
                if(column !=(board.getColumns()-1)){
                    midBoard+= " | ";
                }
            }
            midBoard += " \n";
            if( row != (board.getRows()-1)){
                for (int k = 0; k<board.getColumns();k++){
                    midBoard += "------";
                }
            }
            midBoard += "\n";
        }
        return midBoard;
    }

    public void generateBoard(Board board){
        float xFactor = MyGdxGame.WIDTH / board.getColumns();
        float yFactor = MyGdxGame.HEIGHT / board.getRows();
        float xPosition = 0;
        float yPosition = 0;
        int id = 0;
        for (int row = 0; row < board.getRows(); row++){
            for (int column = 0; column < board.getColumns(); column++){
                tiles.add(new Tile(xPosition, yPosition, xFactor, yFactor, id,row,column));
                xPosition += xFactor;
                id++;
            }
            id++;
            yPosition += yFactor;
            xPosition = 0;
        }
    }

    private char isWinner(char[][] board,int amountToWin){
        char[][] brett1 = board;
        char winner = '-';

        //Checking rows
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j<board[0].length-2;j++){
                if(board[i][0+j]==board[i][1+j] && board[i][0+j]==board[i][2+j] && board[i][0+j]!='-'){
                    return board[i][0+j];
                }
            }
        }
        //Checking columns
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j<board.length-2;j++){
                if(board[0+j][i]==board[1+j][i] && board[0+j][i]==board[2+j][i] && board[0+j][i]!='-'){
                    return board[0+j][i];
                }
            }
        }

        //Checking diagonal \
        for (int i = 0; i<board.length-2;i++){
            int teller = 0;
            if(board[0+i][0+i] == board[1+i][1+i] && board[0+i][0+i] == board[2+i][2+i] && board[0+i][0+i] != '-'){
                return board[0+i][0+i];

                //teller ++;
            }
        }

        //Checking diagonal /
        for (int i = 0; i<board.length-2;i++){
            if(board[0+i][2+i] == board[1+i][1+i] && board[0+i][2+i] == board[2+i][0+i] && board[0+i][2+i] != '-'){
                return board[0+i][2+i];
            }
        }
        return '-';
    }
}
