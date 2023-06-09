package com.example.provabottomnav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.provabottomnav.Classibase.DBHandler;
import com.example.provabottomnav.Classibase.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PreferitiAdapter extends BaseAdapter {
    private ArrayList<Film> mNames=new ArrayList<Film>();
    private ArrayList<Film> preferiti=new ArrayList<Film>();
    // private ArrayList<String> mImageUrls= new ArrayList<>();
    private Context mContext;


    public PreferitiAdapter(ArrayList<Film> mNames, Context mContext) {
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
Log.d("sei nei preferiti","eco");
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.preferitiview, null);
        }
        ImageView image=view.findViewById(R.id.ImageCinema);
        Glide.with(view.getContext()).load(mNames.get(i).getImmagine()).override(Target.SIZE_ORIGINAL).into(image);

      //  Picasso.get().load(mNames.get(i).getImmagine()).into(image);
        TextView titolo=view.findViewById(R.id.titoloFilm);
        titolo.setText(mNames.get(i).getTitolo());

        ImageView locandina = view.findViewById(R.id.ImageCinema);
        ImageView star=view.findViewById(R.id.star);
        star.setImageResource(R.drawable.star_pieno);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(view.getContext());
                preferiti = dbHandler.readCourses();
                dbHandler.deleteElement(mNames.get(i));
                star.setImageResource(R.drawable.star_vuoto);
                preferiti.remove(mNames.get(i));
                Toast.makeText(view.getContext(),"Film rimosso dai preferiti",Toast.LENGTH_LONG).show();
                refresh(preferiti);
            }
        });


        locandina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","hai premuto un pulsante");

                Intent e = new Intent(mContext, FilmInfo.class);
                e.putExtra("LIST_POSITION",i );
                e.putExtra("listafilm",mNames);
                e.putExtra("film", mNames.get(i));

                mContext.startActivity(e);

            }
        });


        return view;}
    public void refresh(ArrayList<Film> preferiti)
    {
        mNames.clear();
        mNames.addAll(preferiti);
        this.notifyDataSetChanged();
    }
}




