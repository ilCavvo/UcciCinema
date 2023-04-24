package com.example.provabottomnav;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.DBHandler;
import com.example.provabottomnav.Classibase.Film;
import com.example.provabottomnav.Classibase.Region;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class FilmFragment extends Fragment implements View.OnClickListener{

    DBHandler dbHandler;

    private ArrayList<Film> films=new ArrayList<Film>();
    private ArrayList<Film> filmtrend=new ArrayList<Film>();
    private ArrayList<Film> preferiti=new ArrayList<Film>();
    private int isPreferito=0;
    GridView gridView;
    View view;
    private ArrayList<Integer>id=new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_film, container, false);
        super.onCreate(savedInstanceState);


        ///PER IL FULLSCREEN DELL APP SENZA IL NOME DEL PROGETTO
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // INIZIALIZZO LISTE FILM

        getIdPreferiti(view);
      //  getFilmAdd(view);

        //initGridLayout(view);

        return view;
    }

    private void getIdPreferiti(View view) {
        dbHandler = new DBHandler(view.getContext());

        // getting our course array
        // list from db handler class.
        preferiti = dbHandler.readCourses();
        for(Film preferito:preferiti){
            Log.d("idpreferiti", String.valueOf(preferito.getIdfilm()));
            id.add(preferito.getIdfilm());
        }
        getFilmAdd(view);
    }

    private void getFilmAdd(View view){
        Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();

        mSingleThreadExecutor.execute(setRunnable( view ));
    }

    private Runnable setRunnable(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run()  {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("FILM");
                Log.d("TAG", "Value is: " + myRef);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                    JSONArray film = jsonObj.getJSONArray(String.valueOf(js.get(j)));
                                    for (int i = 0; i < film.length(); i++) {
                                        JSONObject e = film.getJSONObject(i);
                                        int idfilm = e.getInt("idFilm");
                                        if(id.contains(idfilm)){
                                            isPreferito=1;
                                        }else{isPreferito=0;}
                                        Log.d("ispreferito", String.valueOf(isPreferito));
                                        String anno = e.getString("anno");
                                        String cast = e.getString("cast");
                                        String durata = e.getString("durata");
                                        String genere = e.getString("genere");

                                        String immagine = e.getString("immagine");
                                        String paese = e.getString("paese");
                                        String regista = e.getString("regista");
                                        String titolo = e.getString("titolo");
                                        String trama = e.getString("trama");
                                        String trailer = e.getString("trailer");


                                        Film newFilm = new Film(idfilm,immagine,anno,durata,genere,paese,titolo,regista,cast,trama,trailer);

                                        newFilm.preferiti=isPreferito;
                                        if(i<=5)
                                        {
                                            filmtrend.add(newFilm);

                                        }else{
                                       films.add(newFilm);}

                                    }}
                                for (int i = 0; i < films.size(); i++) {
                                    Log.d("tag" + String.valueOf(i), films.get(i).toString());

                                }

                            } catch (final JSONException e) {
                                Log.e("JASON_TEST", "Json parsing error 1: " + e.getMessage());


                            }
                        } else {
                            Log.e("JASON_TEST", "Couldn't get json from server.");


                        }
                        initLayoutOrizzonatale(view);
                       initGridLayout(view);

                        Log.e("JASON_TEST", String.valueOf(films.size()));
                        Log.e("JASON_TEST", String.valueOf(filmtrend.size()));
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


    private void initLayoutOrizzonatale(View view){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=view.findViewById(R.id.TrendFilmLayoutO);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(filmtrend, this.getContext());
        recyclerView.setAdapter(adapter);

    }


    private void initGridLayout(View view){
        //Log.i("GETCOUNTS", String.valueOf(titoliFilm.size()));
        GridViewAdapter gridadapter= new GridViewAdapter(films, this.getContext());
         gridView=view.findViewById(R.id.AltriFilmLayout);
        Log.d("landscape:",String.valueOf(view.getResources().getConfiguration().orientation));
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
    public void onConfigurationChanged(Configuration newConfig) {
        gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 7 : 4);
        initGridLayout(view);
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onClick(View view) {

    }
}