package com.example.proiect_recuperare_pop_lauranicoleta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_recuperare_pop_lauranicoleta.data.Departament;
import com.example.proiect_recuperare_pop_lauranicoleta.data.Facultate;

import java.util.ArrayList;
import java.util.List;

public class VizualizeazaNotitele extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vizualizeaza_notitele_activity);

        Button btInchide = findViewById(R.id.btInchideViz);
        TextView tvNotite = findViewById(R.id.etNotite);
        List<Object> listaFacultatiSiDepartamente = (List<Object>) getIntent().getSerializableExtra("listaFacultatiSiDepartamente");
        String textCuToateNotitele = "";
        if(listaFacultatiSiDepartamente != null){
            int i = 0;
            for (Object o : listaFacultatiSiDepartamente){
                i++;
                if(o instanceof Facultate){
                    Facultate f = (Facultate) o;
                    textCuToateNotitele += String.valueOf(i) + ". " + f.getNotite() + "\n";
                }else{
                    Departament d = (Departament) o;
                    textCuToateNotitele +=  String.valueOf(i) + ". " + d.getNotite() + "\n";
                }
            }
        }
        tvNotite.setText(textCuToateNotitele);

        btInchide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
