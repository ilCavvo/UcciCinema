package com.example.provabottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.provabottomnav.Classibase.DBHandler;
import com.example.provabottomnav.Classibase.Film;
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
    Cinema cinema;
    ImageView logoMaps;
    private ArrayList<Film> films=new ArrayList<Film>();
    private ArrayList<Film> preferiti;
    private int ispreferito=0;
    private ArrayList<Integer> id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        //RECUPERO I DATI MANDATI DAL ACTIVITY CHIAMANTE E INIZIALIZZO
        // I VARI ELEMENTI DEL LAYOUT
        int listPositon=intent.getIntExtra("LIST_POSITION", 0);
        cinema = intent.getParcelableExtra("cinema");
        nome=findViewById(R.id.nomeFilm);
        indirizzo=findViewById(R.id.indirizzo);
        telefono=findViewById(R.id.telefono);
        orario=findViewById(R.id.orario);
        numSale=findViewById(R.id.numSale);
        logoMaps=findViewById(R.id.logoMaps);
        nome.setText(cinema.getName());
        telefono.setText("INDIRIZZO \n" + cinema.getTelefono());
        indirizzo.setText("TELEFONO \n" + cinema.getIndirizzo());
        orario.setText("ORARIO \n" +
                       "- Dal lunedì al mercoledì: 20.00/21.30 \n" +
                       "- Giovedì e venerdì: 17.00/21.30 \n" +
                       "- Sabato: 17.00/23.00 \n" +
                       "- Domenica: 15.30/21.30");
        numSale.setText("NUMERO SALE: " + cinema.getCinemaRoomsNumber());
        //LISTENER SUL LOGO DI MAPS DEL LAYOUT CHE APPENA PREMUTO APRE L APPLICAZIONE
        //DI GOOGLE MAPS CON IL TRAGITTO DALLA PROPRIA POSIZIONE AL CINEMA  SCELTO
        logoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?saddr=" + 44.71400756615368 + "," + 10.622492806291696 + "&daddr=" + cinema.getLatitudine() + "," + cinema.getLongitudine();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


        getIdPreferiti();
        getFilm();
    }
    private void getIdPreferiti() {
        preferiti =new ArrayList<Film>();
        id=new ArrayList<Integer>();

        DBHandler dbHandler = new DBHandler(this);

        // getting our course array
        // list from db handler class.
        preferiti = dbHandler.readCourses();
        for(Film preferito: preferiti){

            id.add(preferito.getIdfilm());
        }
    }
    private void initLayoutOrizzonatale(){
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
                                        Log.d("value is",String.valueOf(film));
                                        for (int i = 0; i < film.length(); i++) {
                                            JSONObject e = film.getJSONObject(i);
                                            if(cinema.getFilmid().contains(e.getInt("idFilm"))){
                                              {int idfilm = e.getInt("idFilm");
                                                  if(id.contains(idfilm)){
                                                      ispreferito=1;
                                                  }else{ispreferito=0;}
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
                                              newFilm.preferiti=ispreferito;
                                              films.add(newFilm);
                                                  Log.e("JASON_TEST", String.valueOf(films.size()));}



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


}