package com.example.proiect_recuperare_pop_lauranicoleta;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_recuperare_pop_lauranicoleta.data.Departament;
import com.example.proiect_recuperare_pop_lauranicoleta.data.DepartamentDao;
import com.example.proiect_recuperare_pop_lauranicoleta.data.Facultate;
import com.example.proiect_recuperare_pop_lauranicoleta.data.FacultateDao;
import com.example.proiect_recuperare_pop_lauranicoleta.data.UserDatabase;

import java.util.ArrayList;

public class EditeazaNotite extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editeaza_notite_activity);

        //folosesc asta pentru a putea actualiza continutul obiectului - mai exact notitele.
        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());
        FacultateDao facultateDao = userDatabase.getFacultateDao();
        DepartamentDao departamentDao = userDatabase.getDepartamentDao();

        EditText etNotite = findViewById(R.id.etEditeazaNotite);
        Spinner spinner = findViewById(R.id.spinnerFacSiDepartamente);
        Button btSalveazaNotite = findViewById(R.id.btSalveazaNotitele);
        ArrayList<Object> listaFacultatiSiDepartamente = (ArrayList<Object>) getIntent().getSerializableExtra("listaFacultatiSiDepartamente");
        ArrayList<String> listaDenumiri = new ArrayList<>();
        if(listaFacultatiSiDepartamente != null){
            for (Object o : listaFacultatiSiDepartamente){
                if(o instanceof Facultate){
                    Facultate f = (Facultate) o;
                    listaDenumiri.add(f.getDenumire());
                }else{
                    Departament d = (Departament) o;
                    listaDenumiri.add(d.getDenumire());
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaDenumiri);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(listaFacultatiSiDepartamente.get(position) instanceof Facultate){
                    Facultate facultate = (Facultate) listaFacultatiSiDepartamente.get(position);
                    Facultate facultateDinBazaDeDate = facultateDao.getFacultateById(facultate.getId());
                    etNotite.setText(facultateDinBazaDeDate.getNotite());
                }else {
                    Departament dep = (Departament) listaFacultatiSiDepartamente.get(position);
                    Departament departamentDinBazaDeDate = departamentDao.getDepartament(dep.getId());
                    etNotite.setText(departamentDinBazaDeDate.getNotite());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btSalveazaNotite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = spinner.getSelectedItemPosition();
                try {
                    userDatabase.beginTransaction();
                    if (listaFacultatiSiDepartamente.get(selectedPosition) instanceof Facultate) {
                        Facultate facultate = (Facultate) listaFacultatiSiDepartamente.get(selectedPosition);
                        facultate.setNotite(etNotite.getText().toString());
                        facultateDao.updateFacultate(facultate);
                        userDatabase.setTransactionSuccessful();
                    } else {
                        Departament dep = (Departament) listaFacultatiSiDepartamente.get(selectedPosition);
                        dep.setNotite(etNotite.getText().toString());
                        departamentDao.updateDepartament(dep);
                        userDatabase.setTransactionSuccessful();
                    }
                } catch (Exception ex) {
                    throw new IllegalArgumentException();
                } finally
                 {
                    userDatabase.endTransaction();
                 }
                finish();
            }
        });
    }
}
