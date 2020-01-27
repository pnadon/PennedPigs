package com.example.piggiesteam5.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datastructures.BoardPosition;
import com.example.piggiesteam5.datastructures.GameBoard;
import com.example.piggiesteam5.datastructures.GameSetup;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.game.Game;
import com.example.piggiesteam5.datamanagement.JSON;


public class GameActivity extends AppCompatActivity {
    boolean playerTurn = true;
    Game game;
    GameSetup gameSetup;
    private Options options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Read all files
        game = JSON.loadJSON(getApplicationContext(), Game.class, Constants.FileNames.GAME);
        options = JSON.loadJSON(getApplicationContext(),Options.class, Constants.FileNames.OPTIONS);
        gameSetup = JSON.loadJSON(getApplicationContext(), GameSetup.class, Constants.FileNames.SETUP);

        //Load proper screen
        if (gameSetup.getBoardSize() == 3) {
            setContentView(R.layout.activity_game_board3x3);
        } else if (gameSetup.getBoardSize() == 4) {
            setContentView(R.layout.activity_game_board4x4);
        } else {
            setContentView(R.layout.activity_game_board5x5);
        }

        //Update display
        updateDisplay();
        displayNumUndos();
    }
    protected void onStart() {
        super.onStart();
        //Used if going to options and back
        options = JSON.loadJSON(getApplicationContext(),Options.class, Constants.FileNames.OPTIONS);
    }



    public void OptionsButton(View v) {
        Intent intentName = new Intent(getApplicationContext(), OptionsActivity.class);
        startActivity(intentName);
    }//HomeButton

    /**
     * Method for the Undo button
     * Needs to change the display and gameboard
     * @param v undo button
     */
    public void UndoButton(View v) {
        game.triggerUndo();
        displayNumUndos();
        reDrawBoard();
        updateDisplay();
    }//undo button

    /**
     * Main method for trying to place lines
     * Side effects may include AI turn
     * @param v Button the user is trying to place a line with
     */
    public void receiveButtonClick(View v) {
        playerTurn = playerTurn(v);

        if ((!gameSetup.getIsTwoPlayer()) && !playerTurn) {
            aiWrapper();
        }

        JSON.saveJSON(getApplicationContext(), game, Constants.FileNames.GAME);
    }

    /**
     * Button that is passed must have a tag associated with its location
     * This tag is used to create the location
     *
     * @param v the view that was clicked
     * @return int[] location
     */
    private int[] locationCreation(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        final int[] location = new int[2];
        location[0] = (tag - (tag % 10)) / 10;
        location[1] = tag % 10;

        return location;
    }

    /**
     * Set respective views to what the score was
     */
    private void updateScoreBoard() {
        int[] scores = game.getScore();

        TextView player1Score = findViewById(R.id.player1Score);
        player1Score.setText("" + scores[0] + "%");

        TextView player2Score = findViewById(R.id.player2Score);
        player2Score.setText("" + scores[1] + "%");
    }

    /**
     * Set turnIndicator to respective turn
     */
    private void whichPlayersTurn() {
        int turn = game.getTurn();
        TextView turnIndicator = findViewById(R.id.turnIndicator);

        if (turn == 0) {
            turnIndicator.setText("Player 1 Turn");
            turnIndicator.setBackgroundColor(getResources().getColor(R.color.colorPink));
        } else if (gameSetup.getIsTwoPlayer()) {
            turnIndicator.setText("Player 2 Turn");
            turnIndicator.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        } else {
            turnIndicator.setText("AI Turn");
            turnIndicator.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        }
    }


    private boolean playerTurn(View v) {
        //Figure out where the dot just clicked is
        int[] location;
        location = locationCreation(v);

        //Figure out if there was a previous dot clicked
        int[] firstClickedDotLocation;
        firstClickedDotLocation = game.clicks(location);

        //if this was the first button clicked set the background to a circle image
        if (firstClickedDotLocation[0] == -1) {
            v.setBackgroundResource(R.drawable.circle);
            return true;
        } else {
            //Reset the firstClickedDot back to original color scheme
            setFirstClickedButtonBackToOriginalColorScheme(firstClickedDotLocation);
            updateDisplay();

            //Play sound
            MediaPlayer boardPlacement = MediaPlayer.create(this, R.raw.wood_placement);
            if(options.getSoundEffects())
                boardPlacement.start();


            MediaPlayer pigOink = MediaPlayer.create(this, R.raw.pig);
            if (game.gameIsDone()) {
                if(options.getSoundEffects())
                    pigOink.start();
                Intent intentName = new Intent(getApplicationContext(), ResultsActivity.class);
                startActivity(intentName);
                return true;
            } else if (game.playerPlayAgain()) {
                if(options.getSoundEffects())
                    pigOink.start();
                return true;

            } else {
                return false;
            }
        }

    }

    private void displayNumUndos() {
        int numUndos = game.getUndos();
        TextView undoCounter = findViewById(R.id.undoCounter);
        undoCounter.setText(Integer.toString(numUndos));
    }

    private void checkIfGameIsDoneAndEnd(){
        if (game.gameIsDone()) {
            Intent intentName = new Intent(getApplicationContext(), ResultsActivity.class);
            startActivity(intentName);
        }
    }

    private void reDrawBoard() {
        GameBoard gameBoard = game.getBoard();
        System.out.println("REDRAWING: ");
        System.out.println(gameBoard.toString());
        String id;
        int resID;
        ImageView lineToShow;
        for (int i = 0; i < (gameBoard.getHeight()); i++) {
            for (int j = 0; j < (gameBoard.getWidth() - 1); j++) {
                id = Constants.Board.HORI_PREFIX + i + j;
                resID = getResources().getIdentifier(id, "id", getPackageName());
                lineToShow = findViewById(resID);
                if (gameBoard.getLine(i, j, true)) {
                    lineToShow.setVisibility(View.VISIBLE);
                } else {
                    lineToShow.setVisibility(View.INVISIBLE);
                }
                id = Constants.Board.VERT_PREFIX + j + i;
                resID = getResources().getIdentifier(id, "id", getPackageName());
                lineToShow = findViewById(resID);
                if (gameBoard.getLine(j, i, false)) {
                    lineToShow.setVisibility(View.VISIBLE);
                } else {
                    lineToShow.setVisibility(View.INVISIBLE);
                }
            }
        }
        ImageView boxToShow;
        for (int i = 0; i < (gameBoard.getHeight() - 1); i++) {
            for (int j = 0; j < (gameBoard.getWidth() - 1); j++) {

            int boxBoard[][];
            boxBoard = gameBoard.getBoxBoard();
            id = "pen" + i + j;
            resID = getResources().getIdentifier(id, "id", getPackageName());
            boxToShow = findViewById(resID);
            if (boxBoard[i][j] == 1){
                boxToShow.setImageDrawable(getApplicationContext().getDrawable(R.drawable.ic_player1));
                boxToShow.setVisibility(View.VISIBLE);
            }
            else if (boxBoard[i][j] == 2){
                boxToShow.setImageDrawable(getApplicationContext().getDrawable(R.drawable.ic_player2));
                boxToShow.setVisibility(View.VISIBLE);
            }
            else
                boxToShow.setVisibility(View.INVISIBLE);
            }//inner for
        }//outer for
    }

    private void updateDisplay(){
        reDrawBoard();
        updateScoreBoard();
        whichPlayersTurn();
    }

    private void setFirstClickedButtonBackToOriginalColorScheme(int[] firstClickedDotLocation){
        String firstClickedDot;
        firstClickedDot = "location" + firstClickedDotLocation[0] + firstClickedDotLocation[1];
        int intID = getResources().getIdentifier(firstClickedDot, "id", getPackageName());
        ImageButton firstButton = findViewById(intID);
        firstButton.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
    }

    /**
     * Sequence of steps that the AI will need to run through
     */
    private void aiWrapper(){
        game.AIturn();
        updateDisplay();
        checkIfGameIsDoneAndEnd();
        playerTurn = true;
    }
}
