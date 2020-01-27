/*
 * Score.java
 *
 * Object which defines an instance of a Score
 * A Score holds data for the player's name, score achieved, and date achieved.
 * author: Philippe Nadon
 * date: Nov 9, 2019
 */

package com.example.piggiesteam5.datastructures;

import java.util.Date;

import static com.example.piggiesteam5.datastructures.MyDate.DateToString;

/**
 * Represents a single score, which is related to a player's game result
 */
public class Score {
    private String name;
    private long score;
    private Date date;

    public Score(String name, long score, Date date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    /**
     * @return the name of the player who received that score
     */
    public String name() {
        return this.name;
    }

    /**
     * @return the score of the player who received that score, -1 if overflow
     */
    public int score() {
        if(this.score > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)this.score;
    }

    /**
     * @return the date when the player received that score
     */
    public Date date() {
        return this.date;
    }

    public String stringDate() { return DateToString(this.date); }

    @Override
    public String toString() {
        return "Score: {name=" + this.name + ", score=" + this.score + ", date=" + this.date + "}";
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("comparison:");
        System.out.println(this);
        System.out.println(o);
        if( getClass() != o.getClass()) {
            return false;
        }
        if( this == o) {
            return true;
        }
        Score otherScore = (Score) o;
        return (name.equals(otherScore.name) &&
                (score - otherScore.score == 0) &&
                (date.equals(otherScore.date))
        );
    }

    @Override
    public int hashCode() {
        int result = (int) (score ^ (score >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
