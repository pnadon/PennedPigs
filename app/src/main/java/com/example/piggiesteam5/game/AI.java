/*
 * AI.java
 *
 * Library which simulates a player's choices during the game
 * authors: Philippe Nadon, Yusheng Liu
 * date: Nov 9, 2019
 * last modified date: Dec, 4, 2019
 */

package com.example.piggiesteam5.game;


import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameBoard;
import com.example.piggiesteam5.datastructures.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Library which simulates a player's choices during the game
 */
public class AI {

    private Random r = new Random();
    private ArrayList<BoardPosition> bestMoves = new ArrayList<>();
    private ArrayList<BoardPosition> goodMoves = new ArrayList<>();
    private ArrayList<BoardPosition> normalMoves = new ArrayList<>();
    private ArrayList<BoardPosition> badMoves = new ArrayList<>();
    private ArrayList<BoardPosition> worstMoves = new ArrayList<>();
    private ArrayList<BoardPosition> adjacentLines = new ArrayList<>();
    private int height;
    private int width;
    private GameBoard predictBoard;
    private GameState predictState;

    public AI(GameBoard gameBoard){
        this.height = gameBoard.getHeight();
        this.width = gameBoard.getWidth();
    }


    /**
     * author: Philippe Nadon
     * @param gameBoard The GameBoard to be modified, a random line is placed in a valid position on it
     */
    public BoardPosition placeRandomLine(GameBoard gameBoard) {
        BoardPosition pos = new BoardPosition(gameBoard.getHeight(), gameBoard.getWidth());
        while (gameBoard.getLine(pos.yPos(), pos.xPos(), pos.isHorizontal())) {
            pos.setRandomPosition();
        }
        gameBoard.setLine(pos);
        return pos;
    }

    /**
     * author : Yusheng Liu
     * @param gameBoard The GameBoard that contain data required by this method
     * @return adjacentLine that will be placed on the board as an BoardPosition object
     * This method place and return the adjacent line for the game
     */
    BoardPosition placeAdjacentLine(GameBoard gameBoard){
        updateAdjacentLines(gameBoard);
        BoardPosition adjacentLine = new BoardPosition(-10,-10,true,gameBoard.getHeight(),gameBoard.getWidth());
        while(adjacentLine.yPos() == -10 || gameBoard.getLine(adjacentLine.yPos(),adjacentLine.xPos(),adjacentLine.isHorizontal())) {
            randomAdjacentLine(adjacentLine);
        }//while there line is not a legit move, random bug(index out of bound) appear without this loop
        gameBoard.setLine(adjacentLine);
        return adjacentLine;
    }

    /**
     * author: Yusheng Liu
     * @param gameBoard
     * Clear and update the Array list that contains all unmade line that has an adjacentLine
     */
    private void updateAdjacentLines(GameBoard gameBoard){
        adjacentLines.clear();
        for(int y = 0; y < gameBoard.getHeight() - 1; y++) {
            for (int x = 0; x < gameBoard.getWidth(); x++) {
                if (!gameBoard.getLine(y, x, false)&&thisVerticalLineHasAdjacentLine(gameBoard,y,x)) {
                    adjacentLines.add(new BoardPosition(y,x,false,height,width));
                }//if it's an adjacent line
            }
        }//all vertical line
        for(int y = 0; y < gameBoard.getHeight(); y++){
            for(int x = 0; x<gameBoard.getWidth() - 1; x++){
                if(!gameBoard.getLine(y,x,true)&&thisHorizontalLineHasAdjacentLine(gameBoard,y,x)) {
                    adjacentLines.add(new BoardPosition(y,x,true,height,width));
                }//if it's an adjacent line
            }
        }//all horizontal line
    }

    /**
     * author: Yusheng Liu
     * @param adjacentLine the adjacent line that this method will change
     * @return the adjacentLine
     * this method find a random adjacent line from the array list that contains all of those line
     */
    private BoardPosition randomAdjacentLine(BoardPosition adjacentLine){
        if(!adjacentLines.isEmpty()){
            adjacentLine.setPosition(adjacentLines.get(r.nextInt(adjacentLines.size())));//randomly picking one line
        }//if the list is empty
        else{
            adjacentLine.setRandomPosition();
        }//use random line
        return adjacentLine;
    }

