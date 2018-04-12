package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class MenuState implements State {
//    BitmapFont font = new BitmapFont();
    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;
//    private Stage stage;

//    private Skin skin;
//    TextButton playButton;
//    TextButton settingsButton;

    private Skin skin;
    private BitmapFont font;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton powerUpButton;
    private TextButton highscoreButton;
    private TextButton exitButton;
//    private TextButton task3Btn;

    Stage stage;




    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create font for button
        font = new BitmapFont();
        font.setColor(Color.BLUE);

        createSkin();

        playButton = new TextButton("Play now", skin);
        playButton.setPosition((MyGdxGame.WIDTH - playButton.getWidth())/2, ((MyGdxGame.HEIGHT + 3 * playButton.getHeight())/2));

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition((MyGdxGame.WIDTH - settingsButton.getWidth())/2, (MyGdxGame.HEIGHT/2));

        powerUpButton = new TextButton("Power ups", skin);
        powerUpButton.setPosition((MyGdxGame.WIDTH - powerUpButton.getWidth())/2, ((MyGdxGame.HEIGHT - 3 * powerUpButton.getHeight())/2));

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition((MyGdxGame.WIDTH - exitButton.getWidth())/2, (MyGdxGame.HEIGHT/2) - 3 * exitButton.getHeight());

        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(powerUpButton);
        stage.addActor(highscoreButton);
        stage.addActor(exitButton);


    }

    @Override
    public void handleInput() {
        if(playButton.isPressed()){
            gsm.set(new PlayState(gsm));
        }
        if(exitButton.isPressed()){
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {}


//    Create skin for buttons
    private void createSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/15, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

}



