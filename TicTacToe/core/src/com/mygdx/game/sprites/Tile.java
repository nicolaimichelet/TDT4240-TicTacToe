package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.InputHandler;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.powerups.ObstaclePowerup;


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
            if (pu instanceof com.mygdx.game.powerups.SwapPowerup){
                //System.out.println("Swap selected");
                if (touchDown()){
                    if (((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile1() == null){
                        ((com.mygdx.game.powerups.SwapPowerup) pu).setSelectedTile1(this);
                        System.out.println("Sucessfully selected first tile");
                    } else {
                        ((com.mygdx.game.powerups.SwapPowerup) pu).setSelectedTile2(this);
                        ((com.mygdx.game.powerups.SwapPowerup) pu).swap(((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile1(), ((com.mygdx.game.powerups.SwapPowerup) pu).getSelectedTile2());
                        System.out.println("Successfully swapped");
                    }
                }
            }
            if (pu instanceof ExpandBoardPowerup){
                ((ExpandBoardPowerup) pu).expand(singleton.getBoard());
            }
            if (pu instanceof ObstaclePowerup){
                if (touchDown()){
                    System.out.println("HEEEY");
                    ((ObstaclePowerup) pu).setObstacle(this);
                }
            }
        }


        public void placeMark(){
            if (touchDown()){
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


    }
