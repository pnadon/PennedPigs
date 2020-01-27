/*
 * SortScoreByRScore.java
 *
 * Comparator class for sorting Score classes by score (decreasing order)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.sorters;

import com.example.piggiesteam5.datastructures.Score;

import java.util.Comparator;

/**
 * Comparator class for sorting Score classes by score (decreasing order)
 */
public class SortScoreByRScore implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return b.score() - a.score();
    }
}
