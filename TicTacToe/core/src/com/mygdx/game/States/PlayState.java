package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Singleton.Singleton;
import com.mygdx.game.domain.Board;
import com.mygdx.game.domain.Player;
import com.mygdx.game.domain.TileState;
import com.mygdx.game.powerups.ExpandBoardPowerup;
import com.mygdx.game.domain.GameLogic;
import com.mygdx.game.powerups.ObstaclePowerup;
import com.mygdx.game.powerups.SwapPowerup;
import com.mygdx.game.sprites.Mark;
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
    private Label playerTurn;
    private boolean isSoundMuted;
    private String soundButtontext;

    public PlayState(GameStateManager gsm, int n, boolean isMuted) {
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

        initializeButtons();
        stage.addActor(backButton);
        stage.addActor(soundButton);

        stage.addActor(playerTurn);


        singleton.setPowerup(new ExpandBoardPowerup());
        singleton.setPowerup(new ObstaclePowerup());
        singleton.setPowerup(new SwapPowerup());

        ArrayList<Player> players = new ArrayList<Player>();

        ArrayList<Powerup> mocklist = new ArrayList<Powerup>();
        //mocklist.add(new SwapPowerup());
        //mocklist.add(new ObstaclePowerup());
        //mocklist.add(new ExpandBoardPowerup());
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

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = playerFont;
        String playerText = "Player "+singleton.getPlayerState()+"'s turn";

        playerTurn = new Label(playerText,style);
        playerTurn.setPosition((Gdx.graphics.getWidth()-playerTurn.getWidth())/2,Gdx.graphics.getHeight()-playerTurn.getHeight()-5);


        backButton = new TextButton("Back", skin);
        backButton.setPosition(5, Gdx.graphics.getHeight()-backButton.getHeight()-5);

        soundButton = new TextButton(soundButtontext,skin);
        soundButton.setPosition(Gdx.graphics.getWidth()-5-soundButton.getWidth(),Gdx.graphics.getHeight()-soundButton.getHeight()-5);

    }

    @Override
    public void handleInput() {
        if (backButton.isPressed()){
            singleton.stopSound(1);
            //System.out.println(singleton.isMuted());
            gsm.set(new MainMenuState(gsm));
            dispose();
        }
        if (soundButton.isPressed()){
            if (isSoundMuted){
                isSoundMuted = false;
                singleton.unMuteSound();
                singleton.resumeSound(1);
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
                System.out.println("Vinneren er spiller " + gameLogic.getWinner());
                gsm.set(new AfterGameMenuState(gsm, gameLogic.getWinner(),isSoundMuted));
                dispose();
            }
            else if (!gameLogic.hasWinner() && gameLogic.getWinner() == 'D') {
                System.out.println("UAVGJORT");
                if (!isSoundMuted){
                    singleton.stopSound(1);
                    singleton.playSound(3);
                }
                gsm.set(new AfterGameMenuState(gsm, gameLogic.getWinner(),isSoundMuted));
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
            renderPowerupsOnBoard(singleton.getTiles(), sb);

            // Draw marks when pressed
            renderMarks(sb);

            // Powerups
            Player activePlayer = singleton.getPlayers().get(singleton.getPlayerState());
            if (activePlayer.havePowerupsAvailable()){
                renderPowerups(activePlayer.getPowerups(), sb);
            }

            if (currentMoveCount - gameLogic.getMoveCount() == 0){
                spawnRandomPowerup();
                currentMoveCount = rm.nextInt(3) + 1 + gameLogic.getMoveCount();
            }
            sb.end();

            //Render top-bar
            stage.act();
            stage.draw();
    }

    @Override
    public void dispose () {
        matrix.dispose();
        for (TileState ts : singleton.getBoardState()) {
            ts.getTile().getTexture().dispose();
        }
    }

    public void renderPowerupsOnBoard(ArrayList<Tile> tiles, SpriteBatch sb){
        for (Tile t : tiles){
            if (t.getPowerup() != null) {
                Sprite pu = new Sprite(t.getPowerup().getTexture());
                Vector3 tilePosition = t.getPosition();
                pu.setSize(50, 50);
                float xPosition = (tilePosition.x + (t.getWidth() / 2) - 25);
                float yPosition = (tilePosition.y + (t.getHeight() / 2) - 25);
                pu.setPosition(xPosition, yPosition);
                pu.draw(sb);
            }
        }
    }


    public void renderPowerups(ArrayList<Powerup> powerups, SpriteBatch sb) {
        float factor = powerups.size() > 1 ? MyGdxGame.WIDTH / powerups.size() : MyGdxGame.WIDTH / 2;
        float blankspace = powerups.size() > 1 ? factor / powerups.size() : 0;
        float renderIterator = powerups.size() > 1 ? 0 : MyGdxGame.WIDTH / 2;
        for (Powerup pu : powerups){
            Sprite s = new Sprite(pu.getTexture());
            if (pu.equals(singleton.getPowerupSelected())){
                s.setAlpha(0.5f);
            }
            s.setSize(50, 50);
            s.setPosition(renderIterator + blankspace - 25, /*Gdx.graphics.getHeight() - MyGdxGame.BAR + 10*/10); // Fix this to appear in own menu
            pu.setPosition(new Vector3(renderIterator + blankspace - 25, /*Gdx.graphics.getHeight()  - MyGdxGame.BAR + 10*/ 10, 0f));
            pu.setHeight(50);
            pu.setWidth(50);
            s.draw(sb);
            renderIterator += factor;
        }
    }

    public void renderMarks(SpriteBatch sb) {
        gameLogic.clearBoard();
        gameLogic.setZeroMoveCount();
        for (TileState ts : singleton.getBoardState()) {
            Tile tile = ts.getTile();
            Mark m = new Mark(tile, ts.getState());
            if (ts.getState() == 1) {
                gameLogic.Move(tile.getX(), tile.getY(), 'O');
            } else if (ts.getState() == 0) {
                gameLogic.Move(tile.getX(), tile.getY(), 'X');
            }
            else if (ts.getState()==-1){
                gameLogic.Move(tile.getX(), tile.getY(),'T');
            }
            Sprite s = new Sprite(m.getTexture());
            s.setPosition(tile.getPosition().x, tile.getPosition().y);
            s.setSize(tile.getWidth(), tile.getHeight());
            s.draw(sb);
        }
        if (gameLogic.getMoveCount()>updateMoveCount){
            //System.out.println("movecount "+gameLogic.getMoveCount());
            //System.out.println("To draw: "+singleton.getN()*singleton.getN());
            updateMoveCount=gameLogic.getMoveCount();
            System.out.println(gameLogic.printBoard());
        }
    }

    public void spawnRandomPowerup(){
        Tile t = singleton.getTiles().get(rm.nextInt((singleton.getBoard().getColumns() * singleton.getBoard().getRows())));
        Powerup pu = gameLogic.getMoveCount() > 2 ? singleton.getPowerups().get(rm.nextInt(singleton.getPowerups().size())) : singleton.getPowerups().get(rm.nextInt(singleton.getPowerups().size() - 1));
        boolean canPlacePowerup = true;
        for (TileState tileState : singleton.getBoardState()) {
            if (tileState.getTile().getX() == t.getX() && tileState.getTile().getY() == t.getY()){
                canPlacePowerup = false;
            }
        }
        if (canPlacePowerup){
            pu.setPosition(new Vector3(-10f,-10f,0f)); // powerup needs position to work. Random position for init
            t.setPowerup(pu);
        }
    }
}
