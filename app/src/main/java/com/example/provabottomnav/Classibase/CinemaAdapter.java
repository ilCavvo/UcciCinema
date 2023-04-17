package com.example.provabottomnav.Classibase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.provabottomnav.R;

import java.util.List;

public class CinemaAdapter extends ArrayAdapter<Cinema> {
    private int layoutId;

    public CinemaAdapter(Context context,
                          int layoutId,
                          List<Cinema> objects)
    {
        super(context, layoutId, objects);

        this.layoutId=layoutId;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent)
    {Log.d("tag","sei nel adapter");
        //LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Cinema cinema=getItem(position);

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listacinema, null);
        }

        TextView nomecinema=convertView.findViewById(R.id.NomeCinema);


        Log.d("tag",cinema.getName());
        nomecinema.setText(cinema.getName());


        return convertView;
    }


}
