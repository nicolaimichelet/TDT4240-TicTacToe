package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.TileState;


/**
 * Created by eiriksandberg on 09.04.2018.
 */

    /**
     * Created by eiriksandberg on 22.01.2018.
     */

    public class Tile {
        private Vector3 position;
        private float height;
        private float width;
        private Texture tile;
        private int id;
        private boolean isMarked;
        Singleton singleton = Singleton.getInstance();

        public Tile(float positionX, float positionY, float width, float height, int id){
            this.position = new Vector3(positionX, positionY, 0);
            this.tile = new Texture("tile.png");
            this.height = height;
            this.width = width;
            this.id = id;
            this.isMarked = false;
        }

        public void update(float dt){
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (isClicked(Gdx.input.getX(), Gdx.input.getY())){
                    if (!isMarked){
                        singleton.addBoardState(new TileState(this, singleton.getPlayerState()));
                        System.out.println(singleton.getPlayerState());
                        singleton.changePlayerState();
                        isMarked = true;
                    } else {

                    }
                }
            }
        }

        public boolean isClicked(int x, int y){
            float tileX = getPosition().x;
            float tileY = getPosition().y;
            if (x >= tileX && x <= tileX + width && y >= tileY && y <= tileY + height){
                return true;
            } else{
                return false;
            }
        }

        public Vector3 getPosition() {
            return position;
        }

        public Texture getTexture() {
            return tile;
        }

        public float getHeight() {
            return height;
        }

        public float getWidth() {
            return width;
        }

        public Texture getTile() {
            return tile;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "position=" + position +
                    ", height=" + height +
                    ", width=" + width +
                    ", tile=" + tile +
                    ", isMarked=" + isMarked +
                    ", singleton=" + singleton +
                    '}';
        }
    }
