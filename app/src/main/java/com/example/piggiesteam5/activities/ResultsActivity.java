package com.example.piggiesteam5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.MyDate;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.datastructures.Score;
import com.example.piggiesteam5.datastructures.ScoreBoard;
import com.example.piggiesteam5.game.Game;
import com.example.piggiesteam5.datamanagement.IO;
import com.example.piggiesteam5.datamanagement.JSON;

public class ResultsActivity extends AppCompatActivity {
    Context context;
    Game game;
    GameSetup TheGamesSetup;
    long winnerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        context = getApplicationContext();
        game = JSON.loadJSON(context, Game.class, Constants.FileNames.GAME);
        IO.deleteFile(context, Constants.FileNames.GAME);
        TheGamesSetup = JSON.loadJSON(getApplicationContext(), GameSetup.class, Constants.FileNames.SETUP);

        //Winner Section
        TextView winner = findViewById(R.id.Winner);
        int[] Scores;
        Scores = game.getScore();
        winnerScore = Math.max(Scores[0], Scores[1]);
        if (Scores[0] == Scores[1]) {
            winner.setText("Tie GAME");
        } else if (Scores[0] > Scores[1]) {
            winner.setText("Player 1 Wins");
        } else if (Scores[0] < Scores[1] && TheGamesSetup.getIsTwoPlayer()) {
            winner.setText("Player 2 Wins");
        } else
            winner.setText("AI Wins");

        //Score Section
        TextView scoreDisplay = findViewById(R.id.DisplayedScores);
        if (TheGamesSetup.getIsTwoPlayer()) {
            scoreDisplay.setText("Player 1 Score: " + Scores[0] + "\nPlayer 2 Score: " + Scores[1]);
        } else {
            scoreDisplay.setText("Player Score: " + Scores[0] + "\nAI Score: " + Scores[1]);
        }

    }

    /**
     * Send the user straight back into the game
     * @param v
     */
    public void refreshButton(View v) {
        final GameSetup gameSetupSave;

        gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);
        int boardSize = gameSetupSave.getBoardSize();

        Options options = JSON.loadJSON(getApplicationContext(), Options.class, Constants.FileNames.OPTIONS);
        Game game = new Game(options, gameSetupSave);
        JSON.saveJSON(getApplicationContext(), game, Constants.FileNames.GAME);
        Intent goToGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(goToGame);
    }

    public String getUserId() {
        EditText UserID = findViewById(R.id.getUserID);
        String name = UserID.getText().toString();
        return name;
    }

    public void generateScore(View v) {
        int[] Scores;
        Scores = game.getScore();
        String userId = getUserId();
        if(!userId.equals("")) {
            ScoreBoard scoreBoard = JSON.loadJSON(getApplicationContext(), ScoreBoard.class, Constants.FileNames.SCORE_BOARD);
            if(TheGamesSetup.getIsTwoPlayer())
                scoreBoard.addScore(new Score(userId, winnerScore, MyDate.getCurrentDate()));
            else
                scoreBoard.addScore(new Score(userId, Scores[0], MyDate.getCurrentDate()));
            JSON.saveJSON(getApplicationContext(), scoreBoard, Constants.FileNames.SCORE_BOARD);
        }
        v.setVisibility(View.INVISIBLE);
        EditText getInput = findViewById(R.id.getUserID);
        getInput.setVisibility(View.INVISIBLE);
    }

    public void HomeButton(View v) {
        Intent GoHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoHome);
    }//HomeButton

    public void ScoreBoardButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), ScoreBoardActivity.class);
        startActivity(intentName);
    }//ScoreBoardButton

    public void shareButton(View v) {
        Intent sendIntent = new Intent();
        TextView scoreDisplay = findViewById(R.id.DisplayedScores);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, scoreDisplay.getText());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

}
