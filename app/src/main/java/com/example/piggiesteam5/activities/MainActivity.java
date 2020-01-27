package com.example.piggiesteam5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datamanagement.IO;
import com.example.piggiesteam5.datamanagement.JSON;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.datastructures.ScoreBoard;


public class MainActivity extends AppCompatActivity {
    @Override
    /**
     * Ensure OptionsSave file exists
     * Ensure ScoreBoard file exists
     * Set the continue buttons visibility to whether a game exists
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Options OptionsSave;
        if (!IO.fileExists(getApplicationContext(), Constants.FileNames.OPTIONS)) {
            OptionsSave = new Options(3, false, false);
            JSON.saveJSON(getApplicationContext(), OptionsSave, Constants.FileNames.OPTIONS);
        }

        final ScoreBoard ScoreBoard;
        Context context = getApplicationContext();
        if (!IO.fileExists(context, Constants.FileNames.SCORE_BOARD)) {
            ScoreBoard = new ScoreBoard();
            JSON.saveJSON(getApplicationContext(), ScoreBoard, Constants.FileNames.SCORE_BOARD);
        }

        if (IO.fileExists(context, Constants.FileNames.GAME)){
            Button Continue = findViewById(R.id.ContinueButton);
            Continue.setVisibility(View.VISIBLE);
        }
    }//OnCreate

    /**
     * For the new game button
     * Create a new intent to go to game setup activity and startActivity
     */
    public void NewGameButton(View v) {
        Intent goToGameSetup = new Intent(getApplicationContext(), SetUpActivity.class);
        startActivity(goToGameSetup);
    }//NewGameButton


    /**
     * For the Continue button
     * Create a new intent based on previous game and startActivity
     */
    public void ContinueButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intentName);

    }//ContinueButton

    /**
     * For the About us button
     * Create a new intent to go to about us page and startActivity
     */
    public void AboutUsButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), AboutUsActivity.class);
        startActivity(intentName);
    }//AboutUsButton

    /**
     * For the score board button
     * Create a new intent to go to the score board and startActivity
     */
    public void ScoreBoardButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), ScoreBoardActivity.class);
        startActivity(intentName);
    }//ScoreBoardButton

    /**
     * For the Options button
     * Create a new intent to go to the options activity and startActivity
     */
    public void OptionsButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), OptionsActivity.class);
        startActivity(intentName);
    }//OptionsButton
}//Class
