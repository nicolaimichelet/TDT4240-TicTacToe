package com.mygdx.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Singleton.Singleton;

/**
 * Created by KasperKBerg on 20.04.2018.
 */

public class GameMusic {

    private Sound lobbySound,gameSound,winningSound,drawSound;
    private boolean isMuted, isPlaying;
    private static Singleton singleton = Singleton.getInstance();




    public GameMusic(){

        lobbySound = Gdx.audio.newSound(Gdx.files.internal("lobby.mp3"));
        gameSound = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
        winningSound = Gdx.audio.newSound(Gdx.files.internal("winning.mp3"));
        drawSound = Gdx.audio.newSound(Gdx.files.internal("aah.mp3"));

    }



    public void playSound(int i){
        if (!isMuted){
            if (i == 0){
                lobbySound.loop(1.0f);
                isPlaying=true;
            }
            else if (i == 1){
                gameSound.play(1.0f);
                isPlaying=true;
            }
            else if (i == 2){
                winningSound.play(1.0f);
            }
            else if (i == 3){
                drawSound.play(1.0f);
            }
        }
    }

    public void resumeSound(int i){
        if (!isMuted){
            if (i == 0){
                lobbySound.resume();
                isPlaying=true;
            }
            else if (i == 1){
                gameSound.resume();
                isPlaying=true;
            }
        }
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public boolean isMuted(){
        return isMuted;
    }

    public void muteSound() {
        isMuted = true;
        pauseSound(0);
    }

    public void unMuteSound(){
        isMuted = false;
    }

    public void pauseSound(int i){
        if (i == 0){
            lobbySound.pause();
            isPlaying=false;
        }
        else if (i == 1){
            gameSound.pause();
            isPlaying=false;
        }
    }

    public void stopSound(int i){
        if (i == 0){
            lobbySound.dispose();
            isPlaying=false;
        }
        else if (i == 1){
            gameSound.dispose();
            isPlaying=false;
        }
        else if (i == 2){
            winningSound.dispose();
        }
        else if (i == 3){
            System.out.println("Play draw sound");
        }
    }


}
