package com.example.aidan.mathfunfacts;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ali on 4/12/2016.
 */

public class CustomAdapterByRating extends ArrayAdapter<ParserMathFunFact> {
    public CustomAdapterByRating(Context context, ArrayList<ParserMathFunFact> MathFunFacts) {
        super(context, R.layout.custom_row, MathFunFacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ListInflater = LayoutInflater.from(getContext());
        View customView = ListInflater.inflate(R.layout.custom_row_by_rating, parent, false);

        String title = getItem(position).getTitle();
        String level = getItem(position).getLevel().toLowerCase();
        TextView titleListElement = (TextView) customView.findViewById(R.id.titleMFF);
        titleListElement.setText(title+ "  (" +String.valueOf(getItem(position).getRating()) +" Stars)");


        //set the rating
        //RatingBar ratingBar = (RatingBar) customView.findViewById(R.id.ratingBarOfList);
        //ratingBar.setRating(getItem(position).getRating());
        TextView rating = (TextView) customView.findViewById(R.id.rating);
        rating.setText("");
        //String.valueOf(getItem(position).getRating())

        //set the color depending on the difficulty
        if(level.equals("1")) {
            //easy
            titleListElement.setBackgroundColor(Color.rgb(255,255,136));
        }
        else if (level.equals("2")) {
            //medium
            titleListElement.setBackgroundColor(Color.rgb(255,116,0));
        }
        else {
            // hard
            titleListElement.setBackgroundColor(Color.rgb(255,26,0));

        }

        return customView;
    }
}