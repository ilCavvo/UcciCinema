package com.example.provabottomnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;


public class CinemaFragment extends Fragment {

    public ListView cinemaListView;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_cinema, container, false);
        super.onCreate(savedInstanceState);


        autoCompleteTextView = view.findViewById(R.id.auto_complete_textview);

        adapterItem = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, Region.getRegionStringArray());

        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
            }
        });
        // Recuperiamo un references a un widget attraverso il suo id
        cinemaListView = view.findViewById(R.id.cinemaListView);

        configureListView();

        return view;
    }


    private void configureListView() {
        Log.i("ciao", "ciao");

        // Adattatore
        ArrayAdapter<Region> cinemaAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1, Region.values());

        cinemaListView.setAdapter(cinemaAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter,
                                    View view,
                                    int position, long id) {
                Log.d("OnItemClick", "ID: " + id);
            }
        };
        cinemaListView.setOnItemClickListener(clickListener);
    }
}
