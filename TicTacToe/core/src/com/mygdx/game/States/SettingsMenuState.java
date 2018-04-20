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
    private TextButton threeButton;
    private TextButton fourButton;
    private TextButton fiveButton;
    private TextButton backButton;

    private Label settingsLabel;

    private Singleton singleton = Singleton.getInstance();


    public SettingsMenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeLabels();
        initializeButtons();

//        Add buttons and labels to stage
        stage.addActor(settingsLabel);
        stage.addActor(threeButton);
        stage.addActor(fourButton);
        stage.addActor(fiveButton);
        stage.addActor(backButton);
    }

    @Override
    public void handleInput() {
        if(backButton.isPressed()){
            //singleton.resetSingleton();
            gsm.set(new MainMenuState(gsm));
            dispose();
        }
        else if (threeButton.isPressed()){
            singleton.setN(3);
            gsm.set(new MainMenuState(gsm));
        }
        else if (fourButton.isPressed()){
            singleton.setN(4);
            gsm.set(new MainMenuState(gsm));
        }
        else if (fiveButton.isPressed()){
            singleton.setN(5);
            gsm.set(new MainMenuState(gsm));
        }

//        if(exitButton.isPressed()){
//            Gdx.app.exit();
//        }
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
        threeButton = new TextButton("3x3 Grid", skin);
        threeButton.setPosition((Gdx.graphics.getWidth() - threeButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 + 4 * threeButton.getHeight())/2));

        fourButton = new TextButton("4x4 Grid", skin);
        fourButton.setPosition((Gdx.graphics.getWidth() - fourButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 + fourButton.getHeight())/2));

        fiveButton = new TextButton("5x5 Grid", skin);
        fiveButton.setPosition((Gdx.graphics.getWidth() - fiveButton.getWidth())/2,((Gdx.graphics.getHeight() - 5 * fiveButton.getHeight())/2));

        backButton = new TextButton("Back", skin);
        backButton.setPosition((Gdx.graphics.getWidth() - fourButton.getWidth())/2, ((Gdx.graphics.getHeight() - 8 * backButton.getHeight())/2));
    }

    private void initializeLabels(){
        BitmapFont settingsFont = new BitmapFont(Gdx.files.internal("winnerFont.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = settingsFont;
        String settingsMenuText = "Settings";

        settingsLabel = new Label(settingsMenuText, style);
        settingsLabel.setPosition((Gdx.graphics.getWidth() - settingsLabel.getWidth())/2, (float)(Gdx.graphics.getHeight() / 1.4));
    }

    //    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}

