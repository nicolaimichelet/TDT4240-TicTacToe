package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Singleton.Singleton;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class MainMenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton powerUpButton;
    private TextButton exitButton;
    private Label titleLabel, NxNLabel;

    private Texture menuPic;

    private Singleton singleton = Singleton.getInstance();
    private boolean isMuted;

    public MainMenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create skin for buttons
        createSkin();
        initializeButtons();
        initializeLabels();

        menuPic = new Texture("thinkOutsideTheBox.png");

   /*     image = new Image();
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(menuPic)));
        image.setSize(menuPic.getWidth() / 4, menuPic.getHeight() / 4);
        menuPic.setPosition(Gdx.graphics.getWidth() - image.getImageWidth() / 2, Gdx.graphics.getHeight() - image.getImageHeight() / 2);
*/


        // Add labels to stage
        stage.addActor(NxNLabel);

        //Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(powerUpButton);
        stage.addActor(exitButton);

        // Start lobby music
        if (!singleton.isPlaying() && !singleton.isMuted()){
            singleton.playSound(0);
        }

    }

    @Override
    public void handleInput() {
        if(playButton.isPressed()){
            isMuted = singleton.isMuted();
            singleton.resetSingleton();
            gsm.set(new PlayState(gsm,singleton.getN(),isMuted));
            singleton.stopSound(0);
            dispose();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(settingsButton.isPressed()){
            gsm.set(new SettingsMenuState(gsm));
        }
        if(powerUpButton.isPressed()){
//            Change to power-up state
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

        stage.getBatch().begin();

        stage.getBatch().draw(menuPic,Gdx.graphics.getWidth() * 0.13f, Gdx.graphics.getHeight()*0.6f,Gdx.graphics.getWidth()*0.90f, Gdx.graphics.getHeight()*0.4f);
        stage.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
    }
  
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

    private void initializeLabels(){
        BitmapFont font = new BitmapFont(Gdx.files.internal("menuText.fnt"));
        skin = new Skin();
        skin.add("default", font);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;


        NxNLabel = new Label(singleton.getN()+"x"+singleton.getN(),style);
        NxNLabel.setPosition((Gdx.graphics.getWidth()-NxNLabel.getWidth())/2,(float)(Gdx.graphics.getHeight() / 1.8));
    }

    private void initializeButtons(){

        playButton = new TextButton("Play now", skin);
        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth())/2, ((Gdx.graphics.getHeight() - 2  * playButton.getHeight())/2));

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition((Gdx.graphics.getWidth() - settingsButton.getWidth())/2, ((Gdx.graphics.getHeight() + (float)0.8 - 5 * settingsButton.getHeight())/2));

        powerUpButton = new TextButton("Power ups", skin);
        powerUpButton.setPosition((Gdx.graphics.getWidth() - powerUpButton.getWidth())/2, ((Gdx.graphics.getHeight() + (float)0.8 - 8 * powerUpButton.getHeight())/2));

        /*highscoreButton = new TextButton("Highscore", skin);
        highscoreButton.setPosition((Gdx.graphics.getWidth() - highscoreButton.getWidth())/2, ((Gdx.graphics.getHeight() - 5 * highscoreButton.getHeight())/2));
        */
        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight()/2) - 6 * exitButton.getHeight());
    }

//    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}



