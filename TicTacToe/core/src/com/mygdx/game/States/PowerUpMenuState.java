package com.mygdx.game.States;

/**
 * Created by Simen on 20.04.2018.
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

public class PowerUpMenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextButton backButton;
    private Label aboutTitleLabel, aboutGameLabel, powerUpLabel;

    private Singleton singleton = Singleton.getInstance();

    private Texture swapIcon;
    private Texture expandBoard;

    public PowerUpMenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeButtons();
        initializeLabels();
        initializeTextures();

        // Add labels to stage
        stage.addActor(aboutTitleLabel);
        stage.addActor(aboutGameLabel);
        stage.addActor(powerUpLabel);

//        Add buttons to stage
        stage.addActor(backButton);
        if (!singleton.isPlaying()){
            singleton.playSound(0);
        }

    }

    @Override
    public void handleInput() {
        if(backButton.isPressed()){
            gsm.set(new MainMenuState(gsm));
            dispose();
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
        sb.draw(swapIcon, 40, Gdx.graphics.getHeight() - 350, 30, 30);
        sb.draw(expandBoard, 40, Gdx.graphics.getHeight() - 410, 30, 30);
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

    private void initializeLabels(){
        BitmapFont font = new BitmapFont(Gdx.files.internal("menuText.fnt"));
        skin = new Skin();
        skin.add("default", font);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        String aboutTitle = "About";
        String aboutGameText = "TicTacToe Unleashed gives a new twist to the original game. By introducing \"power-ups\" and obstacles on the board, the game becomes rather fun to play.";
        String powerUpText = "Power-ups";

        aboutTitleLabel = new Label(aboutTitle, style);
        aboutTitleLabel.setPosition((Gdx.graphics.getWidth() - aboutTitleLabel.getWidth()) / 2,Gdx.graphics.getHeight() - 150);

        aboutGameLabel = new Label(aboutGameText, style);
        aboutGameLabel.setPosition((Gdx.graphics.getWidth() - aboutGameLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 200);

        powerUpLabel = new Label(powerUpText, style);
        powerUpLabel.setPosition((Gdx.graphics.getWidth() - powerUpLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 300);
    }

    private void initializeButtons(){
        backButton = new TextButton("Back", skin);
        backButton.setPosition((Gdx.graphics.getWidth() - backButton.getWidth())/2, 100);
    }

    private void initializeTextures(){
        swapIcon = new Texture("swap.png");
        expandBoard = new Texture("expand.png");
    }

    //    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}




