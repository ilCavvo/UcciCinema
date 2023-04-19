package com.example.provabottomnav;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmInfo extends AppCompatActivity {

    Button back;
    TextView titolo;
    TextView cast;
    TextView registi;
    TextView trama;
    ImageView locandina;
    private ArrayList<Film> titoliFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        int listPositon=intent.getIntExtra("LIST_POSITION", 0);
        Film film = intent.getParcelableExtra("film");
        titolo=findViewById(R.id.indirizzo);
        cast=findViewById(R.id.orario);
        registi=findViewById(R.id.telefono);
        trama=findViewById(R.id.trama);
        locandina=findViewById(R.id.locandina);
        cast.setText("ATTORI: "+film.getAttori());
        titolo.setText(film.getTitolo());
        registi.setText("REGISTI: "+film.getRegisti());
        trama.setText(film.getTrama());
        Picasso.get().load(film.getImmagine()).into(locandina);
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