    /**
     * author: Yusheng Liu
     * @param gameBoard the gameBoard
     * @param y y index of the line that was checked
     * @param x x inded of the line that was checked
     * @return true if this line has adjacent line, false otherwise
     */
    private boolean thisHorizontalLineHasAdjacentLine(GameBoard gameBoard, int y, int x){
        if(y > 0){
            if (gameBoard.getLine(y - 1, x, false)
                    ||gameBoard.getLine(y - 1, x + 1, false)) {
                return true;
            }
        }
        if(y < height - 1) {
            return gameBoard.getLine(y, x, false)
                    ||gameBoard.getLine(y, x + 1, false);
        }
        return false;
    }

    /**
     * author: Yusheng Liu
     * @param gameBoard the gameBoard
     * @param y y index of the line that was checked
     * @param x x inded of the line that was checked
     * @return true if this line has adjacent line, false otherwise
     */
    private boolean thisVerticalLineHasAdjacentLine(GameBoard gameBoard, int y, int x){
        if(x > 0){
            if (gameBoard.getLine(y + 1, x - 1, true)
                    ||gameBoard.getLine(y, x - 1, true)) {
                return true;
            }
        }
        if(x < width - 1) {
            return gameBoard.getLine(y + 1, x, true)
                    ||gameBoard.getLine(y, x, true);
        }
        return false;
    }

    /**
     * author: Yusheng Liu
     * @param gameBoard the GameBoard object
     * @return the BoardPosition of the best line from this level of AI
     * This method check the board, record and sort every line for their value
     * in the rest of the game and determine the best line to made
     */
    BoardPosition placeBestLine(GameBoard gameBoard){
        BoardPosition bestLine =
                new BoardPosition(-10,-10,true,gameBoard.getHeight(),
                        gameBoard.getWidth());//-10 for debugging
        updateLineValue(gameBoard);
        findBestLine(bestLine);
        gameBoard.setLine(bestLine);
        return bestLine;
    }//bestLine

    /**
     * author: Yusheng Liu
     * @param gameBoard the GameBoard object
     * @return the BoardPosition of the best line from this level of AI
     * This method is a higher level of placeBestLine but using different evaluate logic that try
     * to close any box immediately while not leaving a bigger chain to player.
     */
    private BoardPosition placeBestLine2(GameBoard gameBoard){
        BoardPosition bestLine2 =
                new BoardPosition(-10,-10,true,gameBoard.getHeight(),
                        gameBoard.getWidth());//-10 for debugging
        updateLineValue2(gameBoard);
        findBestLine(bestLine2);
        gameBoard.setLine(bestLine2);
        System.out.println("AIIIIIII");
        System.out.println(bestLine2);
        return bestLine2;
    }//bestLine

    /**
     * author: Yusheng Liu
     * @param gameBoard the current gameboard
     * This method reset and update line value lists
     */
    private void updateLineValue2(GameBoard gameBoard){
        resetValues();
        checkAllHorizontalLines2(gameBoard);
        checkAllVerticalLines2(gameBoard);
    }

    /**
     * author: Yusheng Liu
     * @param thisLine the line that need be sorted and saved
     * @param value value of the line
     * This method store all lines to their list base on its value
     */
    private void sortLine2(BoardPosition thisLine, int value) {
        if (value > 99) {
            bestMoves.add(thisLine);
        } else if (value > 49) {
            goodMoves.add(thisLine);
        } else if (value > -1) {
            normalMoves.add(thisLine);
        } else if (value > -99) {
            badMoves.add(thisLine);
        } else {
            worstMoves.add(thisLine);
        }
    }

    /**
     * author:Yusheng Liu
     * @param gameBoard
     * this method check all vertical lines in the gameBoard
     */
    private void checkAllVerticalLines2(GameBoard gameBoard) {
        for(int y = 0; y < gameBoard.getHeight() - 1; y++){
            for(int x = 0; x<gameBoard.getWidth(); x++) {
                if (!gameBoard.getLine(y, x, false))
                    sortLine2(new BoardPosition(y,x,false,height,width), getValue2(checkAllLinesAtLeft(gameBoard, y, x), checkAllLinesAtRight(gameBoard, y, x)));
            }//if this line has not be made, determine its value and sort it
        }
    }

    /**
     * author:Yusheng Liu
     * @param gameBoard
     * this method check all horizontal lines in the gameBoard
     */
    private void checkAllHorizontalLines2(GameBoard gameBoard) {
        for(int y = 0; y < gameBoard.getHeight(); y++){
            for(int x = 0; x<gameBoard.getWidth() - 1; x++){
                if(!gameBoard.getLine(y,x,true)) {
                    sortLine2(new BoardPosition(y,x,true,height,width), getValue2(checkAllLinesAbove(gameBoard, y, x), checkAllLinesBelow(gameBoard, y, x)));
                }//if this line has not be made, determine its value and sort it
            }
        }
    }

