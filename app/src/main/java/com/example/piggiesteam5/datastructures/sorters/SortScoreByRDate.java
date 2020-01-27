/*
 * SortScoreByRDate.java
 *
 * Comparator class for sorting Score classes by date (decreasing order)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.sorters;

import com.example.piggiesteam5.datastructures.Score;

import java.util.Comparator;

/**
 * Comparator class for sorting Score classes by date (decreasing order)
 */
public class SortScoreByRDate implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return b.date().compareTo(a.date());
    }
}
