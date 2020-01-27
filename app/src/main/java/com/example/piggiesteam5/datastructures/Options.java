package com.example.piggiesteam5.datastructures;

import java.util.Arrays;

public class Options {
    private int numUndos;
    private boolean sound;
    private boolean soundEffects;

    public Options(int numUndos, boolean sound, boolean soundEffects){
        this.numUndos = numUndos;
        this.sound = sound;
        this.soundEffects = soundEffects;
    }

    public int getNumUndos(){
        return numUndos;
    }

    public void setNumUndos(int numUndos){
        this.numUndos = numUndos;
    }

    public boolean getSound(){
        return sound;
    }

    public void setSound(boolean sound){
        this.sound = sound;
    }

    public boolean getSoundEffects(){
        return soundEffects;
    }

    public void setSoundEffects(boolean soundEffects){
        this.soundEffects = soundEffects;
    }



    @Override
    public String toString() {
        return "Num Of Undo: " +
                numUndos +
                "Sound: " +
                sound +
                "SoundEffects " +
                soundEffects;
    }
}
