package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Singleton.Singleton;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class MenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton powerUpButton;
    private TextButton highscoreButton;
    private TextButton exitButton;
    private Texture background = new Texture("white.png");
    private Singleton singleton = Singleton.getInstance();

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeButtons();

//        Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(powerUpButton);
        stage.addActor(highscoreButton);
        stage.addActor(exitButton);

    }

    @Override
    public void handleInput() {
        if(playButton.isPressed()){
            singleton.resetSingleton();
            gsm.set(new PlayState(gsm,5));

            dispose();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(exitButton.isPressed()){
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Resize stage viewport
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        stage.act();
        stage.draw();
        sb.end();
    }

    @Override
    public void dispose() {}
  
//    Create skin for buttons
    private void createSkin(){
        // Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        // Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/15, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }

    private void initializeButtons(){
        playButton = new TextButton("Play now", skin);
        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth())/2, ((Gdx.graphics.getHeight() + 4 * playButton.getHeight())/2));

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition((Gdx.graphics.getWidth() - settingsButton.getWidth())/2, ((Gdx.graphics.getHeight() + playButton.getHeight())/2));

        powerUpButton = new TextButton("Power ups", skin);
        powerUpButton.setPosition((Gdx.graphics.getWidth() - powerUpButton.getWidth())/2, ((Gdx.graphics.getHeight() - 2 * powerUpButton.getHeight())/2));

        highscoreButton = new TextButton("Highscore", skin);
        highscoreButton.setPosition((Gdx.graphics.getWidth() - highscoreButton.getWidth())/2, ((Gdx.graphics.getHeight() - 5 * highscoreButton.getHeight())/2));

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight()/2) - 4 * exitButton.getHeight());
    }

//    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}



