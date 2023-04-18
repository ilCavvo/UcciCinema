package com.example.provabottomnav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Film> mNames=new ArrayList<Film>();
    // private ArrayList<String> mImageUrls= new ArrayList<>();
    private Context mContext;


    public GridViewAdapter(ArrayList<Film> mNames, Context mContext) {
        this.mNames = mNames;
        this.mContext=mContext;
    }


    @Override
    public int getCount() {
        return mNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.filmview, null);
        }
        ImageView image=view.findViewById(R.id.TrendFilmImage);
        Picasso.get().load(mNames.get(i).getImmagine()).into(image);
        TextView titolo=view.findViewById(R.id.TrendFilmTitle);
        titolo.setText(mNames.get(i).getTitolo());

        ImageView locandina = view.findViewById(R.id.TrendFilmImage);


        locandina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","hai premuto un pulsante");

                Intent e = new Intent(mContext, FilmInfo.class);
                //Intent e = new Intent(mContext, CinemaInfo.class);
                mContext.startActivity(e);

            }
        });


        return view;}
}



