/*
 * BoxPosition.java
 *
 * Object which represents a position among the boxes of the grid
 * A "box" is an area surrounded by 4 lines
 * author: Philippe Nadon
 * date: Nov 27, 2019
 */

package com.example.piggiesteam5.datastructures;

import java.util.Random;

/**
 * Object which represents a position among the boxes of the grid
 */
public class BoxPosition {
    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private Random r;

    public BoxPosition(int yPos, int xPos, int height, int width) {
        this.yPos = yPos;
        this.xPos = xPos;
        this.height = height;
        this.width = width;
        this.r = new Random();
    }

    /**
     * Initial position is placed randomly
     */
    public BoxPosition(int height, int width) {
        this.width = width;
        this.height = height;
        this.r = new Random();
        setRandomPosition();
    }

    /**
     * A position is set on the board
     *
     * @param yPos         the vertical index of the board
     * @param xPos         the horizontal index of the board
     * @return true if the position lies on the board, false otherwise
     */
    public boolean setPosition(int yPos, int xPos) {
        if (yPos >= 0 && yPos < this.height && xPos >= 0 && xPos < this.width - 1) {
            this.yPos = yPos;
            this.xPos = xPos;
            return true;
        }
        return false;
    }

    /**
     * A position is set on the board
     * @param position represents the position
     * @return true if the position lies on the board, false otherwise
     */
    public boolean setPosition(BoxPosition position) {
        return setPosition(position.yPos(), position.xPos());
    }

    /**
     * A random position is chosen on the board
     */
    public void setRandomPosition() {
        this.yPos = r.nextInt(this.height);
        this.xPos = r.nextInt(this.width);
    }

    public int xPos() {
        return xPos;
    }

    public int yPos() {
        return yPos;
    }

    @Override
    public String toString() {
        return "xPos: " + xPos() +
                "\nyPos: " + yPos();
    }
}
