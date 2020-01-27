/*
 * SortScoreByRName.java
 *
 * Comparator class for sorting Score classes by name (decreasing order)
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures.sorters;

import com.example.piggiesteam5.datastructures.Score;

import java.util.Comparator;

/**
 * Comparator class for sorting Score classes by name (decreasing order)
 */
public class SortScoreByRName implements Comparator<Score> {
    public int compare(Score a, Score b) {
        return b.name().compareToIgnoreCase(a.name());
    }
}
