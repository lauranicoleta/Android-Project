package com.example.proiect_recuperare_pop_lauranicoleta;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.proiect_recuperare_pop_lauranicoleta.data.Departament;
import com.example.proiect_recuperare_pop_lauranicoleta.data.Facultate;

import java.util.List;

public class Adapter extends ArrayAdapter {
    public Adapter(@NonNull Context context,@NonNull List<Object> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element_lista, parent, false);
        }
        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvDenumire = convertView.findViewById(R.id.tvDenumire);

        if (getItem(position) instanceof Facultate) {
            Facultate facultate = (Facultate) getItem(position);
            tvId.setText(String.valueOf(facultate.getId()));
            tvDenumire.setText(facultate.getDenumire());
        } else {
            Departament departament = (Departament) getItem(position);
            tvId.setText(String.valueOf(departament.getId()));
            tvDenumire.setText(departament.getDenumire());
        }
        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#00D2FF"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#9874a7"));
        }
        return convertView;
    }
}
