/*
 * PrePlacedBoard.java
 *
 * Library which generates various GameBoards with lines already placed on them
 * author: Philippe Nadon
 * date: Nov 27, 2019
 */
package com.example.piggiesteam5.datastructures.frenzy;

import com.example.piggiesteam5.datastructures.GameBoard;
import com.example.piggiesteam5.game.AI;

public class PrePlacedBoard {

    /**
     * @param height height of the GameBoard to be returned
     * @param width width of the GameBoard to be returned
     * @param numPrePlacedLines number of lines to be placed
     * @return the GameBoard with a certain amount of lines already placed
     */
    public static GameBoard getRandomPrePlacedBoard(int height, int width, int numPrePlacedLines) {
        GameBoard gameBoard = new GameBoard(height, width);
        AI ai = new AI(gameBoard);
        for(int i = 0; i < numPrePlacedLines; i++) {
            ai.placeRandomLine(gameBoard);
        }
        return gameBoard;
    }
}
