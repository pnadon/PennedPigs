/*
 * SortScoreByDate.java
 *
 * Comparator class for sorting Score classes by date (increasing order)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.sorters;

import com.example.piggiesteam5.datastructures.Score;

import java.util.Comparator;

/**
 * Comparator class for sorting Score classes by date
 */
public class SortScoreByDate implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return a.date().compareTo(b.date());
    }
}
