package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 15.04.2018.
 */

public class ObstaclePowerup extends InputHandler implements Powerup {
    private Singleton singleton = Singleton.getInstance();
    private Texture texture;

    public ObstaclePowerup() {
        this.texture = new Texture("trash.png");
    }

    public void setObstacle(Tile t){
        for (Tile tile : singleton.getTiles()){
            if (tile.getX() == t.getX() && tile.getY() == t.getY()){
                boolean canPlaceObstacle = true;
                for (TileState tileState : singleton.getBoardState()) {
                    if (tileState.getTile().getX() == t.getX() && tileState.getTile().getY() == t.getY()){
                        canPlaceObstacle = false;
                    }
                }
                if (canPlaceObstacle){
                    t.setMarked(true);
                    ArrayList<TileState> newTileState = singleton.getBoardState();
                    newTileState.add(new TileState(t, -1));
                    singleton.setBoardState(newTileState);
                }
            }
        }
        setIndexToRemovePowerup();
    }

    public void setIndexToRemovePowerup(){
        Player player = singleton.getPlayers().get(singleton.getPlayerState());
        int index = 0;
        if (player.getPowerups().size() > 0){
            for (Powerup pu : player.getPowerups()){
                if (pu instanceof ObstaclePowerup){
                    singleton.setIndexTopowerupToRemove(index);
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public void update(float dt) {
        if (touchDown()) {
            if (singleton.getPowerupSelected() != null) {
                if (singleton.getPowerupSelected().equals(this)) {
                    singleton.setpowerupSelected(null);
                }
            } else {
                singleton.setpowerupSelected(this);
                System.out.println("Obstacle selected");
            }
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public String getType() {
        return "ObstaclePowerup";
    }
}
