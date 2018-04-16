package com.mygdx.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
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
                    ArrayList<TileState> newTileState = singleton.getBoardState();
                    newTileState.add(new TileState(t, -1));
                    singleton.setBoardState(newTileState);
                } else{
                    System.out.println("Cannot place obstacle there. Already in use");
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        if (touchDown()){
            singleton.setpowerupSelected(this);
            System.out.println("Obstacle selected");
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
