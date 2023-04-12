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

    private String[] item = {"Abruzzo", "Basilicata", "Calabria", "Campania", "Emilia Romagna",
            "Friuli Venezia Giulia", "Lazio", "Liguria", "Lombardia", "Marche",
            "Molise", "Piemonte", "Puglia", "Sardegna", "Sicilia", "Toscana",
            "Trentino Alto Adige", "Umbria", "Val d'Aosta", "Veneto"};


    public ListView cinemaListView;

    public ArrayList<String> personeArrayList;

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_cinema, container, false);
        super.onCreate(savedInstanceState);


        autoCompleteTextView = view.findViewById(R.id.auto_complete_textview);

        adapterItem = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, item);

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


        ArrayList<String> nomi = new ArrayList<>();
        nomi.add("Alessia");
        nomi.add("Alessia 2");
        nomi.add("Alessia 3");
        nomi.add("Alessia 4");
        nomi.add("Alessia 5");
        nomi.add("Alessia 6");
        nomi.add("Alessia 7");
        nomi.add("Alessia 8");
        nomi.add("Alessia 9");
        nomi.add("Alessia 10");
        nomi.add("Alessia 11");
        nomi.add("Alessia 12");
        nomi.add("Alessia 13");
        nomi.add("Alessia 14");
        nomi.add("Alessia 15");
        nomi.add("Alessia 16");

        // Adattatore
        ArrayAdapter<String> personaAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1,
                nomi);

        cinemaListView.setAdapter(personaAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter,
                                    View view,
                                    int position, long id) {
                Log.d("ONITEMCLICK", "ID: " + id);
            }
        };
        cinemaListView.setOnItemClickListener(clickListener);
    }

}
