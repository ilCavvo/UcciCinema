package com.example.provabottomnav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecycleViewAdapterTrendFilms extends RecyclerView.Adapter<RecycleViewAdapterTrendFilms.ViewHolder>{
    private ArrayList<String> titoli =new ArrayList<>();
    private ArrayList<String> locandine = new ArrayList<>();
    private Context mContext;


    public RecycleViewAdapterTrendFilms(ArrayList<String> mNames,ArrayList<String> locandine,  Context mContext) {
        this.titoli = mNames;
        this.locandine=locandine;
        this.mContext = mContext;
    }

    public RecycleViewAdapterTrendFilms(ArrayList<String> titoli, Context mContext) {
        this.titoli = titoli;
        this.mContext = mContext;
    }

    ///CREO IL LAYOUT HOTIZONTALVIEW
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.filmview,parent,false);

        return new ViewHolder(view);

    }
//MODIFICO IL LAYOUT CREATO
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(titoli.get(position));
        if(!locandine.isEmpty())
            holder.image.setImageResource(Integer.parseInt(locandine.get(position)));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","hai premuto un pulsante");

                Intent e = new Intent(mContext, FilmInfo.class);
                //Intent e = new Intent(mContext, CinemaInfo.class);
                mContext.startActivity(e);

            }
        });

    }


// ritorno quanti titoli ci sono
    @Override
    public int getItemCount() {
        return titoli.size();

    }

    //CREO IL VIEW HOLDER CHE MI SERVIRA' PER ACCEDERE AI SINGOLI ELEMENTI DEL LAYOUT
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.TrendFilmImage);
            name = itemView.findViewById(R.id.TrendFilmTitle);

        }
    }
}
