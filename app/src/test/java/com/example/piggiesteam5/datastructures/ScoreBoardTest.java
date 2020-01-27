package com.example.piggiesteam5.datastructures;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ScoreBoardTest {

    ScoreBoard scoreBoard;

    public ScoreBoard sampleAddScoreBoard() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy");
        try {
            Score score1 = new Score("bob", 100, dateFormatter.parse("01/11/2019"));
            Score score2 = new Score("aob", 10, dateFormatter.parse("01/01/2019"));
            Score score3 = new Score("z", 2, dateFormatter.parse("01/11/2016"));
            Score score4 = new Score("zed", 1, dateFormatter.parse("02/11/2016"));
            Score score5 = new Score("cat", 25, dateFormatter.parse("01/01/2013"));
            Score score6 = new Score("dog", 1000, dateFormatter.parse("09/11/2019"));
            Score score7 = new Score("z", 10, dateFormatter.parse("01/11/2019"));
            scoreBoard.addScore(score1);
            scoreBoard.addScore(score2);
            scoreBoard.addScore(score3);
            scoreBoard.addScore(score4);
            scoreBoard.addScore(score5);
            scoreBoard.addScore(score6);
            scoreBoard.addScore(score7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return scoreBoard;
    }

    public ArrayList<Score> sampleScoreBoard() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy");
        ArrayList<Score> scores = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                scores.add(new Score("bob", 100, dateFormatter.parse("01/11/2019")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return scores;
    }

    @Before
    public void setup() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void scoreBoardTest_TestInitializer() {
        ScoreBoard scoreBoard = new ScoreBoard(sampleScoreBoard());
        assertEquals(5, scoreBoard.length());
    }

    @Test
    public void scoreBoardTest_AddingScores() {
        ScoreBoard scoreBoard = sampleAddScoreBoard();

        assertEquals(7, scoreBoard.length());
    }

    @Test
    public void scoreBoardTest_SortingByName() {
        ScoreBoard scoreBoard = sampleAddScoreBoard();

        scoreBoard.sortNames();
        assertEquals(scoreBoard.toString(), "aob", scoreBoard.getScore(0).name());
        scoreBoard.sortNames();
        assertEquals(scoreBoard.toString(), "zed", scoreBoard.getScore(0).name());
    }

    @Test
    public void scoreBoardTest_SortingByDate() {
        ScoreBoard scoreBoard = sampleAddScoreBoard();

        scoreBoard.sortDates();
        assertEquals(scoreBoard.toString(), "dog", scoreBoard.getScore(0).name());
        scoreBoard.sortDates();
        assertEquals(scoreBoard.toString(), "cat", scoreBoard.getScore(0).name());
    }

    @Test
    public void scoreBoardTest_SortingByScore() {
        ScoreBoard scoreBoard = sampleAddScoreBoard();

        scoreBoard.sortScores();
        assertEquals(scoreBoard.toString(), "dog", scoreBoard.getScore(0).name());
        scoreBoard.sortScores();
        assertEquals(scoreBoard.toString(), "zed", scoreBoard.getScore(0).name());
    }
}