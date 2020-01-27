/*
 * MineBoard.java
 *
 * Object which represents a 2D board of pre-placed landmines
 * author: Philippe Nadon
 * date: Nov 27, 2019
 */

package com.example.piggiesteam5.datastructures.frenzy;

import com.example.piggiesteam5.datastructures.BoxPosition;

/**
 * Object which represents a 2D board of pre-placed landmines
 */
public class MineBoard {
    private boolean[][] mineBoard;
    private int numMines;

    public MineBoard(int height, int width, int numMines) {
        BoxPosition pos = new BoxPosition(height, width);
        mineBoard = new boolean[height][width];
        for (int i = 0; i < numMines; i++) {
            while (getMine(pos.yPos(), pos.xPos())) {
                pos.setRandomPosition();
            }
            mineBoard[pos.yPos()][pos.xPos()] = true;
        }
        this.numMines = numMines;
    }

    public boolean getMine(int yPos, int xPos) {
        return mineBoard[yPos][xPos];
    }

    public int getNumMines() { return numMines; }
}
