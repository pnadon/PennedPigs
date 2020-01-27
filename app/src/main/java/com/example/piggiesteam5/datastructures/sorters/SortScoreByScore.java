/*
 * SortScoreByScore.java
 *
 * Comparator class for sorting Score classes by score (increasing order)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.sorters;

import com.example.piggiesteam5.datastructures.Score;

import java.util.Comparator;

/**
 * Comparator class for sorting Score classes by score (increasing order)
 */
public class SortScoreByScore implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return a.score() - b.score();
    }
}
