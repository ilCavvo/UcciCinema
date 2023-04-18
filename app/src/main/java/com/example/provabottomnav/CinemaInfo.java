package com.example.provabottomnav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.provabottomnav.Classibase.Cinema;

import java.util.ArrayList;

public class CinemaInfo extends AppCompatActivity {

    Button back;
    TextView indirizzo;
    TextView nome;
    TextView telefono;
    private ArrayList<String> titoliFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_info);
        Intent intent=getIntent();
        int listPositon=intent.getIntExtra("LIST_POSITION", 0);
        Cinema cinema = intent.getParcelableExtra("cinema");
        nome=findViewById(R.id.nomeCinema);
        indirizzo=findViewById(R.id.indirizzo);
        telefono=findViewById(R.id.telefono);
        titoliFilm =new ArrayList<>();
        nome.setText(cinema.getName());
        telefono.setText(cinema.getTelefono());
        indirizzo.setText(cinema.getIndirizzo());

        titoliFilm =new ArrayList<>();
        getFilm();
       // initLayoutOrizzonatale();

    }

    private void initLayoutOrizzonatale(){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.idRecycle);
        recyclerView.setLayoutManager(layoutManager);
     //   RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(titoliFilm,this);
     //   recyclerView.setAdapter(adapter);

    }

    private void getFilm() {
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
}