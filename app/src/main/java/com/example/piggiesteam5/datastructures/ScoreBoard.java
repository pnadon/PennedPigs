/*
 * SCORE_BOARD.java
 *
 * Object which defines an instance of a SCORE_BOARD
 * A SCORE_BOARD holds an ArrayList of Scores, along with methods for sorting
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures;

import com.example.piggiesteam5.datastructures.sorters.SortScoreByDate;
import com.example.piggiesteam5.datastructures.sorters.SortScoreByName;
import com.example.piggiesteam5.datastructures.sorters.SortScoreByRDate;
import com.example.piggiesteam5.datastructures.sorters.SortScoreByRName;
import com.example.piggiesteam5.datastructures.sorters.SortScoreByRScore;
import com.example.piggiesteam5.datastructures.sorters.SortScoreByScore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * holds an ArrayList of Scores, along with methods for sorting
 */
public class ScoreBoard {
    private ArrayList<Score> scores;
    private boolean isSortedByName;
    private boolean isSortedByScore;
    private boolean isSortedByDate;

    public ScoreBoard() {
        this.scores = new ArrayList<>();
        this.isSortedByDate = false;
        this.isSortedByName = false;
        this.isSortedByScore = false;
    }
    public ScoreBoard(ArrayList<Score> scores) {
        this.scores = scores;
        this.isSortedByDate = false;
        this.isSortedByName = false;
        this.isSortedByScore = false;
    }

    /**
     * Sorts the list of scores by name
     */
    public void sortNames() {
        if( this.isSortedByName) {
            Collections.sort(this.scores, new SortScoreByRName());
            this.isSortedByName = false;
        } else {
            Collections.sort(this.scores, new SortScoreByName());
            this.isSortedByName = true;
        }
    }

    /**
     * Sorts the list of scores by score
     */
    public void sortScores() {
        if( this.isSortedByScore) {
            Collections.sort(this.scores, new SortScoreByScore());
            this.isSortedByScore = false;
        } else {
            Collections.sort(this.scores, new SortScoreByRScore());
            this.isSortedByScore = true;
        }
    }

    /**
     * Sorts the list of scores by date
     */
    public void sortDates() {
        if( this.isSortedByDate) {
            Collections.sort(this.scores, new SortScoreByDate());
            this.isSortedByDate = false;
        } else {
            Collections.sort(this.scores, new SortScoreByRDate());
            this.isSortedByDate = true;
        }
    }

    /**
     * Adds a score to the list of scores
     * @param score the score to add
     */
    public void addScore(Score score) {
        this.scores.add(score);
    }

    /**
     * @return The number of scores in scores
     */
    public int length() { return this.scores.size(); }

    /**
     * @param index the index at which the Score is located in the SCORE_BOARD
     * @return the Score at the index specified, in the SCORE_BOARD
     */
    public Score getScore(int index) {
        return this.scores.get(index);
    }

    /**
     * @return returns the SCORE_BOARD's list of Scores
     */
    public ArrayList<Score> getScores() { return this.scores; }

    /**
     * Empties the SCORE_BOARD's list of scores
     */
    public void makeEmpty() {
        scores = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for( Score score : this.scores) {
            str.append(score.toString() + "\n");
        }
        return str.toString();
    }
}
