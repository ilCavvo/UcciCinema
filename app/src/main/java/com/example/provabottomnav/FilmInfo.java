package com.example.provabottomnav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmInfo extends AppCompatActivity {

    Button back;
    TextView durata;

    public TextView nomeFilm;
    TextView anno;
    TextView cast;
    TextView genere;
    TextView regista;
    TextView paese;
    TextView trama;
    ImageView logoyt;
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
        titoliFilm=intent.getParcelableArrayListExtra("listafilm");
        nomeFilm=findViewById(R.id.nomeFilm);
        durata =findViewById(R.id.durata);
        genere =findViewById(R.id.genere);
        anno=findViewById(R.id.anno);
        paese=findViewById(R.id.paese);
        regista=findViewById(R.id.regista);
        cast=findViewById(R.id.cast);
        trama=findViewById(R.id.trama);
        locandina=findViewById(R.id.locandina);
        logoyt=findViewById(R.id.youtube);
        logoyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getTrailer()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(film.getTrailer()));
                try {
                   startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });
        nomeFilm.setText(film.getTitolo());
        durata.setText("DURATA: "+film.getDurata());
        genere.setText("GENERE: "+film.getGenere());
        anno.setText("ANNO: "+film.getAnno());
        paese.setText("PAESE: "+film.getPaese());
        regista.setText("REGISTA: "+film.getRegisti());
        cast.setText("CAST: "+film.getAttori());
        trama.setText("TRAMA: "+film.getTrama());
        Picasso.get().load(film.getImmagine()).into(locandina);
        initLayoutOrizzonatale();

    }

    private void initLayoutOrizzonatale(){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.idRecycle2);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(titoliFilm,this);
        recyclerView.setAdapter(adapter);

    }

}