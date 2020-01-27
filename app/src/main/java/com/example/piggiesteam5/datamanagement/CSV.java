/*
 * CSV.java
 *
 * A library of methods for saving and loading data to CSV files
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datamanagement;

import android.content.Context;

import com.example.piggiesteam5.Constants;
import com.example.piggiesteam5.datastructures.Score;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Library that holds methods for loading and storing CSVs
 */
public class CSV {

    /**
     * Saves an ArrayList of Scores as a CSV file
     *
     * @param context Android context
     * @param scores  the list of scores
     * @param fName   the filename where the CSV will be saved
     * @return true if successful
     */
    public static boolean saveScoresToCSV(Context context, ArrayList<Score> scores, String fName) throws ParseException, IOException {
        try (
                OutputStream outputStream = new FileOutputStream(new File(context.getFilesDir() + "/" + fName));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT
                        .withHeader(
                                Constants.SCORE_BOARD_HEADERS[0],
                                Constants.SCORE_BOARD_HEADERS[1],
                                Constants.SCORE_BOARD_HEADERS[2]))
        ) {
            for (Score score : scores) {
                csvPrinter.printRecord(score.name(), score.score(), score.date());
            }
            csvPrinter.flush();
            return false;
        }
    }

    /**
     * Loads an ArrayList of Scores from a CSV file
     *
     * @param context Android context
     * @param fName   The filename where the CSV is located
     * @return An ArrayList of Scores
     */
    public static ArrayList<Score> loadCSVToScores(Context context, String fName) throws IOException, ParseException {
        ArrayList<Score> scores = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy");
        InputStream inputStream = new FileInputStream(
                new File(context.getFilesDir() + "/" + fName));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord csvRecord : csvParser) {
            scores.add(new Score(
                    csvRecord.get("name"),
                    Long.parseLong(csvRecord.get("score")),
                    dateFormatter.parse(csvRecord.get("date"))
            ));
        }
        return scores;
    }
}