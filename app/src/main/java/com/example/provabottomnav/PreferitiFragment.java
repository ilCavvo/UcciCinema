package com.example.provabottomnav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.provabottomnav.Classibase.Film;
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class PreferitiFragment extends Fragment {

    private ArrayList<String> titoliTrendFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();
    private ArrayList<Film> films=new ArrayList<Film>();
    private ArrayList<Film> filmtrend=new ArrayList<Film>();
    private ArrayList<String> titoliFilm;

    private ArrayList<Integer> id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_preferiti, container, false);
        // Inflate the layout for this fragment
        getIdPreferiti(view);

        return view;
    }

    private void getIdPreferiti(View view) {
        File currentDir=this.getContext().getFilesDir();
        id=new ArrayList<Integer>();
        try {
            FileInputStream fis = this.getContext().openFileInput("filmpreferito.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("hshf",line.toString());
                id.add(Integer.valueOf(line.toString()));
                Log.d("id",String.valueOf(id.size()));
            }
            getFilm(view);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFilm(View view) {
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
                                                Film newFilm = new Film(idfilm,immagine,anno,durata,genere,paese,titolo,regista,cast,trama,trailer,1);
                                                films.add(newFilm);
                                            }
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

    private void initGridLayout(View view) {
        Log.i("GETCOUNTS", String.valueOf(films.size()));
        GridViewAdapter gridadapter= new GridViewAdapter(films, this.getContext());
        GridView gridView=view.findViewById(R.id.FilmPreferiti);
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
}