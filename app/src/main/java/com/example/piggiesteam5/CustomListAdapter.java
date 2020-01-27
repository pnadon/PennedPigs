/*
 * CustomListAdapter.java
 *
 * Passes data from the ScoreBoard datastructure,
 * to the list in the ScoreBoardActivity activity
 * author: Philippe Nadon
 * date: Nov 28, 2019
 * last modified date: Dec, 4, 2019
 */
package com.example.piggiesteam5;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.piggiesteam5.datastructures.ScoreBoard;

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    private final ScoreBoard scoreBoard;

    public CustomListAdapter(Activity context, ScoreBoard scoreBoard){

        super(context,R.layout.listview_row , scoreBoard.getScores());

        this.context = context;
        this.scoreBoard = scoreBoard;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        TextView nameView = rowView.findViewById(R.id.nameView);
        TextView scoreView = rowView.findViewById(R.id.scoreView);
        TextView dateView = rowView.findViewById(R.id.dateView);

        nameView.setText(scoreBoard.getScore(position).name());
        scoreView.setText(Integer.toString(scoreBoard.getScore(position).score()));
        dateView.setText(scoreBoard.getScore(position).stringDate());

        return rowView;
    }
}
