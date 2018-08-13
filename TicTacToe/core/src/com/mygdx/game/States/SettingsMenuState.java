package com.mygdx.game.States;

/**
 * Created by Simen on 19.04.2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Singleton.Singleton;


public class SettingsMenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private Texture settingWheel;

//    Buttons
    private TextButton threeButton, fourButton, fiveButton, backButton, muteButton;

//    Labels
    private Label settingsLabel;

    private Singleton singleton = Singleton.getInstance();


    public SettingsMenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeLabels();
        initializeButtons();

        settingWheel = new Texture("settingWheel.png");


//        Add buttons and labels to stage
        stage.addActor(settingsLabel);
        stage.addActor(threeButton);
        stage.addActor(fourButton);
        stage.addActor(fiveButton);
        stage.addActor(backButton);
        stage.addActor(muteButton);
    }

    @Override
    public void handleInput() {
        if(backButton.isPressed()){
            gsm.set(new MainMenuState(gsm,singleton.getN()));
            dispose();
        }
        else if (threeButton.isPressed()){
            gsm.set(new MainMenuState(gsm,3));
        }
        else if (fourButton.isPressed()){
            gsm.set(new MainMenuState(gsm,4));
        }
        else if (fiveButton.isPressed()){
            gsm.set(new MainMenuState(gsm,5));
        }

        else if(muteButton.isPressed()){
            if (singleton.isMuted()){
                singleton.unMuteSound();
                singleton.resumeSound(0);
                muteButton.setText("Mute sound");
            }
            else{
                singleton.muteSound();
                muteButton.setText("Unmute sound");
            }
            try {
                Thread.sleep(100);
            }
            catch (Exception e){
                System.out.println("SleepingError "+e);
            }
        }
    }

    @Override
    public void update(float dt) {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Resize stage viewport
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.act();
        stage.draw();
        sb.begin();
        sb.draw(settingWheel, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/(float)1.2 -10, 60, 60);
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


        threeButton = new TextButton("3x3 Grid", skin);
        threeButton.setPosition((Gdx.graphics.getWidth() - threeButton.getWidth())/2, (Gdx.graphics.getHeight() + 4 * threeButton.getHeight())/2);//(Gdx.graphics.getHeight() * (float)0.8 + 1 * threeButton.getHeight())/2);

        fourButton = new TextButton("4x4 Grid", skin);
        fourButton.setPosition((Gdx.graphics.getWidth() - fourButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 + 4 * fourButton.getHeight())/2));

        fiveButton = new TextButton("5x5 Grid", skin);
        fiveButton.setPosition((Gdx.graphics.getWidth() - fiveButton.getWidth())/2,((Gdx.graphics.getHeight() * (float)0.8 + fiveButton.getHeight())/2));

        if (singleton.isMuted()){
            muteButton = new TextButton("Unmute sound",skin);
        }
        else{
            muteButton = new TextButton("Mute sound",skin);
        }
        muteButton.setPosition((Gdx.graphics.getWidth() - muteButton.getWidth())/2,((Gdx.graphics.getHeight() - 5 * muteButton.getHeight())/2));

        backButton = new TextButton("Back", skin);
        backButton.setPosition((Gdx.graphics.getWidth() - fourButton.getWidth())/2, ((Gdx.graphics.getHeight() - 8 * backButton.getHeight())/2));
    }

    private void initializeLabels(){
        BitmapFont settingsFont = new BitmapFont(Gdx.files.internal("menuText.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = settingsFont;
        String settingsMenuText = "Settings";

        settingsLabel = new Label(settingsMenuText, style);
        settingsLabel.setPosition((Gdx.graphics.getWidth() - settingsLabel.getWidth())/2, (float)(Gdx.graphics.getHeight() / 1.2));
    }

    //    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}

