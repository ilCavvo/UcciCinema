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


import com.example.provabottomnav.Classibase.Cinema;
import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewAdapterCinemas extends RecyclerView.Adapter<RecycleViewAdapterCinemas.ViewHolder>{
    private ArrayList<Cinema> cinema =new ArrayList<Cinema>();

    private Context mContext;


    public RecycleViewAdapterCinemas(ArrayList<Cinema> mNames, Context mContext) {
        this.cinema = mNames;
        this.mContext=mContext;
    }



    ///CREO IL LAYOUT HOTIZONTALVIEW
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cinemaview,parent,false);

        return new ViewHolder(view);

    }
    //MODIFICO IL LAYOUT CREATO
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i =position;
        holder.name.setText(cinema.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","hai premuto un pulsante");

                Intent e = new Intent(mContext, CinemaInfo.class);
                e.putExtra("LIST_POSITION",i );
                e.putExtra("cinema", cinema.get(i));
                e.putExtra("listacinema",cinema);

                //Intent e = new Intent(mContext, CinemaInfo.class);
                mContext.startActivity(e);

            }
        });

    }


    // ritorno quanti titoli ci sono
    @Override
    public int getItemCount() {
        return cinema.size();

    }

    //CREO IL VIEW HOLDER CHE MI SERVIRA' PER ACCEDERE AI SINGOLI ELEMENTI DEL LAYOUT
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.CinemaCorrelati);
            image=itemView.findViewById(R.id.ImageCinema);

        }
    }
}
