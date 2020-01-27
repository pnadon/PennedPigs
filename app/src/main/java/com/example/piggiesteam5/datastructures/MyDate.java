/*
 * MyDate.java
 *
 * Simplifies parsing a String into a new Date
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */
package com.example.piggiesteam5.datastructures;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Library containing methods for parsing a String into a Date
 */
public class MyDate {

    /**
     * @param strDate A String representation of a Date
     * @return The Date represented by the String
     */
    public static Date StringToDate(String strDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy");
        Date date = null;
        try {
            dateFormatter.parse(strDate);
            date = dateFormatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param date The Date object to be converted to a String
     * @return A String representation of the date parameter
     */
    public static String DateToString(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy");
        return dateFormatter.format(date);
    }

    /**
     * @return the current Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }
}
