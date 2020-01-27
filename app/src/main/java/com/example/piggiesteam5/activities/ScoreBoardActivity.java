package com.example.piggiesteam5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.CustomListAdapter;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datamanagement.JSON;
import com.example.piggiesteam5.datastructures.ScoreBoard;

public class ScoreBoardActivity extends AppCompatActivity {

    ScoreBoard scoreBoard;
    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


        scoreBoard = JSON.loadJSON(getApplicationContext(), ScoreBoard.class, Constants.FileNames.SCORE_BOARD);
        adapter = new CustomListAdapter(this, scoreBoard);
        listView = findViewById(R.id.scoreViewID);
        listView.setAdapter(adapter);
//        DisplayScoreBoard();
    }

    public void SortByName(View v){
        scoreBoard.sortNames();
        adapter.notifyDataSetChanged();
//        DisplayScoreBoard();
    }


    public void SortByScore(View v){
        scoreBoard.sortScores();
        adapter.notifyDataSetChanged();
//        DisplayScoreBoard();
    }


    public void SortByDate(View v){
        scoreBoard.sortDates();
        adapter.notifyDataSetChanged();
//        DisplayScoreBoard();
    }

    public void HomeButton(View v) {
        Intent GoHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoHome);
    }//HomeButton
}
