package com.example.provabottomnav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


public class FilmFragment extends Fragment implements View.OnClickListener{


    private ArrayList<String> titoliTrendFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();
    private ArrayList<String> titoliFilm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_film, container, false);
        super.onCreate(savedInstanceState);

        ///PER IL FULLSCREEN DELL APP SENZA IL NOME DEL PROGETTO
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // INIZIALIZZO LISTE FILM
        titoliTrendFilm =new ArrayList<>();
        titoliFilm =new ArrayList<>();
        getTrendFilms();
        initLayoutOrizzonatale(view);
        getFilmAdd();
        initGridLayout(view);

        return view;
    }
    private void getFilmAdd(){
        titoliFilm.add("doc strange 2");

        titoliFilm.add("super mario");

        titoliFilm.add("me contro te");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("super mario");

        titoliFilm.add("me contro te");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("doc strange 2");

        titoliFilm.add("doc strange 2");

    }
    private void getTrendFilms(){
        //POPOLO LE LISTE PER I TITOLI DEI FILM IN IN TREND
        locandineTrendFilm.add(String.valueOf(R.drawable.img_switcher));
        titoliTrendFilm.add("doc strange 2");
        locandineTrendFilm.add(String.valueOf(R.drawable.immaginemoschettieri));
        titoliTrendFilm.add("me contro te");
        locandineTrendFilm.add(String.valueOf(R.drawable.locandina));
        titoliTrendFilm.add("doc strange 2");

        locandineTrendFilm.add(String.valueOf(R.drawable.img_switcher__1_));
        titoliTrendFilm.add("doc strange 2");
        locandineTrendFilm.add(String.valueOf(R.drawable.super_mario_bros_il_film_zlqbhbt));
        titoliTrendFilm.add("doc strange 2");



        Log.i("mess","sei dentro");


    }

    private void initLayoutOrizzonatale(View view){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=view.findViewById(R.id.TrendFilmLayoutO);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(titoliTrendFilm,locandineTrendFilm,this.getContext());
        recyclerView.setAdapter(adapter);

    }


    private void initGridLayout(View view){
        Log.i("GETCOUNTS", String.valueOf(titoliFilm.size()));
        GridViewAdapter gridadapter= new GridViewAdapter(titoliFilm, this.getContext());
        GridView gridView=view.findViewById(R.id.AltriFilmLayout);
        gridView.setAdapter(gridadapter);
        //BLOCCO LA SCROLL VIEW COSI QUANDO IO SCROLLO LA GRID VIEW NON SI MUOVE IL LAYOUT INTERO
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });
    }

    @Override
    public void onClick(View view) {

    }
}