    /**
     * author: Yusheng Liu
     * @param lineAboveOrLeft total number of lines that above or left
     * @param lineBelowOrRight total number of lines that is below this line or right
     * @return the total value of this line
     * contains core algorithm of this AI, can be customized for higher level of intelligence
     * By evaluating lines through different logic, this AI close every box available immediately
     */
    private int getValue2(int lineAboveOrLeft, int lineBelowOrRight) {
        int value = 0;
        if (lineAboveOrLeft == 3) {
            value += 100;
        }
        if (lineAboveOrLeft == 2) {
            value -= 50;
        }
        if (lineAboveOrLeft == 1) {
            value++;
        }
        if (lineBelowOrRight == 3) {
            value += 100;
        }
        if (lineBelowOrRight == 2) {
            value -= 50;
        }
        if (lineBelowOrRight == 1) {
            value++;
        }
        return value;
    }


    /**
     * author: Yusheng Liu
     * @param bestLine the best line that this ai want to find
     * this method look through every level of lines and find the list with highest value and randomly
     *                 picking one.
     */
    private void findBestLine(BoardPosition bestLine) {
        if (!bestMoves.isEmpty()) {
            bestLine.setPosition(bestMoves.get(r.nextInt(bestMoves.size())));//if can close two box at a time
        } else if (!goodMoves.isEmpty()) {
            bestLine.setPosition(goodMoves.get(r.nextInt(goodMoves.size())));//if can close one box or create two trap
        } else if (!normalMoves.isEmpty()) {
            bestLine.setPosition(normalMoves.get(r.nextInt(normalMoves.size())));//if balance
        } else if (!badMoves.isEmpty()) {
            bestLine.setPosition(badMoves.get(r.nextInt(badMoves.size())));//has to step into a trap
        } else if (!worstMoves.isEmpty()) {
            bestLine.setPosition(worstMoves.get(r.nextInt(worstMoves.size())));//has to step into two trap
        }
    }

    /**
     * author: Yusheng Liu
     * @param gameBoard the current gameboard
     * This method reset and update line value lists
     */
    private void updateLineValue(GameBoard gameBoard){
        resetValues();
        checkAllHorizontalLines(gameBoard);
        checkAllVerticalLines(gameBoard);
    }

    /**
     * author: Yusheng Liu
     * This method reset everything to null
     */
    private void resetValues() {
        bestMoves.clear();
        goodMoves.clear();
        normalMoves.clear();
        badMoves.clear();
        worstMoves.clear();
    }

    /**
     * author:Yusheng Liu
     * @param gameBoard
     * this method check all vertical lines in the gameBoard
     */
    private void checkAllVerticalLines(GameBoard gameBoard) {
        for(int y = 0; y < gameBoard.getHeight() - 1; y++){
            for(int x = 0; x<gameBoard.getWidth(); x++) {
                if (!gameBoard.getLine(y, x, false))
                    sortLine(new BoardPosition(y,x,false,height,width), getValue(checkAllLinesAtLeft(gameBoard, y, x), checkAllLinesAtRight(gameBoard, y, x)));
            }//if this line has not be made, determine its value and sort it
        }
    }

    /**
     * author:Yusheng Liu
     * @param gameBoard
     * this method check all horizontal lines in the gameBoard
     */
    private void checkAllHorizontalLines(GameBoard gameBoard) {
        for(int y = 0; y < gameBoard.getHeight(); y++){
            for(int x = 0; x<gameBoard.getWidth() - 1; x++){
                if(!gameBoard.getLine(y,x,true)) {
                    sortLine(new BoardPosition(y,x,true,height,width), getValue(checkAllLinesAbove(gameBoard, y, x), checkAllLinesBelow(gameBoard, y, x)));
                }//if this line has not be made, determine its value and sort it
            }
        }
    }

    /**
     * author: Yusheng Liu
     * @param thisLine the line that need be sorted and saved
     * @param value value of the line
     * This method store all lines to their list base on its value
     */
    private void sortLine(BoardPosition thisLine, int value) {
        if (value > 3) {
            bestMoves.add(thisLine);
        } else if (value > 1) {
            goodMoves.add(thisLine);
        } else if (value > -1) {
            normalMoves.add(thisLine);
        } else if (value > -2) {
            badMoves.add(thisLine);
        } else {
            worstMoves.add(thisLine);
        }
    }

