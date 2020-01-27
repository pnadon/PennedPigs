package com.example.piggiesteam5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.R;
import com.example.piggiesteam5.datastructures.Options;
import com.example.piggiesteam5.datamanagement.IO;
import com.example.piggiesteam5.datamanagement.JSON;

public class OptionsActivity extends AppCompatActivity {
    /**
     * Set visibilities
     * create or load optionsSave
     * Functionality of seekbar and in game music and sound buttons
     */
    private Options OptionsSave;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        Button confirmation = findViewById(R.id.Confirmation);
        confirmation.setVisibility(View.INVISIBLE);
        Button DeleteEverything = findViewById(R.id.DeleteEverythingButton);
        DeleteEverything.setVisibility(View.VISIBLE);


        if (!IO.fileExists(getApplicationContext(), Constants.FileNames.OPTIONS)) {
            OptionsSave = new Options(3, true, true);
        } else {
            OptionsSave = JSON.loadJSON(getApplicationContext(), Options.class, Constants.FileNames.OPTIONS);
        }

        Button inGameMusicButton = findViewById(R.id.InGameMusicButton);
        if(OptionsSave.getSound())
            inGameMusicButton.setForeground(getApplicationContext().getDrawable(R.drawable.ic_volume_up_24px));
        else
            inGameMusicButton.setForeground(getApplicationContext().getDrawable(R.drawable.ic_volume_off));


        Button SoundButton = findViewById(R.id.SoundButton);
        if(OptionsSave.getSoundEffects())
            SoundButton.setForeground(getApplicationContext().getDrawable(R.drawable.ic_music_note_24px));
        else
            SoundButton.setForeground(getApplicationContext().getDrawable(R.drawable.ic_music_off));


        SeekBar NumberOfUndos = findViewById(R.id.NumberOfUndos);
        NumberOfUndos.setProgress(OptionsSave.getNumUndos());


        NumberOfUndos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                OptionsSave.setNumUndos(progress);
                JSON.saveJSON(getApplicationContext(), OptionsSave, Constants.FileNames.OPTIONS);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }//onCreate


    /**
     * Go to last intent
     * @param v
     */
    public void BackButton(View v) {
        finish();
    }//BackButton

    /**
     * Go through all files deleting them
     * Set the back button to invisible (Game should not exist anymore)
     * @param v
     */
    public void DeleteEverythingButton(View v) {
        for(int i = 0; i < Constants.FileNames.FNAMES.length; i++) {
            IO.deleteFile(getApplicationContext(), Constants.FileNames.FNAMES[i]);
        }
        Button backButton = findViewById(R.id.BackButton);
        backButton.setVisibility(View.INVISIBLE);
    }

    /**
     * Set this button to invisible
     * Set confimation button to visible
     * @param v
     */
    public void getConfirmation(View v){
        Button confirmation = findViewById(R.id.Confirmation);
        confirmation.setVisibility(View.VISIBLE);
        v.setVisibility(View.INVISIBLE);
    }

    /**
     * Create an intent to go to MainActivity.class
     * Start an activity based on that intent
     * @param v
     */
    public void HomeButton(View v) {
        Intent GoHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoHome);
    }//HomeButton


    public void soundButton(View v){
        OptionsSave.setSoundEffects(!OptionsSave.getSoundEffects());
        JSON.saveJSON(getApplicationContext(), OptionsSave, Constants.FileNames.OPTIONS);
        if(!OptionsSave.getSoundEffects())
            v.setForeground(getApplicationContext().getDrawable(R.drawable.ic_music_off));
        else
            v.setForeground(getApplicationContext().getDrawable(R.drawable.ic_music_note_24px));
    }

    public void musicButton(View v){
        OptionsSave.setSound(!OptionsSave.getSound());
        JSON.saveJSON(getApplicationContext(), OptionsSave, Constants.FileNames.OPTIONS);
        if(!OptionsSave.getSound())
            v.setForeground(getApplicationContext().getDrawable(R.drawable.ic_volume_off));
        else
            v.setForeground(getApplicationContext().getDrawable(R.drawable.ic_volume_up_24px));

    }

}


