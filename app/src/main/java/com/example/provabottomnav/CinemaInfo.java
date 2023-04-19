package com.example.provabottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.CinemaAdapter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CinemaInfo extends AppCompatActivity {

    Button back;
    TextView indirizzo;
    TextView nome;
    TextView telefono;
    TextView orario;
    TextView numSale;
    private ArrayList<Film> films=new ArrayList<Film>();
    private ArrayList<String> titoliFilm;
    private ArrayList<String> locandineTrendFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        int listPositon=intent.getIntExtra("LIST_POSITION", 0);
        Cinema cinema = intent.getParcelableExtra("cinema");
        nome=findViewById(R.id.nomeCinema);
        indirizzo=findViewById(R.id.indirizzo);
        telefono=findViewById(R.id.telefono);
        orario=findViewById(R.id.orario);
        numSale=findViewById(R.id.numSale);
        titoliFilm =new ArrayList<>();
        nome.setText(cinema.getName());
        telefono.setText("INDIRIZZO \n" + cinema.getTelefono());
        indirizzo.setText("TELEFONO \n" + cinema.getIndirizzo());
        orario.setText("ORARIO \n" +
                       "- Dal lunedì al mercoledì: 20.00/21.30 \n" +
                       "- Giovedì e venerdì: 17.00/21.30 \n" +
                       "- Sabato: 17.00/23.00 \n" +
                       "- Domenica: 15.30/21.30");
        numSale.setText("NUMERO SALE: " + cinema.getCinemaRoomsNumber());


        titoliFilm =new ArrayList<>();
        getFilm();
       // initLayoutOrizzonatale();

    }

    private void initLayoutOrizzonatale(){
        Log.i("mess","sei dentro2");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.idRecycle);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapterTrendFilms adapter=new RecycleViewAdapterTrendFilms(films,this);
        recyclerView.setAdapter(adapter);

    }

    private void getFilm() {

            Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();
            mSingleThreadExecutor = Executors.newSingleThreadExecutor();

            mSingleThreadExecutor.execute(setRunnable());
        }

        private Runnable setRunnable() {

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

                                            String anno = e.getString("anno");
                                            String cast = e.getString("cast");
                                            String durata = e.getString("durata");
                                            String genere = e.getString("genere");
                                            int idfilm = e.getInt("idFilm");
                                            String immagine = e.getString("immagine");
                                            String paese = e.getString("paese");
                                            String regista = e.getString("regista");
                                            String titolo = e.getString("titolo");
                                            String trama = e.getString("trama");


                                            Film newFilm = new Film(idfilm,immagine,anno,durata,genere,paese,titolo,regista,cast,trama);

                                                films.add(newFilm);

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
                            initLayoutOrizzonatale();


                            Log.e("JASON_TEST", String.valueOf(films.size()));

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

    public static class CinemaFragment extends Fragment {

        private ArrayList<Cinema> cinemas = new ArrayList<>();
        private ArrayList<Cinema> cinemasForRegion = new ArrayList<>();
        public ListView cinemaListView;
        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapterItem;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view;

            view = inflater.inflate(R.layout.fragment_cinema, container, false);
            super.onCreate(savedInstanceState);


            autoCompleteTextView = view.findViewById(R.id.auto_complete_textview);

            adapterItem = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, Region.getRegionStringArray());

            autoCompleteTextView.setAdapter(adapterItem);

            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    cinemasForRegion.clear();
                    String item = adapterView.getItemAtPosition(position).toString();
                    Log.d("valore",item);
                    Log.d("valore",String.valueOf(cinemas.size()));
                    for(Cinema cinema:cinemas){
                       {
                           if(cinema.getRegion().equals(item)){
                           Log.d("valore",cinema.toString());
                           cinemasForRegion.add(cinema);}

                        }
                    }
                    Log.d("valore",String.valueOf(cinemasForRegion.size()));
                    configureListView(cinemasForRegion);

                }
            });
            // Recuperiamo un references a un widget attraverso il suo id
            cinemaListView = view.findViewById(R.id.cinemaListView);

            createNewThread();
            return view;
        }
        private void configureListView(ArrayList<Cinema> cinemasForRegion) {
            Log.i("ciao", "ciao");

            // Adattatore
            CinemaAdapter cinemaAdapter = new CinemaAdapter (this.getContext(),
                    R.layout.listacinema, cinemasForRegion);

            cinemaListView.setAdapter(cinemaAdapter);

            AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter,
                                        View view,
                                        int position, long id) {
                    Log.d("OnItemClick", "ID: " + id);
                    Intent intent=new Intent(getContext(), CinemaInfo.class);
                    intent.putExtra("LIST_POSITION", position);
                    intent.putExtra("cinema", cinemas.get(position));
                    startActivity(intent);

                }
            };
            cinemaListView.setOnItemClickListener(clickListener);
        }

        private void configureListView() {
            Log.i("ciao", "ciao");

            // Adattatore
            CinemaAdapter cinemaAdapter = new CinemaAdapter (this.getContext(),
                    R.layout.listacinema, cinemas);

            cinemaListView.setAdapter(cinemaAdapter);

            AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter,
                                        View view,
                                        int position, long id) {
                    Log.d("OnItemClick", "ID: " + id);
                    Intent intent=new Intent(getContext(), CinemaInfo.class);
                    intent.putExtra("LIST_POSITION", position);
                    intent.putExtra("cinema", cinemas.get(position));
                    startActivity(intent);

                }
            };
            cinemaListView.setOnItemClickListener(clickListener);
        }

        public void createNewThread() {

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
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Map<String ,List<Object>> map = (Map<String ,List<Object>>) dataSnapshot.getValue();
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

                                        String nome = e.getString("nome");
                                        int numSale = e.getInt("numSale");
                                        String telefono = e.getString("telefono");
                                        String indirizzo = e.getString("indirizzo");
                                        String regione =e.getString("regione");


                                        Cinema newCinema = new Cinema(nome, numSale, telefono, indirizzo, regione);

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
                            configureListView();

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
}