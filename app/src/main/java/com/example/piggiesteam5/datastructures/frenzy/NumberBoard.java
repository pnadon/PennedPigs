package com.example.piggiesteam5.datastructures.frenzy;

public class NumberBoard {

    private int[][] numberBoard;

    public NumberBoard(MineBoard mineBoard, int height, int width){
        numberBoard = new int[height - 1][width - 1];
        for(int y = 0; y < height - 1; y++){
            for(int x = 0; x < width - 1; x++){
                if(y > 0 && x > 0 && mineBoard.getMine(y - 1, x - 1)){
                    numberBoard[y][x]++;
                }//top left
                if(y > 0 && mineBoard.getMine(y - 1, x)){
                    numberBoard[y][x]++;
                }//top
                if(y > 0 && x < width - 1 && mineBoard.getMine(y - 1, x + 1)){
                    numberBoard[y][x]++;
                }//top right
                if(x < width - 1 && mineBoard.getMine(y, x + 1)){
                    numberBoard[y][x]++;
                }//right
                if(y < height - 1 && x < width - 1 && mineBoard.getMine(y + 1,x + 1)){
                    numberBoard[y][x]++;
                }//bottom right
                if(y < height - 1 && mineBoard.getMine(y + 1, x)){
                    numberBoard[y][x]++;
                }//bottom
                if(y < height - 1 && x > 0 && mineBoard.getMine(y + 1, x - 1)){
                    numberBoard[y][x]++;
                }//bottom left
                if(x > 0 && mineBoard.getMine(y, x - 1)){
                    numberBoard[y][x]++;
                }//left
            }
        }
    }

    public int getNumberOfBox(int y, int x){
        return numberBoard[y][x];
    }

    public int[][] getNumberBoard() {
        return numberBoard;
    }
}