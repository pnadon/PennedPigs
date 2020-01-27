/*
 * IO.java
 *
 * A library of methods for reading and writing serialized data to files.
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datamanagement;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A library of methods for reading and writing serialized data to files.
 */
public class IO {

    /**
     * @return The device's total storage capacity in Bytes
     */
    static long getTotalMemory() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        return (statFs.getBlockCountLong() * statFs.getBlockSizeLong());
    }

    /**
     * @return The device's total free storage in Bytes
     */
    static long getFreeMemory() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        return (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong());
    }

    /**
     * Saves a String representing a serialized data structure to a file
     *
     * @param context Android context
     * @param fName   the file's name
     * @param content the content of the file
     */
    static void stringToFile(Context context, String fName, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(
                new File(context.getFilesDir() + "/" + fName)
            );
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a file and converts the contents into a String
     *
     * @param context Android context
     * @param fName   the file's name
     * @return The contents of the file
     */
    static String fileToString(Context context, String fName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(
                new File(context.getFilesDir() + "/" + fName)
            );
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream)
            );
            String bufferLine;

            while ((bufferLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferLine);
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * @param context Android context
     * @param fName the file's name
     * @return whether the file exists
     */
    public static boolean fileExists(Context context, String fName) {
        File testFile = new File(context.getFilesDir() + "/" + fName);
        return testFile.exists();
    }

    /**
     * @param context Android context
     * @param fName the file's name
     * @return whether the file was successfully deleted
     */
    public static boolean deleteFile(Context context, String fName) {
        File deleteFile = new File(context.getFilesDir() + "/" + fName);
        return deleteFile.delete();
    }
}
