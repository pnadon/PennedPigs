package com.example.piggiesteam5.datastructures;

public class GameSetup {
    private int boardSize;
    private boolean isTwoPlayer;
    private int difficulty;
    private String frenzyMode;

    public GameSetup(int boardSize, boolean isTwoPlayer, int difficulty, String frenzyMode){
        this.boardSize = boardSize;
        this.isTwoPlayer = isTwoPlayer;
        this.difficulty = difficulty;
        this.frenzyMode = frenzyMode;
    }

    public int getBoardSize(){
        return boardSize;
    }

    public void setBoardSize(int boardSize){
        this.boardSize = boardSize;
    }

    public boolean getIsTwoPlayer(){
        return isTwoPlayer;
    }

    public void setIsTwoPlayer(boolean isTwoPlayer){
        this.isTwoPlayer = isTwoPlayer;
    }

    public int getDiffculty(){
        return difficulty;
    }

    public void  setDiffculty(int difficulty){
        this.difficulty = difficulty;
    }

    public String getFrenzyMode(){
        return frenzyMode;
    }

    public void setFrenzyMode(String frenzyMode) {
        this.frenzyMode = frenzyMode;
    }
}
