package com.example.piggiesteam5.datamanagement;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameBoard;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class JSONTest {

    Context context;
    GameBoard gameBoard;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        gameBoard = new GameBoard(3,3);
    }

    public GameBoard sampleGameBoard() {
        GameBoard innerGameBoard = new GameBoard(3,3);
        innerGameBoard.setLine(new BoardPosition(0,0,false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1,0,false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1,2,false, 3, 3));
        innerGameBoard.setLine(new BoardPosition(0,0,true, 3, 3));
        innerGameBoard.setLine(new BoardPosition(1,1,true, 3, 3));
        innerGameBoard.setLine(new BoardPosition(2,1,true, 3, 3));
        return innerGameBoard;
    }

    @Test
    public void testGameBoardToJSON() {
        gameBoard = sampleGameBoard();
        assertEquals(true, JSON.saveJSON(context, gameBoard, "gameboard.json"));
        GameBoard loadedGameBoard = JSON.loadJSON(context, GameBoard.class,"gameboard.json");
        for(int i = 0; i < gameBoard.getHeight(); i++) {
            for(int j = 0; j < gameBoard.getWidth() - 1; j++) {
                assertEquals(gameBoard.getLine(i, j, true), loadedGameBoard.getLine(i, j, true));
            }
        }
        for(int i = 0; i < gameBoard.getHeight() - 1; i++) {
            for(int j = 0; j < gameBoard.getWidth(); j++) {
                assertEquals(gameBoard.getLine(i, j, false), loadedGameBoard.getLine(i, j, false));
            }
        }
    }


}