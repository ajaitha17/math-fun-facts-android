package com.example.aidan.mathfunfacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.xml.sax.Parser;

import java.util.ArrayList;
import java.util.ListIterator;

import static com.example.aidan.mathfunfacts.MainActivity.collection;


public class ListMFFs extends Fragment {

    private static final String TAG = "ListMFFs";

    String whichTab;

    public ListMFFs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list_mffs, container, false);

        Bundle args = getArguments();

        ArrayList<ParserMathFunFact> results = new ArrayList<>();

        //ListMFFs must handle being called by tab3, tab4, and tab5
        //they all do slightly different things to pass their arguments along
        //because the items in the bundle are all different, stored under different
        //keys

        if (args.containsKey("filenames")) {
            whichTab = "tab5";
            ArrayList<String> stringFilenames = args.getStringArrayList("filenames");
            ListIterator iter = stringFilenames.listIterator();
            while(iter.hasNext()) {
                ParserMathFunFact temp = new ParserMathFunFact((String) iter.next(), getContext());
                results.add(temp);
            }


        }

        else if (args.containsKey("subject")) {
            whichTab = "tab4";
            ArrayList<String> stringFilenames = args.getStringArrayList("subject");
            ListIterator iter = stringFilenames.listIterator();
            while(iter.hasNext()) {
                ParserMathFunFact temp = new ParserMathFunFact((String) iter.next(), getContext());
                results.add(temp);
            }
        }

        else {
            whichTab = "tab3";
            results = collection.findMFFWithLevel(args.getString("difficulty"));
        }

        ListAdapter difficultyAdapter;
        difficultyAdapter = new CustomAdapter(this.getContext(), results);
        ListView difficultyListView = (ListView) view.findViewById(R.id.mff_list);
        difficultyListView.setAdapter(difficultyAdapter);

        //set ClickListener to take us to a single fact
        //when selected in the ListView
        difficultyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //call a fragment transaction, put the name of the file we want to
                        //display in the bundle, pass that bundle along, then call
                        //DisplayOneMFF to display the single selected fact, replace
                        //the root fragment depending on which fragment called this fragment
                        //originally

                        FragmentTransaction ft = getFragmentManager().beginTransaction();

                        DisplayOneMFF display = new DisplayOneMFF();
                        Bundle newArgs = new Bundle();
                        ParserMathFunFact MFF = (ParserMathFunFact) parent.getItemAtPosition(position);
                        newArgs.putString("MFFFile", MFF.getFilename());
                        display.setArguments(newArgs);

                        if (whichTab.equals("tab3")) {
                            ft.replace(R.id.difficulty_root, display);
                        }
                        else if (whichTab.equals("tab4")) {
                            ft.replace(R.id.subject_root, display);
                        }
                        else if (whichTab.equals("tab5")) {
                            ft.replace(R.id.search_root, display);
                        }

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                }
        );
        // Inflate the layout for this fragment
        return view;
    }
}
