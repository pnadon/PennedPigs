package com.example.piggiesteam5;

import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.game.Game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameUpdateScore {
    @Test
    public void testAllPossibleLinePlacementsAndUpdateScore() {
        int boardSize = 3;
        Game game = new Game(
                new Options(
                        3, true, true
                ),
                new GameSetup(
                        boardSize, true, 1, ""
                )
        );
        boolean success = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                try {
                    game.updateScore(new BoardPosition(
                            i, j, true, boardSize, boardSize
                    ), 0);
                } catch (Exception e) {
                    System.out.println("ERROR! ->" + i + " " + j + " true");
                    success = false;
                }
                try {
                    game.updateScore(new BoardPosition(
                            j, i, false, boardSize, boardSize
                    ), 0);
                } catch (Exception e) {
                    System.out.println("ERROR! ->" + j + " " + i + " false");
                    success = false;
                }
            }
        }
        assertEquals(true, success);
    }
}
