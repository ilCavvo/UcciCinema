package com.example.provabottomnav;

import androidx.annotation.NonNull;
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

import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.Film;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    private ArrayList<Cinema> cinemas = new ArrayList<>();
    ArrayList<Integer> listafilm;
    JSONArray array;
Film film;
    private ArrayList<Film> titoliFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        int listPositon=intent.getIntExtra("LIST_POSITION", 0);
        film= intent.getParcelableExtra("film");
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
        getcinemas();


    }

    private void getcinemas() {

        Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();

        mSingleThreadExecutor.execute(setRunnable());
    }

    private Runnable setRunnable() {

        Runnable runnable = new Runnable() {
            @Override
            public void run()  {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("CINEMA");
                Log.d("TAG", "Value is: " + myRef);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Random rand = new Random();
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Map<String , List<Object>> map = (Map<String ,List<Object>>) dataSnapshot.getValue();
                        Log.d("TAG", "Value is: " + map);
                        Log.d("tag",String.valueOf(dataSnapshot.getValue().getClass() ));
                        if (map != null) {
                            try {
                                //1.
                                JSONObject jsonObj = new JSONObject(map);
                                JSONArray js = jsonObj.names();
                                for(int j=0;j<js.length();j++)    {
                                    JSONArray cinema = jsonObj.getJSONArray(String.valueOf(js.get(j)));
                                    for (int i = 0; i < cinema.length(); i++) {
                                        JSONObject e = cinema.getJSONObject(i);


                                        int integer=0;
                                        listafilm=new ArrayList<>();
                                        array=e.getJSONArray("proiezione");
                                        Log.d("dddd",String.valueOf(array.length()));
                                        for(int var=0;var< array.length();var++){
                                            int id= array.getInt(var);
                                            if(id==film.getIdfilm())
                                            {
                                                String nome = e.getString("nome");
                                                int numSale = e.getInt("numSale");
                                                String telefono = e.getString("telefono");
                                                String indirizzo = e.getString("indirizzo");
                                                String regione =e.getString("regione");
                                                Double latitudine = e.getDouble("latitudine");
                                                Double longitudine = e.getDouble("longitudine");
                                                for(int d=0;d< array.length();d++){
                                                    int dato= array.getInt(d);
                                                    Log.d("dddd",String.valueOf(dato));
                                                    listafilm.add(dato);
                                                }
                                                Cinema newCinema = new Cinema(nome, numSale, telefono, indirizzo, regione,listafilm,latitudine,longitudine);
                                            cinemas.add(newCinema);
                                            break;}
                                        }
                                        Log.d("componenti lista",String.valueOf(listafilm.size()));


                                    }}
                                for (int i = 0; i < cinemas.size(); i++) {
                                    Log.d("tag" + String.valueOf(i), cinemas.get(i).toString());

                                }

                            } catch (final JSONException e) {
                                Log.e("JASON_TEST", "Json parsing error 1: " + e.getMessage());


                            }
                        } else {
                            Log.e("JASON_TEST", "Couldn't get json from server.");


                        }
                        initLayoutOrizzonatale();

                        Log.e("JASON_TEST", String.valueOf(cinemas.size()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });

            }
        };
        return runnable;
    }

    private void initLayoutOrizzonatale(){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.idRecycle2);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterCinemas adapter=new RecycleViewAdapterCinemas(cinemas,this);
        recyclerView.setAdapter(adapter);

    }

}