package com.example.piggiesteam5.datastructures;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SortersTest {

    private Score score1;
    private Score score2;
    private Score score3;
    private Score score4;

    @Before
    public void setup() {
        score1 = new Score("a", 1, MyDate.StringToDate("01/01/2013"));
        score2 = new Score("b", 2, MyDate.StringToDate("01/01/2019"));
        score3 = new Score("aa", 10, MyDate.StringToDate("01/01/2019"));
        score4 = new Score("ab", 11, MyDate.StringToDate("02/01/2019"));
    }

    @Test
    public void testCompareName() {
        assertEquals(-1, score1.name().compareTo(score2.name()));
        assertEquals(1, score2.name().compareTo(score1.name()));
        assertEquals(1, score3.name().compareTo(score1.name()));
        assertEquals(-1, score3.name().compareTo(score4.name()));
    }

    @Test
    public void testCompareDate() {
        assertEquals(-1, score1.date().compareTo(score2.date()));
        assertEquals(1, score2.date().compareTo(score1.date()));
        assertEquals(1, score3.date().compareTo(score1.date()));
        assertEquals(-1, score3.date().compareTo(score4.date()));
    }

    @Test
    public void testCompareScore() {
        assertEquals(-1, score1.score() - score2.score());
        assertEquals(1, score2.score() - score1.score());
        assertEquals(9, score3.score() - (score1.score()));
        assertEquals(-1, score3.score() - (score4.score()));
    }
}
