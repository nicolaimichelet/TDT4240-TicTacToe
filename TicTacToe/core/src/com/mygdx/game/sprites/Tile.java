package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
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

        private int x,y;
        Singleton singleton = Singleton.getInstance();

        public Tile(float positionX, float positionY, float width, float height, int id, int x, int y){
            this.position = new Vector3(positionX, positionY, 0);
            this.tile = new Texture("tile.png");
            this.height = height;
            this.width = width;
            this.id = id;
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
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (isClicked(Gdx.input.getX(), Gdx.input.getY())){
                    if (!isMarked){
                        singleton.addBoardState(new TileState(this, singleton.getPlayerState()));

                        singleton.changePlayerState();
                        isMarked = true;
                        System.out.println("X: "+x+" , Y: "+y);
                    } else {

                    }
                }
            }
        }

        public boolean isClicked(int x, int y){
            float tileX = getPosition().x;
            float tileY = MyGdxGame.HEIGHT-60-getPosition().y;
            /*
            System.out.print("x: "+x+", y:"+y);
            System.out.println();
            System.out.print("    TileX: "+tileX+", TileY:"+tileY);
            System.out.println();
            */
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
