/*
 * GameState.java
 *
 * Object which holds an instance of a game's board.
 * The board is split into horizontal lines, and vertical lines
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures;

import java.util.Arrays;

/**
 * Object which holds an instance of a game's board.
 * The board is split into horizontal lines, and vertical lines
 */
public class GameBoard {
    private boolean[][] horiBoard;
    private boolean[][] vertBoard;
    private int[][] boxBoard;
    private int height;
    private int width;

    /**
     * constructor
     * @param height height of board , base on dots
     * @param width width of board
     */
    public GameBoard(int height, int width) {
        this.height = height;
        this.width = width;
        boxBoard = new int[height - 1][width - 1];
        horiBoard = new boolean[height][width - 1];
        vertBoard = new boolean[height - 1][width];
    }

    /**
     * constructor
     * @param gameBoard a gameBoard for loading setups
     */
    public GameBoard(GameBoard gameBoard) {
        this.height = gameBoard.getHeight();
        this.width = gameBoard.getWidth();
        boxBoard = new int[height - 1][width - 1];
        horiBoard = new boolean[height][width - 1];
        vertBoard = new boolean[height - 1][width];
        deepCopyBoard(gameBoard.horiBoard, this.horiBoard);
        deepCopyBoard(gameBoard.vertBoard, this.vertBoard);
        deepCopyBoard(gameBoard.boxBoard, this.boxBoard);
    }

    public int totalNumOfLines(){
        int result = 0;
        for(int y =0; y < height; y++){
            for(int x = 0; x < width - 1; x++){
                if(horiBoard[y][x]){
                    result ++;
                }
            }
        }
        for(int y =0; y < height - 1; y++){
            for(int x = 0; x < width; x++){
                if(vertBoard[y][x]){
                    result ++;
                }
            }
        }
        return result;
    }

    /**
     * Sets the line appropriately on the board
     * @param position the position of the line
     * @return true if placed in a valid position, false otherwise
     */
    public boolean setLine(BoardPosition position) {
        if(position.isHorizontal()) {
            if (horiBoard[position.yPos()][position.xPos()]) {
                return false;
            }
            horiBoard[position.yPos()][position.xPos()] = true;
        } else {
            if(vertBoard[position.yPos()][position.xPos()]) {
                return false;
            }
            vertBoard[position.yPos()][position.xPos()] = true;
        }
        return true;
    }

    /**
     * @param yIndex the vertical index of the board
     * @param xIndex the horizontal index of the board
     * @param isHorizontal whether the line is horizontal or not
     * @return whether a line is placed there or not
     */
    public boolean getLine(int yIndex, int xIndex, boolean isHorizontal) {
        return isHorizontal ? horiBoard[yIndex][xIndex] : vertBoard[yIndex][xIndex];
    }

    /**
     * return boxBoard for visualization
     * @return whole boxBoard
     */
    public int[][] getBoxBoard(){
        return boxBoard;
    }

    /**
     * set a box to box board
     * @param yIndex vertical index of box
     * @param xIndex horizontal index of the board
     * @param player player, 0 for 1st player , 1 for second or AI
     */
    public void setBoxBoard(int yIndex, int xIndex, int player){
        boxBoard[yIndex][xIndex] = player + 1;
    }

    /**
     * @return height of board
     */
    public int getHeight() {
        return height;
    }

    /**
     * getter for width of board
     * @return width of board
     */
    public int getWidth() {
        return width;
    }

    public int getMaxBoxes() {
        return (height - 1) * (width - 1);
    }

    /**
     * deep copy the board for save and load
     * @param board1 current board
     * @param board2 prev board
     */
    private void deepCopyBoard(int[][] board1, int[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            System.arraycopy(board1[i], 0, board2[i], 0, board1[i].length);
        }
    }


    private void deepCopyBoard(boolean[][] source, boolean[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < height; i++) {
            str.append(Arrays.toString(horiBoard[i]));
            str.append("\n");
        }
        str.append("\n");
        for(int i = 0; i < height - 1; i++) {
            str.append(Arrays.toString(vertBoard[i]));
            str.append("\n");
        }
        return str.toString();
    }
}
