package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.powerups.ObstaclePowerup;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.powerups.SwapPowerup;

import java.util.ArrayList;


/**
 * Created by eiriksandberg on 09.04.2018.
 */

    /**
     * Created by eiriksandberg on 22.01.2018.
     */

    public class Tile extends InputHandler{
        private Texture tile;
        private int id;
        private boolean isMarked;
        private int x,y;
        Singleton singleton = Singleton.getInstance();
        private Powerup powerup;

        public Tile(float positionX, float positionY, float width, float height, int x, int y){
            setPosition(new Vector3(positionX, positionY, 0));
            setHeight(height);
            setWidth(width);
            this.tile = new Texture("tile.png");
            this.isMarked = false;
            this.x = x;
            this.y = y;
            //System.out.println("Width: "+width+" , Height: "+height);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void update(float dt){
            if (singleton.getPowerupSelected() != null){
                handlePowerups();
            } else{
                placeMark();
            }
        }

        public void handlePowerups(){
            com.mygdx.game.powerups.Powerup pu = singleton.getPowerupSelected();
            if (pu instanceof SwapPowerup){
                //System.out.println("Swap selected");
                if (touchDown()){
                    if (((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile1() == null){
                        if (((com.mygdx.game.powerups.SwapPowerup) pu).setSelectedTile1(this)){
                            System.out.println("Sucessfully selected first tile");
                        } else{
                            System.out.println("Failed setting first tile");
                        }
                    } else {
                        if (((SwapPowerup) pu).getSelectedTile1() != this){
                            if (((com.mygdx.game.powerups.SwapPowerup) pu).setSelectedTile2(this)){
                                ((com.mygdx.game.powerups.SwapPowerup) pu).swap(((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile1(), ((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile2());
                                System.out.println("Successfully swapped");
                                singleton.setpowerupSelected(null);
                            } else{
                                System.out.println("Failed setting second tile");
                            }
                        }
                    }
                }
            }
            if (pu instanceof ExpandBoardPowerup){
                ((ExpandBoardPowerup) pu).expand(singleton.getBoard());
            }
            if (pu instanceof ObstaclePowerup){
                if (touchDown()){
                    ((ObstaclePowerup) pu).setObstacle(this);
                    singleton.setpowerupSelected(null);
                }
            }
        }

        public void setMarked(boolean marked) {
            isMarked = marked;
        }

        public boolean isMarked() {
            return isMarked;
        }

        public void placeMark(){
            if (touchDown()){
                if (powerup != null){
                    Player player = singleton.getPlayers().get(singleton.getPlayerState());
                    if (player.getPowerups() == null){
                        ArrayList<Powerup> pulist = new ArrayList<Powerup>();
                        pulist.add(powerup);
                        player.setPowerups(pulist);
                    } else{
                        player.getPowerups().add(powerup);
                    }
                    powerup = null;
                }
                if (!isMarked){
                    singleton.addBoardState(new TileState(this, singleton.getPlayerState()));
                    singleton.changePlayerState();
                    isMarked = true;
                }

            }

        }

        public Texture getTexture() {
            return tile;
        }

        public Texture getTile() {
            return tile;
        }

        public Powerup getPowerup() {
            return powerup;
        }

        public void setPowerup(Powerup powerup) {
            this.powerup = powerup;
        }
    }
