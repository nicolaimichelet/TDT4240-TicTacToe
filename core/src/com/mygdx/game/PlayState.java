package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by bent on 16.03.18.
 */

public class PlayState extends State {

    private GameBoard board;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        board = new GameBoard(100, 100);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(board.getBoardPiece(), board.getSizeX(), board.getSizeY());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
