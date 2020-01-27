/*
 * BoardPosition.java
 *
 * Object which represents a position on a board (of a line)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures;

import java.util.Arrays;
import java.util.Random;

/**
 * Object which represents a position on a board (of a line)
 */
public class BoardPosition {
    private int xPos;
    private int yPos;
    private boolean isHorizontal;
    private int height;
    private int width;
    private Random r;

    public BoardPosition(int yPos, int xPos, boolean isHorizontal, int height, int width) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isHorizontal = isHorizontal;
        this.height = height;
        this.width = width;
        this.r = new Random();
    }

    /**
     * Initial position is placed randomly
     */
    public BoardPosition(int height, int width) {
        this.width = width;
        this.height = height;
        this.r = new Random();
        setRandomPosition();
    }

    /**
     * A position is set on the board
     * @param yPos the vertical index of the board
     * @param xPos the horizontal index of the board
     * @param isHorizontal whether the line is horizontal or not
     * @return true if the position lies on the board, false otherwise
     */
    public boolean setPosition(int yPos, int xPos, boolean isHorizontal) {
        if( isHorizontal) {
            if(yPos >= 0 && yPos < this.height && xPos >= 0 && xPos < this.width - 1) {
                this.yPos = yPos;
                this.xPos = xPos;
                this.isHorizontal = true;
            } else {
                return false;
            }
        } else {
            if(yPos >= 0 && yPos < height - 1 && xPos >= 0 && xPos < width) {
                this.yPos = yPos;
                this.xPos = xPos;
                this.isHorizontal = false;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * A position is set on the board
     * @param position represents the position
     * @return true if the position lies on the board, false otherwise
     */
    public boolean setPosition(BoardPosition position) {
        return setPosition(position.yPos(), position.xPos(), position.isHorizontal());
    }

    /**
     * A random position is chosen on the board
     */
    public void setRandomPosition() {
        boolean rIsHorizontal = r.nextBoolean();
        if( rIsHorizontal) {
            this.yPos = r.nextInt(this.height);
            this.xPos = r.nextInt(this.width - 1);
            this.isHorizontal = true;
        } else {
            this.yPos = r.nextInt(this.height - 1);
            this.xPos = r.nextInt(this.width);
            this.isHorizontal = false;
        }
    }

    public int xPos() {
        return xPos;
    }

    public int yPos() {
        return yPos;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    @Override
    public String toString() {
        return "xPos: " + xPos() +
                "\nyPos: " + yPos() +
                "\nisHorizontal: " + isHorizontal();
    }
}
