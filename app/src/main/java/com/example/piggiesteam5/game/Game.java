/*
 * Game.java
 *
 * Runs the backend for the game
 * authors: Philippe Nadon, Yusheng Liu
 * date: Nov 9, 2019
 * last modified date: Dec, 4, 2019
 */

package com.example.piggiesteam5.game;


import android.util.Log;

import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameBoard;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.GameState;
import com.example.piggiesteam5.datastructures.Options;

public class Game {
    private Options options;
    protected GameSetup setup;
    protected GameBoard currentBoard;
    private GameBoard prevBoard;
    protected GameState currentState;
    private GameState prevState;
    private AI ai;
    private int[] vissableDot;
    private BoardPosition vissableLine;
    private boolean playerPlayAgain;

    // similar to cardinal directions
    protected enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public Game(Options options, GameSetup setup) {
        this.options = options;
        this.setup = setup;
        this.vissableLine = new BoardPosition(0, 0, true, setup.getBoardSize(), setup.getBoardSize());
        this.currentBoard = new GameBoard(setup.getBoardSize(), setup.getBoardSize());
        this.currentState = new GameState((setup.getBoardSize() - 1) * setup.getBoardSize() * 2, options.getNumUndos(), 0);
        this.ai = new AI(currentBoard);
    }

    public Game(Options options, GameSetup setup, GameBoard currentBoard, GameBoard prevBoard, GameState currentState, GameState prevState) {
        this.options = options;
        this.setup = setup;
        this.currentBoard = currentBoard;
        this.prevBoard = prevBoard;
        this.currentState = currentState;
        this.prevState = prevState;
        this.ai = new AI(currentBoard);
    }

    public void setBoard(GameBoard gameBoard){
        currentBoard = gameBoard;
        currentState.setNumLinesPlaced(gameBoard.totalNumOfLines());
    }


    private void playerTurn(int player, int[] firstDot, int[] secondDot) {
        backupPrevBoardAndState();
        BoardPosition position = new BoardPosition(
                determineYindex(firstDot, secondDot),
                determineXindex(firstDot, secondDot),
                determineIsHorizontal(firstDot, secondDot),
                currentBoard.getHeight(),
                currentBoard.getWidth()
        );
        System.out.println(position.toString());
        currentBoard.setLine(position);
        vissableLine.setPosition(position);
        currentState.setNumLinesPlaced(currentState.getNumLinesPlaced() + 1);
        boolean closedBox = updateScore(
                position
                , player
        );
        if(!closedBox) {
            playerPlayAgain = false;
            currentState.toggleCurrentPlayersTurn();
        } else {
            System.out.println("END OF PLAYER TURN: PLAY AGAIN!!");
            playerPlayAgain = true;
        }
    }

    public void AIturn() {
        boolean closedBox = true;
        BoardPosition AImove = new BoardPosition(-1,-1,true,currentBoard.getHeight(),currentBoard.getWidth());
        while(closedBox) {
            if(setup.getDiffculty() == 0){
                AImove.setPosition(ai.placeAdjacentLine(currentBoard));
            }else if(setup.getDiffculty() == 1){
                AImove.setPosition(ai.placeBestLine(currentBoard));
            }else{
                AImove.setPosition(ai.superAI(currentBoard,currentState));
            }
            currentState.setNumLinesPlaced(currentState.getNumLinesPlaced() + 1);
            vissableLine.setPosition(AImove.yPos(), AImove.xPos(), AImove.isHorizontal());
            closedBox = updateScore(
                    new BoardPosition(
                            AImove.yPos(),
                            AImove.xPos(),
                            AImove.isHorizontal(),
                            currentBoard.getHeight(),
                            currentBoard.getWidth()
                    ),
                    currentState.getPlayers());
            if(currentState.getNumLinesPlaced() == currentState.getMaxNumLinesPlaced()) {
                return;
            }
        }
        currentState.toggleCurrentPlayersTurn();
    }

