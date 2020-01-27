package com.example.piggiesteam5.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datamanagement.JSON;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.datastructures.frenzy.PigSweeperGame;
import com.example.piggiesteam5.game.Game;

import static com.example.piggiesteam5.datastructures.frenzy.PrePlacedBoard.getRandomPrePlacedBoard;

public class FrenzyModeActivity extends AppCompatActivity {
    //TODO turn all the button onClickListeners into their own methods
    //TODO turn buttons to a new color if selected
    //TODO If the help button is hit pop up a correlating display box

    private int gameMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frenzy_mode);
        TextView defaultGameModeDescription = findViewById(R.id.classicGameModeDescription);
        defaultGameModeDescription.setBackgroundColor(Color.LTGRAY);
    }

    public void backButton(View v){
        finish();
    }

    public void defaultButton(View v){
        gameMode = 0;
        TextView defaultGameModeDescription = findViewById(R.id.classicGameModeDescription);
        TextView otherButtonDescriptions = findViewById(R.id.RandomLinesDescription);
        otherButtonDescriptions.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        defaultGameModeDescription.setBackgroundColor(Color.LTGRAY);

    }

    public void prePlacedButton(View v){
        gameMode = 2;                           //Pig sweeper was one
        TextView defaultGameModeDescription = findViewById(R.id.classicGameModeDescription);
        TextView otherButtonDescriptions = findViewById(R.id.RandomLinesDescription);
        otherButtonDescriptions.setBackgroundColor(Color.LTGRAY);
        defaultGameModeDescription.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
    }


    public void playGameButton(View v){
        final GameSetup gameSetupSave;
        Context context = getApplicationContext();
        gameSetupSave = JSON.loadJSON(context, GameSetup.class, Constants.FileNames.SETUP);
        Options options = JSON.loadJSON(getApplicationContext(), Options.class, Constants.FileNames.OPTIONS);

        Game game;
        if (gameMode == 2){ //PreplacedLinesGame
            int boardSize = gameSetupSave.getBoardSize();
            game = new Game(options, gameSetupSave);
            game.setBoard(getRandomPrePlacedBoard(boardSize, boardSize, (int)(0.5 * boardSize * boardSize)));
            JSON.saveJSON(getApplicationContext(), game, Constants.FileNames.GAME);
        }//if
        else {              //Default Game
            game = new Game(options, gameSetupSave);
            JSON.saveJSON(getApplicationContext(), game, Constants.FileNames.GAME);
        }//else


        Intent goToGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(goToGame);
    }
}
