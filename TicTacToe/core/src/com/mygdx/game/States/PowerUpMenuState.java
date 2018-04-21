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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Singleton.Singleton;

public class PowerUpMenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private Singleton singleton = Singleton.getInstance();

//    Buttons
    private TextButton backButton;

//    Labels
    private Label aboutTitleLabel, aboutGameLabel, powerUpLabel, aboutPowerUpLabel, swapLabel, expandBoardLabel, obstacleLabel, stopLabel;

//    Textures
    private Texture swap;
    private Texture expandBoard;
    private Texture obstacle;
    private Texture obstacleStop;


    public PowerUpMenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeButtons();
        initializeTextures();
        initializeLabels();

        // Add labels to stage
        stage.addActor(aboutTitleLabel);
        stage.addActor(aboutGameLabel);
        stage.addActor(powerUpLabel);
        stage.addActor(aboutPowerUpLabel);
        stage.addActor(swapLabel);
        stage.addActor(expandBoardLabel);
        stage.addActor(obstacleLabel);
        stage.addActor(stopLabel);

//        Add buttons to stage
        stage.addActor(backButton);
        if (!singleton.isPlaying()){
            singleton.playSound(0);
        }

    }

    @Override
    public void handleInput() {
        if(backButton.isPressed()) {
            gsm.set(new MainMenuState(gsm, singleton.getN()));
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
        sb.draw(swap, 40, Gdx.graphics.getHeight() - 410, 30, 30);
        sb.draw(expandBoard, 40, Gdx.graphics.getHeight() - 470, 30, 30);
        sb.draw(obstacle, 40, Gdx.graphics.getHeight() - 530, 30, 30);
        sb.draw(obstacleStop, 40,Gdx.graphics.getHeight() -590, 30,30);
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
        BitmapFont font = new BitmapFont(Gdx.files.internal("aboutText.fnt"));
        BitmapFont font2 = new BitmapFont(Gdx.files.internal("descriptionText.fnt"));

        skin = new Skin();
        skin.add("default", font);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        skin.add("aboutWhite", font2);
        Label.LabelStyle style2 = new Label.LabelStyle();
        style2.font = font2;



//        Label text
        String aboutTitle = "About";
        String aboutGameText = "Think outside the BOX gives a new twist \n " +
                "to the original Tic Tac Toe game. \n " +
                "Collect \"power-ups\" and obstacles \n " +
                "in order to outsmart\n " +
                "and crush your opponent.\n"
                ;
        String powerUpText = "Power-ups";
        String aboutPowerUpText = "Power-ups are spawned and can \n" +
                " be picked up during gameplay";
        String swapText = "Swap one of your marks with one \n " +
                "of the other player's marks";
        String expandBoardText = "Expand the board with one row \n " +
                "and column";
        String obstacleText = "Put an obstacle on the board, \n making it impossible to place \n" +
                "a mark in that tile";
        String stopText = "Obstacle - can't use this tile";

//        Label initializations
        aboutTitleLabel = new Label(aboutTitle, style);
        aboutTitleLabel.setPosition((Gdx.graphics.getWidth() - aboutTitleLabel.getWidth()) / 2,Gdx.graphics.getHeight() - 130);
        System.out.println(aboutTitleLabel);

        aboutGameLabel = new Label(aboutGameText, style2);
        aboutGameLabel.setPosition((Gdx.graphics.getWidth() - aboutGameLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 250);

        powerUpLabel = new Label(powerUpText, style);
        powerUpLabel.setPosition((Gdx.graphics.getWidth() - powerUpLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 300);

        aboutPowerUpLabel = new Label(aboutPowerUpText, style2);
        aboutPowerUpLabel.setPosition((Gdx.graphics.getWidth() - aboutPowerUpLabel.getWidth()) / 2, Gdx.graphics.getHeight() - 350);

        swapLabel = new Label(swapText, style2);
        swapLabel.setPosition(80, Gdx.graphics.getHeight() - 410);

        expandBoardLabel = new Label(expandBoardText, style2);
        expandBoardLabel.setPosition(80, Gdx.graphics.getHeight() - 470);

        obstacleLabel = new Label(obstacleText, style2);
        obstacleLabel.setPosition(80, Gdx.graphics.getHeight() - 550);

        stopLabel = new Label(stopText, style2);
        stopLabel.setPosition(80, Gdx.graphics.getHeight() - 590);
    }

    private void initializeButtons(){
        backButton = new TextButton("Back", skin);
        backButton.setPosition((Gdx.graphics.getWidth() - backButton.getWidth())/2, 100);
    }


    private void initializeTextures(){
//        swap = new Texture("swap.png");
        swap = new Texture("swap.png");
        expandBoard = new Texture("expand.png");
        obstacle = new Texture("trash.png");
        obstacleStop = new Texture("white.png");
    }

    //    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}




