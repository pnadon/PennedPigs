/*
 * PigSweeperGame.java
 *
 * Objects which represents a variation of the standard game
 * A new feature is implemented, which places mines on the grid
 * This feature is similar to Minesweeper
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.frenzy;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameBoard;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.GameState;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.game.Game;

/**
 * Object which combines standard game play with a minesweeper variant
 */
public class PigSweeperGame extends Game {
    private MineBoard mineBoard;
    private NumberBoard numberBoard;

    public PigSweeperGame(Options options, GameSetup setup) {
        super(options, setup);
        int numMines = setup.getBoardSize() * setup.getBoardSize() / Constants.Board.BOARD_TO_MINES_RATIO;
        mineBoard = new MineBoard(setup.getBoardSize(), setup.getBoardSize(), numMines);
        numberBoard = new NumberBoard(mineBoard, setup.getBoardSize(), setup.getBoardSize());
    }

    public PigSweeperGame(Options options, GameSetup setup, GameBoard currentBoard, GameBoard prevBoard, GameState currentState, GameState prevState, MineBoard mineBoard, NumberBoard numberBoard) {
        super(options, setup, currentBoard, prevBoard, currentState, prevState);
        this.mineBoard = mineBoard;
        this.numberBoard = numberBoard;
    }

    /**
     * @return the mineBoard, which represents where the mines are placed
     */
    public MineBoard getMineBoard() { return mineBoard; }

    public boolean ifMine(int y, int x){
        return mineBoard.getMine(y,x);
    }

    public int numOfBox(int y, int x){
        return numberBoard.getNumberOfBox(y,x);
    }

    /**
     * updates the score based on whether a pen was enclosed, or if a mine was triggered
     * @param position the position where the last line was placed
     * @param player the player who placed it
     */
    private boolean updateScore(BoardPosition position, int player) {
        int score = 0;
        boolean closedBox = false;
        if (position.isHorizontal()) {
            if (position.yPos() < setup.getBoardSize() - 1 && checkIfBox(position, Direction.DOWN)) {
                if(mineBoard.getMine(position.yPos(), position.xPos())) {
                    score--;
                } else {
                    score++;
                }
                currentBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
            if (position.yPos() > 0 && checkIfBox(position, Direction.UP)) {
                if(mineBoard.getMine(position.yPos() - 1, position.xPos())) {
                    score--;
                } else {
                    score++;
                }
                currentBoard.setBoxBoard(position.yPos() - 1, position.xPos(), player);
                closedBox = true;
            }
        } else {
            if (position.xPos() > 0 && checkIfBox(position, Direction.LEFT)) {
                if(mineBoard.getMine(position.yPos(), position.xPos() - 1)) {
                    score--;
                } else {
                    score++;
                }
                currentBoard.setBoxBoard(position.yPos(), position.xPos() - 1, player);
                closedBox = true;
            }
            if (position.xPos() < setup.getBoardSize() - 1 && checkIfBox(position, Direction.RIGHT)) {
                if(mineBoard.getMine(position.yPos(), position.xPos())) {
                    score--;
                } else {
                    score++;
                }
                currentBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
        }
        currentState.setScore(player, currentState.getScore(player) + score);
        return closedBox;
    }
}
