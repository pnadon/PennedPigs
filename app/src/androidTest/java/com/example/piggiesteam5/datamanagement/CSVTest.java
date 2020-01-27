package com.example.piggiesteam5.datamanagement;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.piggiesteam5.datastructures.Score;
import com.example.piggiesteam5.datastructures.ScoreBoard;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CSVTest {

    Context context;
    ScoreBoard scoreBoard;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        scoreBoard = new ScoreBoard();
    }

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


    @Test
    public void testLoadScoreFromCSV() {
        try {
            scoreBoard = sampleAddScoreBoard();
            CSV.saveScoresToCSV(context, scoreBoard.getScores(), "scoreboard.csv");
            ArrayList<Score> loadedScoreList = CSV.loadCSVToScores(context, "scoreboard.csv");
            ArrayList<Score> currScoreList = scoreBoard.getScores();
            for (int i = 0; i < currScoreList.size(); i++) {
                assertEquals(currScoreList.get(i), loadedScoreList.get(i));
            }
        }catch(IOException e){
            Log.i("IO Exception showed up"," ");
        }catch(ParseException e){
            Log.i("ParseException showed up"," ");
        }

    }
}