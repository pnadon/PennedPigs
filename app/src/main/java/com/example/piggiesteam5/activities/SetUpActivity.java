package com.example.piggiesteam5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.GameState;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.game.Game;
import com.example.piggiesteam5.datamanagement.IO;
import com.example.piggiesteam5.datamanagement.JSON;


public class SetUpActivity extends AppCompatActivity {

    @Override
    /**
     * Ensure that a SavedGameSetup file exists
     * Set page visibility based on previous setup
     * Enable the difficulty and size seekbars functionality
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_game);


        /**
         * if read from file == blank
         * Create a default object
         * else take the object's data
         * SavedGameSetup
         */
        final GameSetup gameSetupSave;
        Context context = getApplicationContext();
        if (!IO.fileExists(context, Constants.FileNames.SETUP)) {
            gameSetupSave = new GameSetup(3, false, 1, "");
            JSON.saveJSON(getApplicationContext(), gameSetupSave, Constants.FileNames.SETUP);
        } else
            gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);

        if (gameSetupSave.getIsTwoPlayer()) {
            TextView difficultyView = findViewById(R.id.difficulty);
            SeekBar diificultySeekbar = findViewById(R.id.DifficultySeekBar);
            difficultyView.setVisibility(View.INVISIBLE);
            diificultySeekbar.setVisibility((View.INVISIBLE));
        }

        SeekBar boardSize = findViewById(R.id.SizeSeekBar);
        boardSize.setProgress(gameSetupSave.getBoardSize() - 3);

        SeekBar difficulty = findViewById(R.id.DifficultySeekBar);
        difficulty.setProgress(gameSetupSave.getDiffculty());


        difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gameSetupSave.setDiffculty(progress);
                JSON.saveJSON(getApplicationContext(), gameSetupSave, Constants.FileNames.SETUP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        boardSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gameSetupSave.setBoardSize(progress + 3);
                JSON.saveJSON(getApplicationContext(), gameSetupSave, Constants.FileNames.SETUP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * For the play GAME button
     * Move to the gameBoard Screen
     * Create a new intent and startAcitivity
     */
    public void playGameButton(View v) {

        final GameSetup gameSetupSave;
        Context context = getApplicationContext();
        gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);
        int boardSize = gameSetupSave.getBoardSize();

        Options options = JSON.loadJSON(getApplicationContext(), Options.class, Constants.FileNames.OPTIONS);
        Game game = new Game(options, gameSetupSave);

        JSON.saveJSON(getApplicationContext(), game, Constants.FileNames.GAME);

        Intent goToGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(goToGame);
    }//PlayGameButton

    /**
     * For the FrenzyModeActivity button
     * Move to the Frenzy mode Screen
     * Create a new intent and startAcitivity
     */
    public void FrenzyModeButton(View v) {
        Intent frenzyMode = new Intent(getApplicationContext(), FrenzyModeActivity.class);
        startActivity(frenzyMode);
    }//FrenzyModeButton

    /**
     * For the Home button
     * Move to the Home Screen
     * Create a new intent and startAcitivity
     */
    public void HomeButton(View v) {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
    }//HomeButton

    /**
     * Set difficulty seekbar to INVISIBLE
     * set game setup to be two player
     * @param v
     */
    public void multiplayer(View v) {
        final GameSetup gameSetupSave;
        Context context = getApplicationContext();
        gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);

        TextView difficultyView = findViewById(R.id.difficulty);
        SeekBar difficultySeekbar = findViewById(R.id.DifficultySeekBar);
        if (difficultyView.getVisibility() == View.VISIBLE) {
            difficultyView.setVisibility(View.INVISIBLE);
            difficultySeekbar.setVisibility((View.INVISIBLE));
            gameSetupSave.setIsTwoPlayer(true);
        }

        JSON.saveJSON(getApplicationContext(), gameSetupSave, Constants.FileNames.SETUP);
        Log.i("Is Two Player", " " + gameSetupSave.getIsTwoPlayer());
    }

    /**
     * Set difficulty seekbar to VISIBLE
     * set game setup to be single player
     * @param v
     */
    public void singlePlayer(View v) {
        final GameSetup gameSetupSave;
        Context context = getApplicationContext();
        gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);

        TextView difficultyView = findViewById(R.id.difficulty);
        SeekBar diificultySeekbar = findViewById(R.id.DifficultySeekBar);


        if (!(difficultyView.getVisibility() == View.VISIBLE)) {
            difficultyView.setVisibility(View.VISIBLE);
            diificultySeekbar.setVisibility(View.VISIBLE);
            gameSetupSave.setIsTwoPlayer(false);
        }

        JSON.saveJSON(getApplicationContext(), gameSetupSave, Constants.FileNames.SETUP);
        Log.i("Is Two Player", " " + gameSetupSave.getIsTwoPlayer());
    }
}
