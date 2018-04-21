package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.domain.GameLogic;
import com.mygdx.game.powerups.ObstaclePowerup;
import com.mygdx.game.powerups.SwapPowerup;
import com.mygdx.game.powerups.Powerup;
import com.mygdx.game.sprites.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eiriksandberg on 22.01.2018.
 */

public class PlayState implements State {

    private Singleton singleton = Singleton.getInstance();
    private GameStateManager gsm;
    private GameLogic gameLogic;
    private Board matrix;
    private ArrayList<Powerup> powerups;
    private Random rm = new Random();
    private int currentMoveCount = rm.nextInt(3);
    private int updateMoveCount;
    private Stage stage;
    private Skin skin;
    private TextButton backButton,soundButton;
    private Label playerTurn, amountToWin;
    private String amountToWinText;
    private boolean isSoundMuted, mutedBefore;
    private String soundButtontext;
    private int n;

    public PlayState(GameStateManager gsm, int n, boolean isMuted) {
        this.n = n;
        matrix = new Board(n, n);
        singleton.setBoard(matrix);
        singleton.setTiles(matrix.generateBoard());
        singleton.setBoardTiles(matrix.setBoardTiles());
        this.gsm = gsm;
        gameLogic = new GameLogic(n);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //Play music
        this.isSoundMuted = isMuted;
        playMusic();

        //Initialize buttons and labels
        initializeButtons();
        stage.addActor(backButton);
        stage.addActor(soundButton);
        stage.addActor(playerTurn);
        stage.addActor(amountToWin);


        //Append powerups
        singleton.setPowerup(new ExpandBoardPowerup());
        singleton.setPowerup(new SwapPowerup());
        singleton.setPowerup(new ObstaclePowerup());


        //Create players
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player(0, null));
        players.add(new Player(1, null));
        singleton.setPlayers(players);
    }

    private void playMusic(){
        if (!isSoundMuted){
            singleton.playSound(1);
            soundButtontext = "Sound off";
        }
        else{
            soundButtontext = "Sound on";
            mutedBefore = true;
        }
    }

    private void initializeButtons(){
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

        BitmapFont playerFont = new BitmapFont(Gdx.files.internal("playerTurnText.fnt"));
        BitmapFont amountToWinFont = new BitmapFont(Gdx.files.internal("amountToWinFont.fnt"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = playerFont;
        String playerText = "Player "+singleton.getPlayerState()+"'s turn";

        playerTurn = new Label(playerText,style);
        playerTurn.setPosition((Gdx.graphics.getWidth()-playerTurn.getWidth())/2,Gdx.graphics.getHeight()-playerTurn.getHeight()-12);


        Label.LabelStyle style2 = new Label.LabelStyle();
        style2.font = amountToWinFont;
        amountToWinText = n+" in a row to win";

        amountToWin = new Label(amountToWinText,style2);
        amountToWin.setPosition((Gdx.graphics.getWidth()-amountToWin.getWidth())/2,Gdx.graphics.getHeight()-amountToWin.getHeight()-34);

        backButton = new TextButton("Back", skin);
        backButton.setPosition(5, Gdx.graphics.getHeight()-backButton.getHeight()-5);

        soundButton = new TextButton(soundButtontext,skin);
        soundButton.setPosition(Gdx.graphics.getWidth()-5-soundButton.getWidth(),Gdx.graphics.getHeight()-soundButton.getHeight()-5);

    }

    @Override
    public void handleInput() {
        if (backButton.isPressed()){
            singleton.stopSound(1);
            gsm.set(new MainMenuState(gsm, n));
            dispose();
        }
        if (soundButton.isPressed()){
            if (isSoundMuted){
                isSoundMuted = false;
                if (mutedBefore){
                    singleton.unMuteSound();
                    singleton.playSound(1);
                    mutedBefore=false;
                }
                else{
                    singleton.unMuteSound();
                    singleton.resumeSound(1);
                }
                soundButton.setText("Sound off");
            }
            else{
                isSoundMuted = true;
                singleton.pauseSound(1);
                soundButton.setText("Sound on");
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
    public void update ( float dt){
        ArrayList<Player> players = singleton.getPlayers();
        dispose();
        handleInput();
        for (Tile t : singleton.getTiles()) {
            t.update(dt);
        }
        if (gameLogic.hasWinner()) {
            //singleton.playSound(2);
            if (!isSoundMuted){
                singleton.stopSound(1);
                singleton.playSound(2);
            }
            gsm.set(new AfterGameMenuState(gsm, gameLogic.getWinner(),isSoundMuted,n));
            dispose();
        }
        else if (!gameLogic.hasWinner() && gameLogic.getWinner() == 'D') {
            if (!isSoundMuted){
                singleton.stopSound(1);
                singleton.playSound(3);
            }
            gsm.set(new AfterGameMenuState(gsm, gameLogic.getWinner(),isSoundMuted,n));
            dispose();
        }
        if (players.get(singleton.getPlayerState()).getPowerups() != null){
            for (Powerup pu : players.get(singleton.getPlayerState()).getPowerups()){
                pu.update(dt);
            }
        }
        if (singleton.getN() > gameLogic.getN()){
            gameLogic.expandBoard();
        }

        if (singleton.getIndexTopowerupToRemove() >= 0){
            players.get(singleton.getPlayerState()).getPowerups().remove(singleton.getIndexTopowerupToRemove());
            singleton.setIndexTopowerupToRemove(-1);
        }
        if (singleton.getPlayerState()==0){
            playerTurn.setText("Player X's turn");
        }
        else if (singleton.getPlayerState()==1){
            playerTurn.setText("Player O's turn");
        }
        amountToWinText = gameLogic.getN()+" in a row to win";
        amountToWin.setText(amountToWinText);
    }

    @Override
    public void render (SpriteBatch sb){
        // Create board tiles
        sb.begin();
        for (Sprite s : singleton.getBoardTiles()) {
            s.draw(sb);
        }
        // render tiles and powerups
        //renderTiles(singleton.getTiles(), sb);
        matrix.renderPowerupsOnBoard(singleton.getTiles(), sb);

        // Draw marks when pressed
        matrix.renderMarks(sb,gameLogic);

        // Powerups
        Player activePlayer = singleton.getPlayers().get(singleton.getPlayerState());
        if (activePlayer.havePowerupsAvailable()){
            matrix.renderPowerups(activePlayer.getPowerups(), sb);
        }

        if (currentMoveCount - gameLogic.getMoveCount() == 0){
            matrix.spawnRandomPowerup(gameLogic);
            currentMoveCount = rm.nextInt(3) + 1 + gameLogic.getMoveCount();
        }
        sb.end();

        //Render top-bar
        stage.act();
        stage.draw();
        dispose();
}

    @Override
    public void dispose () {
        matrix.dispose();
        for (TileState ts : singleton.getBoardState()) {
            ts.getTile().getTexture().dispose();
        }
    }






}
