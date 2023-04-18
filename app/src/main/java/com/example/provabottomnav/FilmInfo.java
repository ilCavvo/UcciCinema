package com.example.provabottomnav;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.provabottomnav.Classibase.Film;

import java.util.ArrayList;

public class FilmInfo extends AppCompatActivity {

    Button back;
    private ArrayList<Film> titoliFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        titoliFilm =new ArrayList<>();
        getFilm();
        initLayoutOrizzonatale();


        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //
        // vedi questo video per inserire bottone back in alto a sinistra
        // https://www.youtube.com/watch?v=FcPUFp8Qrps
        //https://www.youtube.com/watch?v=OpK1F3Kf1uU
    }

    private void initLayoutOrizzonatale(){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.idRecycle2);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(titoliFilm,this);
        recyclerView.setAdapter(adapter);

    }

    private void getFilm() {

    }
}