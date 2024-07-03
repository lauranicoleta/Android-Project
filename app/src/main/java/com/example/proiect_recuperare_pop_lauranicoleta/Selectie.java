package com.example.proiect_recuperare_pop_lauranicoleta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_recuperare_pop_lauranicoleta.data.Departament;
import com.example.proiect_recuperare_pop_lauranicoleta.data.Facultate;

public class Selectie extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afisare_detalii_activity);

        Button btInchide = findViewById(R.id.btInchide);
        TextView tvIdAfisare = findViewById(R.id.tvIdAfisare);
        TextView tvDenumireAfisare = findViewById(R.id.tvDenumireAfisare);
        TextView tvNotiteAfisare = findViewById(R.id.tvNotiteAfisare);

        Intent intent = this.getIntent();
        if(intent.hasExtra("facultate")){
            Facultate facultate = (Facultate) intent.getSerializableExtra("facultate");
            tvIdAfisare.setText(String.valueOf(facultate.getId()));
            tvDenumireAfisare.setText(facultate.getDenumire());
            tvNotiteAfisare.setText(facultate.getNotite());
        }
        else{
            Departament departament = (Departament) intent.getSerializableExtra("departament");
            tvIdAfisare.setText(String.valueOf(departament.getId()));
            tvDenumireAfisare.setText(departament.getDenumire());
            tvNotiteAfisare.setText(departament.getNotite());
        }

        btInchide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
