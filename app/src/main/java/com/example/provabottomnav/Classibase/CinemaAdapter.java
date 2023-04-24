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
    {
        Cinema cinema=getItem(position);
        // creo il layout da aggiungere alla nostra lista controllando se esiste già
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listacinema, null);
        }
        //setto i valori ai vari componenti del layout che mi servono
        TextView nomecinema=convertView.findViewById(R.id.NomeCinema);
        nomecinema.setText(cinema.getName());
        //ritorno la view per aggiungerla alla lista
        return convertView;
    }


}