    /**
     * author: Yusheng Liu
     * @param lineAboveOrLeft total number of lines that above or left
     * @param lineBelowOrRight total number of lines that is below this line or right
     * @return the total value of this line
     * contains core algorithm of this AI, can be customized for higher level of intelligence
     */
    private int getValue(int lineAboveOrLeft, int lineBelowOrRight) {
        int value = 0;
        if (lineAboveOrLeft == 3) {
            value += 2;
        }
        if (lineAboveOrLeft == 2) {
            value--;
        }
        if (lineAboveOrLeft == 1) {
            value++;
        }
        if (lineBelowOrRight == 3) {
            value += 2;
        }
        if (lineBelowOrRight == 2) {
            value--;
        }
        if (lineBelowOrRight == 1) {
            value++;
        }
        return value;
    }

    /**
     * author: yusheng Liu
     * @param gameBoard the game board
     * @param y y pos of the line that is checked
     * @param x x pos of the line that is checked
     * @return total number of adjacent line above this one
     */
    private int checkAllLinesAbove(GameBoard gameBoard, int y, int x) {
        int lineAbove = 0;
        if(y > 0){
            if (gameBoard.getLine(y - 1, x, true)) {
                lineAbove++;
            }
            if (gameBoard.getLine(y - 1, x, false)) {
                lineAbove++;
            }
            if (gameBoard.getLine(y - 1, x + 1, false)) {
                lineAbove++;
            }
        }
        return lineAbove;
    }

    /**
     * author: yusheng Liu
     * @param gameBoard the game board
     * @param y y pos of the line that is checked
     * @param x x pos of the line that is checked
     * @return total number of adjacent line below this one
     */
    private int checkAllLinesBelow(GameBoard gameBoard, int y, int x) {
        int lineBelow = 0;
        if(y < height - 1) {
            if (gameBoard.getLine(y + 1, x, true)) {
                lineBelow++;
            }
            if (gameBoard.getLine(y, x, false)) {
                lineBelow++;
            }
            if (gameBoard.getLine(y, x + 1, false)) {
                lineBelow++;
            }
        }
        return lineBelow;
    }

    /**
     * author: yusheng Liu
     * @param gameBoard the game board
     * @param y y pos of the line that is checked
     * @param x x pos of the line that is checked
     * @return total number of adjacent lines at the left of this one
     */
    private int checkAllLinesAtLeft(GameBoard gameBoard, int y, int x) {
        int lineAtLeft = 0;
        if(x > 0){
            if (gameBoard.getLine(y, x-1, false)) {
                lineAtLeft++;
            }
            if (gameBoard.getLine(y, x - 1, true)) {
                lineAtLeft++;
            }
            if (gameBoard.getLine(y + 1, x - 1, true)) {
                lineAtLeft++;
            }
        }
        return lineAtLeft;
    }

    /**
     * author: yusheng Liu
     * @param gameBoard the game board
     * @param y y pos of the line that is checked
     * @param x x pos of the line that is checked
     * @return total number of adjacent lines at the right of this one
     */
    private int checkAllLinesAtRight(GameBoard gameBoard, int y, int x) {
        int lineAtRight = 0;
        if(x < width - 1) {
            if (gameBoard.getLine(y, x + 1, false)) {
                lineAtRight++;
            }
            if (gameBoard.getLine(y, x, true)) {
                lineAtRight++;
            }
            if (gameBoard.getLine(y + 1, x, true)) {
                lineAtRight++;
            }
        }
        return lineAtRight;
    }

    /**
     * Author: Yusheng Liu
     * @param gameBoard currentBoard
     * @param gameState currentState
     * @return The best move for this level of AI
     * This AI basically simulate AI and Human turn for the next 10 rounds and find the one first
     * move that will lead to highest score
     */
    BoardPosition superAI(GameBoard gameBoard, GameState gameState){
        int bestPossibleScore = 0;
        BoardPosition BestMove = new BoardPosition(-99,-1,true,height,width);
        for(int i = 0; i < 10; i++){
            predictBoard = new GameBoard(gameBoard);
            predictState = new GameState(gameState);
            BoardPosition firstmove = new BoardPosition(-50,-1,true,height,width);
            singleTurnSimulation(firstmove);
            if(predictState.getScore(1) > bestPossibleScore){
                BestMove = firstmove;
                bestPossibleScore = predictState.getScore(1);
            }
        }
        if(bestPossibleScore == 0){
            return placeBestLine2(gameBoard);
        }
        gameBoard.setLine(BestMove);
        return BestMove;
    }

