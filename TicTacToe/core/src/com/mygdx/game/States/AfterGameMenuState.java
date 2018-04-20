package com.mygdx.game.States;

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


/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class AfterGameMenuState implements State {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;

//    Buttons
    private TextButton playAgainButton;
    private TextButton mainMenuButton;
    private TextButton exitButton;
    private Singleton singleton = Singleton.getInstance();
    private boolean isMuted;

//    Labels
    Label congratulationsLabel;
    Label winnerLabel;

    public AfterGameMenuState(GameStateManager gsm, char winner, boolean isMuted) {
        this.gsm = gsm;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createSkin(); // Create skin for buttons
        initializeLabels(winner);
        initializeButtons();

        //Add buttons to stage
        stage.addActor(congratulationsLabel);
        stage.addActor(winnerLabel);
        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);
        stage.addActor(exitButton);


        this.isMuted = isMuted;
        if (!isMuted){
            singleton.playSound(0);
        }
    }

    @Override
    public void handleInput() {
        if(playAgainButton.isPressed()){
            singleton.resetSingleton();
            singleton.stopSound(0);
            gsm.set(new PlayState(gsm,singleton.getN(),isMuted));
            dispose();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(mainMenuButton.isPressed()){
            //singleton.resetSingleton();
            gsm.set(new MainMenuState(gsm));
            dispose();
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
        playAgainButton = new TextButton("Play again", skin);
        playAgainButton.setPosition((Gdx.graphics.getWidth() - playAgainButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 + 4 * playAgainButton.getHeight())/2));

        mainMenuButton = new TextButton("Main menu", skin);
        mainMenuButton.setPosition((Gdx.graphics.getWidth() - mainMenuButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 + playAgainButton.getHeight())/2));

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, ((Gdx.graphics.getHeight() * (float)0.8 - 2 * exitButton.getHeight())/2));
    }

    private void initializeLabels(char winner){
        BitmapFont fontWinner = new BitmapFont(Gdx.files.internal("winnerFont.fnt"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontWinner;
        String congratulationsText;

        if (winner == 'D'){
            congratulationsText = "It's a draw!";
            congratulationsLabel = new Label(congratulationsText, style);
            congratulationsLabel.setPosition((Gdx.graphics.getWidth() - congratulationsLabel.getWidth())/2, (float)(Gdx.graphics.getHeight() / 1.2));
        }
        else{
            congratulationsText = "Congratulations!";
            congratulationsLabel = new Label(congratulationsText, style);
            congratulationsLabel.setPosition((Gdx.graphics.getWidth() - congratulationsLabel.getWidth())/2, (float)(Gdx.graphics.getHeight() / 1.2));

            String winnerText = "Player " + winner + " has won!";
            winnerLabel = new Label(winnerText, style);
            winnerLabel.setPosition((Gdx.graphics.getWidth() - winnerLabel.getWidth())/2, (float)(congratulationsLabel.getY() - winnerLabel.getHeight() * 1.5));
        }
    }

    //    Update stage viewport when screen is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}



