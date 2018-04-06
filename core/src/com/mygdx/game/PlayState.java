package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by bent on 16.03.18.
 */

public class PlayState extends State {

    private GameBoard board;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        board = new GameBoard(3, 3);
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
        int sizeX = board.getSizeX();
        int sizeY = board.getSizeY();
        // The position of the initial square
        int startPositionX = 100;
        int startPositionY = 100;
        // This should be dynamic and change depending on the boardsize
        int pieceSize = 300;
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                sb.draw(board.getBoardPiece(i, j), startPositionX, startPositionY, pieceSize, pieceSize);
                startPositionY += pieceSize;
            }
            startPositionX += pieceSize;
            startPositionY = 100;
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
