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


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewAdapterTrendFilms extends RecyclerView.Adapter<RecycleViewAdapterTrendFilms.ViewHolder>{
    private ArrayList<Film> titoli =new ArrayList<Film>();

    private Context mContext;


    public RecycleViewAdapterTrendFilms(ArrayList<Film> mNames, Context mContext) {
        this.titoli = mNames;
        this.mContext=mContext;
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
        int i=position;
        holder.name.setText(titoli.get(position).getTitolo());
        Glide.with(mContext).load(titoli.get(i).getImmagine()).override(Target.SIZE_ORIGINAL).into(holder.image);

      //  Picasso.get().load(titoli.get(position).getImmagine()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","hai premuto un pulsante");

                Intent e = new Intent(mContext, FilmInfo.class);
                e.putExtra("LIST_POSITION",i );
                e.putExtra("film", titoli.get(i));
                e.putExtra("listafilm",titoli);

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
            image = itemView.findViewById(R.id.ImageCinema);
            name = itemView.findViewById(R.id.TrendFilmTitle);

        }
    }
}
