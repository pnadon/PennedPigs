/*
 * Constants.java
 *
 * holds global constants for the app
 * author: Philippe Nadon
 * date: Nov 28, 2019
 * last modified date: Dec, 4, 2019
 */
package com.example.piggiesteam5;

public class Constants {

    public static class FileNames {
        public static final String SETUP = "SETUP.json";
        public static final String OPTIONS = "OPTIONS.json";
        public static final String SCORE_BOARD = "SCORE_BOARD.json";
        public static final String GAME = "GAME.json";

        public static final String[] FNAMES = {SETUP, OPTIONS, SCORE_BOARD, GAME};
    }

    public static class Board {
        public static final String VERT_PREFIX = "Vert";
        public static final String HORI_PREFIX = "Hori";
        public static final int BOARD_TO_MINES_RATIO = 5;
    }

    public static final String[] SCORE_BOARD_HEADERS = {"name", "score", "date"};

    private Constants(){
        throw new AssertionError();
    }
}
