/*
 * GameState.java
 *
 * Object which holds an instance of a game's state.
 * This includes player's current scores, how many undos are left, and how many lines have been placed
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures;

import java.util.Arrays;

/**
 * Object which holds an instance of a game's state.
 * This includes player's current scores, how many undos are left, and how many lines have been placed
 */
public class GameState {

    private int[] scores = {0,0};
    private int numLinesPlaced;
    private int maxNumLinesPlaced;
    private int numUndos;
    private int players;

    public GameState(int maxNumLinesPlaced, int numUndos) {
        this.maxNumLinesPlaced = maxNumLinesPlaced;
        this.numUndos = numUndos;
        players = 0;
    }

    public GameState(int maxNumLinesPlaced, int numUndos, int numLinesPlaced) {
        this.maxNumLinesPlaced = maxNumLinesPlaced;
        this.numUndos = numUndos;
        this.numLinesPlaced = numLinesPlaced;
         players = 0;
    }

    public GameState(GameState gameState) {
        this.scores = gameState.scores.clone();
        this.numLinesPlaced = gameState.numLinesPlaced;
        this.maxNumLinesPlaced = gameState.maxNumLinesPlaced;
        this.numUndos = gameState.numUndos;
        this.players = gameState.players;
    }

    public int[] getScores() {
        return scores;
    }

    public int getScore(int i) {
        return scores[i];
    }

    public void setScore(int i, int val) {
        scores[i] = val;
    }

    public int getNumLinesPlaced() {
        return numLinesPlaced;
    }

    public void setNumLinesPlaced(int numLinesPlaced) {
        this.numLinesPlaced = numLinesPlaced;
    }

    public void incrementScore(int i, int val) {
        scores[i] += val;
    }

    public int getMaxNumLinesPlaced() {
        return maxNumLinesPlaced;
    }

    public int getNumUndos() {
        return numUndos;
    }

    public void setNumUndos(int i) {
        numUndos = i;
    }

    public void decrementNumUndos() {
        numUndos -= 1;
    }

    public void toggleCurrentPlayersTurn() {
        if(players == 0){
            players = 1;
        }
        else{
            players = 0;
        }
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getPlayers(){
        return players;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("scores: " + scores[0] + " " + scores[1]);
        str.append("\nnumLinesPlaced: " + numLinesPlaced);
        str.append("\nmaxNumLinesPlaced: " + maxNumLinesPlaced);
        str.append("\nnumUndos: " + numUndos);
        str.append("\nplayers: " + players);
        return str.toString();
    }
}