    /**
     * Decrements the number of remaining undos, and reverts the current board to the previous board
     * @return number of undo
     */
    public boolean triggerUndo() {
        int currNumUndos = currentState.getNumUndos();
        if(currNumUndos > 0) {
            if(prevBoard != null && prevState != null) {
                currentBoard = new GameBoard(prevBoard);
                currentState = new GameState(prevState);
                prevBoard = null;
                prevState = null;
                currentState.setNumUndos(currNumUndos - 1);
                if(!setup.getIsTwoPlayer()) {
                    currentState.setPlayers(0);
                }
                System.out.println("\n\nRESTORE:");
                System.out.println(currentBoard.toString());
                System.out.println(currentState.toString());


                return true;
            }
        }
        return false;
    }

    /**
     * the method directly linked to every button on the board
     * @param dot index of the button(dot)
     * @return value for the visualization of the dots
     */
    public int[] clicks(int[] dot) {
        int[] returnValue = {-1, -1};
        if (vissableDot == null) {
            firstClick(dot);
            return returnValue;
        }//return -1 if it is a first click to light up the button
        if (!secondClick(currentState.getPlayers(), dot)) {
            playerPlayAgain = true;
        }//if not valid move, go again
        returnValue = vissableDot;
        vissableDot = null;
        return returnValue;//return the index of the first click to turn the light up button back
    }

    /**
     * check game is finished or not by totsl num of lines
     * @return true if game is done
     */
    public boolean gameIsDone() {
        return currentState.getNumLinesPlaced() == currentState.getMaxNumLinesPlaced();
    }

    /**
     * getters for visible line that need be lighted up
     * @return the index of the visible line
     */
    public BoardPosition getVisibleLine() {
        return vissableLine;
    }

    /**
     * getter for scores
     * @return int[] that has 2 int as scores in it
     */
    public int[] getScore() {
        return currentState.getScores();
    }

    /**
     * getter for chances left for undo
     * @return int num of undo
     */
    public int getUndos() {
        return currentState.getNumUndos();
    }

    /**
     * getter for game turn
     * @return int, 0 for 1st player, 1 for 2nd or AI
     */
    public int getTurn() {
        return currentState.getPlayers();
    }

    /**
     * getter for current game board
     * @return current game board
     */
    public GameBoard getBoard(){
        return currentBoard;
    }

    public GameState getState(){
        return currentState;
    }

    /**
     * determine if player should go again
     * @return boolean , true for go again
     */
    public boolean playerPlayAgain() {
        System.out.println("PLAYERPLAYAGAIN: " + playerPlayAgain);
        return playerPlayAgain;
    }

    /**
     * store the index of the button
     * @param dot the index of button
     */
    private void firstClick(int[] dot) {
        vissableDot = dot;
    }

    /**
     * check if it's a valid line and run the player turn
     * @param player player turn
     * @param dot dot index
     * @return true if it is a valid move
     */
    private boolean secondClick(int player, int[] dot) {
        if (vissableDot == dot || !isValidInput(vissableDot, dot)) {
            return false;
        }
        playerTurn(player, vissableDot, dot);
        return true;
    }

    /**
     * check if it's a valid input
     * @param firstDot index of first dot
     * @param secondDot index of second dot
     * @return if it is a valid move
     */
    private boolean isValidInput(int[] firstDot, int[] secondDot) {
        for (int i = 0; i < 2; i++) {
            if (firstDot[i] == secondDot[i]) {
                return (
                        Math.abs(firstDot[(i + 1) % 2] - secondDot[(i + 1) % 2]) == 1
                                && !currentBoard.getLine(
                                determineYindex(firstDot, secondDot),
                                determineXindex(firstDot, secondDot),
                                determineIsHorizontal(firstDot, secondDot)
                        )
                );
            }
        }
        return false;
    }

    /**
     * calculate the Y index of the line by dots
     * @param firstDot index of first dot
     * @param secondDot index of second dot
     * @return y index of the line
     */
    private int determineYindex(int[] firstDot, int[] secondDot) {
        return Math.min(firstDot[0], secondDot[0]);
    }

