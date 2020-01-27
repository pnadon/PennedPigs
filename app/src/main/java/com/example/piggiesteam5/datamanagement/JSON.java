/*
 * JSON.java
 *
 * Stores and Retrieves Objects stored as JSON Objects
 *
 * author: Philippe Nadon
 * date: Nov 8, 2019
 */
package com.example.piggiesteam5.datamanagement;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Library for handling JSON objects
 */
public class JSON {
    /**
     * Writes an object's instance to file in JSON format
     *
     * @param context Android context
     * @param obj     The object to save
     * @param fName   The filename
     * @return true if successful
     */
    public static boolean saveJSON(Context context, Object obj, String fName) {
//        if( (IO.getFreeMemory() * 100 / IO.getTotalMemory()) < 10) {
//            return false; // less than 10% free space on device
//        }
        Gson gson = new Gson();
        String strObj = gson.toJson(obj);
        if (strObj.getBytes().length > IO.getFreeMemory()) {
            System.out.println("free memory insufficient");
            System.out.println((IO.getFreeMemory() * 100 / IO.getTotalMemory()));// won't fit in free space
            return false;
        }
        IO.stringToFile(context, fName, strObj);
        return true;
    }

    /**
     * Loads an object's instance from file
     *
     * @param context  Android context
     * @param classOfT The class of the object
     * @param fName    The filename where the object is located
     * @return a newly instantiated version of the object, with the fields from the JSON file
     */
    public static <T> T loadJSON(Context context, Class<T> classOfT, String fName) {
        Gson gson = new Gson();
        String json = IO.fileToString(context, fName);
        return gson.fromJson(json, classOfT);
    }
}
