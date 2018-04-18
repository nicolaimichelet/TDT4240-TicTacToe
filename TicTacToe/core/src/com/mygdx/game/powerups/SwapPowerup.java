package com.mygdx.game.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class SwapPowerup extends InputHandler implements com.mygdx.game.powerups.Powerup {
    private Texture swapIcon;
    private Tile selectedTile1;
    private Tile selectedTile2;
    private Singleton singleton = Singleton.getInstance();

    public SwapPowerup() {
        this.swapIcon = new Texture("swap.png");
    }

    public void swap(Tile t1, Tile t2){
        ArrayList<TileState> boardState = singleton.getBoardState();
        for (TileState tileState : boardState) {
            if (tileState.getTile().getX() == t1.getX() && tileState.getTile().getY() == t1.getY()) {
                for (TileState tileState2 : boardState) {
                    if (tileState2.getTile().getX() == t2.getX() && tileState2.getTile().getY() == t2.getY()) {
                        TileState tempts = new TileState(tileState2.getTile(), tileState2.getState());
                        tileState2.setState(tileState.getState());
                        tileState.setState(tempts.getState());
                        break;
                    }
                }
            }
        }
        System.out.println("Swapped");
        setIndexToRemovePowerup();
    }

    public void update(float dt){
        if (touchDown()){
            if (singleton.getPowerupSelected() != null){
                if (singleton.getPowerupSelected().equals(this)){
                    singleton.setpowerupSelected(null);
                }
            } else{
                singleton.setpowerupSelected(this);
                System.out.println("Swapped selected");
            }
        }
    }

    public boolean checkIfCanPlaceSelectedTile(Tile t){
        boolean qualifyAsSwapTile = false;
        for (Tile tile : singleton.getTiles()) {
            if (tile.getX() == t.getX() && tile.getY() == t.getY()) {
                for (TileState tileState : singleton.getBoardState()) {
                    if (tileState.getTile().getX() == t.getX() && tileState.getTile().getY() == t.getY()) {
                        if (tileState.getState() == 0 || tileState.getState() == 1) {
                            qualifyAsSwapTile = true;
                        }
                    }
                }
            }
        }
        return qualifyAsSwapTile;
    }

    public void setIndexToRemovePowerup(){
        Player player = singleton.getPlayers().get(singleton.getPlayerState());
        int index = 0;
        if (player.getPowerups().size() > 0){
            for (Powerup pu : player.getPowerups()){
                if (pu instanceof SwapPowerup){
                    singleton.setIndexTopowerupToRemove(index);
                    break;
                }
                index++;
            }
        }
    }

    public boolean setSelectedTile1(Tile selectedTile1) {
        if (checkIfCanPlaceSelectedTile(selectedTile1)){
            this.selectedTile1 = selectedTile1;
            return true;
        } else{
            return false;
        }

    }

    public boolean setSelectedTile2(Tile selectedTile2) {
        if (checkIfCanPlaceSelectedTile(selectedTile2)){
            this.selectedTile2 = selectedTile2;
            return true;
        } else {
            return false;
        }
    }

    public Tile getSelectedTile1() {
        return selectedTile1;
    }

    public Tile getSelectedTile2() {
        return selectedTile2;
    }

    public Texture getTexture() {
        return swapIcon;
    }

    @Override
    public String getType() {
        return "SwapPowerup";
    }
}