    /**
     * calculate the X index of the line by dots
     * @param firstDot index of first dot
     * @param secondDot index of second dot
     * @return X index of the line
     */
    private int determineXindex(int[] firstDot, int[] secondDot) {
        return Math.min(firstDot[1], secondDot[1]);
    }

    /**
     * determine if the line is a horizontal line
     * @param firstDot index of first dot
     * @param secondDot index of second dot
     * @return is horizontal
     */
    private boolean determineIsHorizontal(int[] firstDot, int[] secondDot) {
        return (firstDot[1] != secondDot[1]);
    }

    /**
     * calculate the score by the last line maded
     * @param position last line maded
     * @param player player turn
     * @return true if play score
     */
    private boolean updateScore(BoardPosition position, int player) {
        int score = 0;
        boolean closedBox = false;
        if (position.isHorizontal()) {
            if (position.yPos() < setup.getBoardSize() - 1 && checkIfBox(position, Direction.DOWN)) {
                score++;
                currentBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
            if (position.yPos() > 0 && checkIfBox(position, Direction.UP)) {
                score++;
                currentBoard.setBoxBoard(position.yPos() - 1, position.xPos(), player);
                closedBox = true;
            }
        } else {
            if (position.xPos() > 0 && checkIfBox(position, Direction.LEFT)) {
                score++;
                currentBoard.setBoxBoard(position.yPos(), position.xPos() - 1, player);
                closedBox = true;
            }
            if (position.xPos() < setup.getBoardSize() - 1 && checkIfBox(position, Direction.RIGHT)) {
                score++;
                currentBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
        }
        currentState.setScore(player, currentState.getScore(player) + (score * 100 / currentBoard.getMaxBoxes()));
        return closedBox;
    }

    /**
     * check if a box is closed
     * author: Philippe Nadon
     * @param position the position of the line
     * @param direction direction of checking the box
     * @return true if there is a box
     */
    protected boolean checkIfBox(BoardPosition position, Direction direction) {
        if (position.isHorizontal() &&
                (direction == Direction.LEFT || direction == Direction.RIGHT)) {
            return false;
        } else if (!position.isHorizontal() &&
                (direction == Direction.DOWN || direction == Direction.UP)) {
            return false;
        }
        switch (direction) {
            case UP:
                return (currentBoard.getLine(
                        position.yPos() - 1,
                        position.xPos(),
                        true
                )
                        && currentBoard.getLine(
                        position.yPos() - 1,
                        position.xPos(),
                        false
                )
                        && currentBoard.getLine(
                        position.yPos() - 1,
                        position.xPos() + 1,
                        false
                ));
            case RIGHT:
                return (currentBoard.getLine(
                        position.yPos(),
                        position.xPos(),
                        true
                )
                        && currentBoard.getLine(
                        position.yPos(),
                        position.xPos() + 1,
                        false
                )
                        && currentBoard.getLine(
                        position.yPos() + 1,
                        position.xPos(),
                        true
                ));
            case DOWN:
                return (currentBoard.getLine(
                        position.yPos(),
                        position.xPos(),
                        false
                )
                        && currentBoard.getLine(
                        position.yPos() + 1,
                        position.xPos(),
                        true
                )
                        && currentBoard.getLine(
                        position.yPos(),
                        position.xPos() + 1,
                        false
                ));
            default: // LEFT
                return (currentBoard.getLine(
                        position.yPos(),
                        position.xPos() - 1,
                        true
                )
                        && currentBoard.getLine(
                        position.yPos(),
                        position.xPos() - 1,
                        false
                )
                        && currentBoard.getLine(
                        position.yPos() + 1,
                        position.xPos() - 1,
                        true
                ));
        }
    }

    private boolean backupPrevBoardAndState() {
        prevBoard = new GameBoard(currentBoard);
        prevState = new GameState(currentState);
        System.out.println("\n\nBACKUP:");
        System.out.println(prevBoard.toString());
        System.out.println(prevState.toString());
        return true;
    }

}
