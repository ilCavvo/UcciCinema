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

    private ArrayList<Film> films=new ArrayList<Film>();
    private ArrayList<Film> filmtrend=new ArrayList<Film>();
    private DBHandler dbHandler;
    private ArrayList<Integer> id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_preferiti, container, false);
        // Inflate the layout for this fragment
        getFilm(view);
        return view;
    }

    private void getFilm(View view) {
        dbHandler = new DBHandler(view.getContext());
        films = dbHandler.readCourses();
        initGridLayout(view);
    }

    private void initGridLayout(View view) {
        Log.i("GETCOUNTS", String.valueOf(films.size()));
        PreferitiAdapter gridadapter= new PreferitiAdapter(films, this.getContext());
        GridView gridView=view.findViewById(R.id.FilmPreferiti);
        gridView.setAdapter(gridadapter);
    }
}