    /**
     * author: Yusheng Liu
     * @param firstmove the variable to save the best first move
     */
    private void singleTurnSimulation(BoardPosition firstmove) {
        eachSim: for(int j = 0; j < 10; j++) {
            boolean closedBox = true;
            BoardPosition AImove = new BoardPosition(-1,-1,true,height,width);
            while(closedBox) {
                AImove.setPosition(placeBestLine2(predictBoard));
                if(firstmove.yPos() == -50){
                    firstmove.setPosition(AImove);
                }
                predictState.setNumLinesPlaced(predictState.getNumLinesPlaced() + 1);
                closedBox = updateScore(
                        new BoardPosition(
                                AImove.yPos(),
                                AImove.xPos(),
                                AImove.isHorizontal(),
                                predictBoard.getHeight(),
                                predictBoard.getWidth()
                        ),
                        predictState.getPlayers(),
                        predictBoard,
                        predictState);
                if(predictState.getNumLinesPlaced() == predictState.getMaxNumLinesPlaced()) {
                    break eachSim;
                }
            }
            predictState.toggleCurrentPlayersTurn();
        }
    }

    /**
     * calculate the score by the last line maded
     * @param position last line maded
     * @param player player turn
     * @return true if play score
     */
    private boolean updateScore(BoardPosition position, int player, GameBoard gameBoard, GameState gameState) {
        int score = 0;
        boolean closedBox = false;
        if (position.isHorizontal()) {
            if (position.yPos() < width - 1 && checkIfBox(position, Direction.DOWN, gameBoard)) {
                score++;
                gameBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
            if (position.yPos() > 0 && checkIfBox(position, Direction.UP, gameBoard)) {
                score++;
                gameBoard.setBoxBoard(position.yPos() - 1, position.xPos(), player);
                closedBox = true;
            }
        } else {
            if (position.xPos() > 0 && checkIfBox(position, Direction.LEFT, gameBoard)) {
                score++;
                gameBoard.setBoxBoard(position.yPos(), position.xPos() - 1, player);
                closedBox = true;
            }
            if (position.xPos() < height - 1 && checkIfBox(position, Direction.RIGHT, gameBoard)) {
                score++;
                gameBoard.setBoxBoard(position.yPos(), position.xPos(), player);
                closedBox = true;
            }
        }
        gameState.setScore(player, gameState.getScore(player) + score);
        return closedBox;
    }

    protected enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
    /**
     * check if a box is closed
     * author: Philippe Nadon
     * @param position the position of the line
     * @param direction direction of checking the box
     * @return true if there is a box
     */
    private boolean checkIfBox(BoardPosition position, Direction direction, GameBoard gameBoard) {
        if (position.isHorizontal() &&
                (direction == Direction.LEFT || direction == Direction.RIGHT)) {
            return false;
        } else if (!position.isHorizontal() &&
                (direction == Direction.DOWN || direction == Direction.UP)) {
            return false;
        }
        switch (direction) {
            case UP:
                return (gameBoard.getLine(
                        position.yPos() - 1,
                        position.xPos(),
                        true
                )
                        && gameBoard.getLine(
                        position.yPos() - 1,
                        position.xPos(),
                        false
                )
                        && gameBoard.getLine(
                        position.yPos() - 1,
                        position.xPos() + 1,
                        false
                ));
            case RIGHT:
                return (gameBoard.getLine(
                        position.yPos(),
                        position.xPos(),
                        true
                )
                        && gameBoard.getLine(
                        position.yPos(),
                        position.xPos() + 1,
                        false
                )
                        && gameBoard.getLine(
                        position.yPos() + 1,
                        position.xPos(),
                        true
                ));
            case DOWN:
                return (gameBoard.getLine(
                        position.yPos(),
                        position.xPos(),
                        false
                )
                        && gameBoard.getLine(
                        position.yPos() + 1,
                        position.xPos(),
                        true
                )
                        && gameBoard.getLine(
                        position.yPos(),
                        position.xPos() + 1,
                        false
                ));
            default: // LEFT
                return (gameBoard.getLine(
                        position.yPos(),
                        position.xPos() - 1,
                        true
                )
                        && gameBoard.getLine(
                        position.yPos(),
                        position.xPos() - 1,
                        false
                )
                        && gameBoard.getLine(
                        position.yPos() + 1,
                        position.xPos() - 1,
                        true
                ));
        }
    }

}