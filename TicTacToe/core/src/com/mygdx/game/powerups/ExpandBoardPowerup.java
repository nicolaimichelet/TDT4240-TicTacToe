package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 10.04.2018.
 */

public class ExpandBoardPowerup extends InputHandler implements com.mygdx.game.powerups.Powerup {

    private Texture expandIcon;
    private Singleton singleton = Singleton.getInstance();

    public ExpandBoardPowerup() {
        this.expandIcon = new Texture("expand.png"); // change this later to right png
    }

    public void expand(Board currentBoard){
        if (currentBoard.getRows() <= 6){
            System.out.println("Calling expand");
            Board newBoard = new Board(currentBoard.getRows() + 1, currentBoard.getColumns() + 1);
            ArrayList<Tile> newTiles = newBoard.generateBoard();
            singleton.setTiles(newTiles);
            updateMarks(newTiles);
            singleton.setBoardTiles(newBoard.setBoardTiles());
            singleton.setBoard(newBoard);
            singleton.setN(currentBoard.getRows()+1);
        }
        else {
            singleton.updatePowerupList();
            System.out.println("kan ikke forstørre brettet mer");
        }
    }

    public void updateMarks(ArrayList<Tile> newTiles){
        ArrayList<TileState> newBoardState = new ArrayList<TileState>();
        for (Tile t : newTiles) {
            for (TileState ts : singleton.getBoardState()) {
                if (t.getX() == ts.getTile().getX() && t.getY() == ts.getTile().getY()){
                    TileState newTileState = new TileState(t, ts.getState());
                    t.setMarked(true);
                    newBoardState.add(newTileState);
                }
            }
        }
        singleton.setBoardState(newBoardState);
        setIndexToRemovePowerup();
    }

    @Override
    public void update(float dt) {
        if (touchDown()){
            singleton.setpowerupSelected(this);
        }
    }

    @Override
    public Texture getTexture() {
        return expandIcon;
    }

    public void setIndexToRemovePowerup(){
        Player player = singleton.getPlayers().get(singleton.getPlayerState());
        int index = 0;
        if (player.getPowerups().size() > 0){
            for (Powerup pu : player.getPowerups()){
                if (pu instanceof ExpandBoardPowerup){
                    singleton.setIndexTopowerupToRemove(index);
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public String getType() {
        return "ExpandBoardPowerup";
    }
}
