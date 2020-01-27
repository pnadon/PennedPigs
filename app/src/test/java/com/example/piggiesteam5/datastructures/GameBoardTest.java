package com.example.piggiesteam5.datastructures;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GameBoardTest {
    GameBoard gameBoard;

    @Before
    public void setup() {
        gameBoard = new GameBoard(3, 3);
    }

    public GameBoard sampleGameBoard() {
        GameBoard innerGameBoard = new GameBoard(3, 3);
        innerGameBoard.setLine(new BoardPosition(0, 0, false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1, 0, false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1, 2, false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(0, 0, true, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1, 1, true, 3, 3));
        innerGameBoard.setLine(new BoardPosition(2, 1, true, 3, 3));
        return innerGameBoard;
    }

    @Test
    public void testSetLine() {
        gameBoard = sampleGameBoard();
        assertEquals(
                "[true, false]\n" +
                        "[false, true]\n" +
                        "[false, true]\n" +
                        "\n" +
                        "[true, false, false]\n" +
                        "[true, false, true]\n",
                gameBoard.toString()
        );
        assertEquals(false, gameBoard.setLine(new BoardPosition(0, 0, false, 3, 3)));
    }

    @Test
    public void testGetLine() {
        gameBoard = sampleGameBoard();
        assertEquals(true, gameBoard.getLine(0, 0, true));
    }
}
