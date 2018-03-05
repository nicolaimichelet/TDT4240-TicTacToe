package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 23.01.2018.
 */

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        ArrayList<Texture> textures = getTexturesToAnimation();
        for (Texture t : textures){
            frames.add(new TextureRegion(t));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }
    public void update(float dt){
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount){
            frame = 0;
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public ArrayList<Texture> getTexturesToAnimation(){
        Texture t1 = new Texture("heli1.png");
        Texture t2 = new Texture("heli2.png");
        Texture t3 = new Texture("heli3.png");
        Texture t4 = new Texture("heli4.png");
        ArrayList<Texture> textures = new ArrayList<Texture>();
        textures.add(t1);
        textures.add(t2);
        textures.add(t3);
        textures.add(t4);
        return textures;
    }
}
