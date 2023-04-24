package com.example.provabottomnav;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.CinemaAdapter;
import com.example.provabottomnav.Classibase.Region;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CinemaFragment extends Fragment {

    private ArrayList<Cinema> cinemas = new ArrayList<>();
    private ArrayList<Cinema> cinemasForRegion = new ArrayList<>();
    ArrayList<Integer> listafilm;
    JSONArray array;

    public ListView cinemaListView;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_cinema, container, false);
        super.onCreate(savedInstanceState);

///prendo il menu a tendina per la lista di cinema
        autoCompleteTextView = view.findViewById(R.id.auto_complete_textview);
//inizializzo la lista delle regioni da mettere nel menu a tendina
        adapterItem = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, Region.getRegionStringArray());
//metto la lista nel menu a tendina
        autoCompleteTextView.setAdapter(adapterItem);


        //metto un click listener per le regini che quando clicco mi resizano la lista mettendo solo
        //i cinema della regione scelta
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cinemasForRegion.clear();
                String item = adapterView.getItemAtPosition(position).toString();
                for(Cinema cinema:cinemas){
                    {
                        if(cinema.getRegion().equals(item)){
                            cinemasForRegion.add(cinema);}
                    }
                }
                configureListView(cinemasForRegion);

            }
        });
        // Recuperiamo un reference a un widget attraverso il suo id
        cinemaListView = view.findViewById(R.id.cinemaListView);

        createNewThread();
        return view;
    }



    private void configureListView(ArrayList<Cinema> cinemasForRegion) {

        // Adattatore
        CinemaAdapter cinemaAdapter = new CinemaAdapter (this.getContext(),
                R.layout.listacinema, cinemasForRegion);

        cinemaListView.setAdapter(cinemaAdapter);
//SETTO IL LISTENER CHE APRIRA LA ACTIVITY CINEMA INFO CON I DATI DEL CINEMA E FILM IN PROIEZIONE IN ESSO
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter,
                                    View view,
                                    int position, long id) {
                Intent intent=new Intent(getContext(), CinemaInfo.class);
                intent.putExtra("LIST_POSITION", position);
                intent.putExtra("cinema", cinemasForRegion.get(position));
                startActivity(intent);

            }
        };
        cinemaListView.setOnItemClickListener(clickListener);
    }

    public void createNewThread() {
//inizializzo il thread di caricamento cinema
        Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();

        mSingleThreadExecutor.execute(setRunnable());
    }

    private Runnable setRunnable() {

        Runnable runnable = new Runnable() {
            @Override
            public void run()  {
                //ACCEDO AL FIREBASE DA CUI PRENDEREMO I NOSTRI DATI
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("CINEMA");
                Log.d("TAG", "Value is: " + myRef);
                //CARICO I DATI CHE SONO IN FORMATO JSON
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Random rand = new Random();
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Map<String , List<Object>> map = (Map<String ,List<Object>>) dataSnapshot.getValue();
                        if (map != null) {
                            try {
                                //1.
                                JSONObject jsonObj = new JSONObject(map);
                                JSONArray js = jsonObj.names();
                                for(int j=0;j<js.length();j++)    {
                                    JSONArray cinema = jsonObj.getJSONArray(String.valueOf(js.get(j)));
                                    for (int i = 0; i < cinema.length(); i++) {
                                        JSONObject e = cinema.getJSONObject(i);
                                        String nome = e.getString("nome");
                                        int numSale = e.getInt("numSale");
                                        String telefono = e.getString("telefono");
                                        String indirizzo = e.getString("indirizzo");
                                        String regione =e.getString("regione");
                                        Double latitudine = e.getDouble("latitudine");
                                        Double longitudine = e.getDouble("longitudine");
                                        int integer=0;
                                        listafilm=new ArrayList<>();
                                        array=e.getJSONArray("proiezione");
                                        for(int var=0;var< array.length();var++){
                                            int id= array.getInt(var);
                                            listafilm.add(id);
                                        }
                                        Cinema newCinema = new Cinema(nome, numSale, telefono, indirizzo, regione,listafilm,latitudine,longitudine);
                                        cinemas.add(newCinema);

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
                        configureListView(cinemas);

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
}