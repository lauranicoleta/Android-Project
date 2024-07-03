package com.example.proiect_recuperare_pop_lauranicoleta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.proiect_recuperare_pop_lauranicoleta.data.Departament;
import com.example.proiect_recuperare_pop_lauranicoleta.data.DepartamentDao;
import com.example.proiect_recuperare_pop_lauranicoleta.data.Facultate;
import com.example.proiect_recuperare_pop_lauranicoleta.data.FacultateDao;
import com.example.proiect_recuperare_pop_lauranicoleta.data.UserDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Facultate> facultati = new ArrayList<>();
    List<Departament> departamente = new ArrayList<>();
    public List<Object> facultatiSiDepartamente = new ArrayList<>();
    ListView lvFacultatiSiDepartamente;
    Adapter adapter;
    int REQUEST_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbDenumireUniversitate);
        setSupportActionBar(toolbar);

        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());
        FacultateDao facultateDao = userDatabase.getFacultateDao();
        DepartamentDao departamentDao = userDatabase.getDepartamentDao();

        String jsonString = incarcaDateJsonDinFisier(getApplicationContext(), "json_proiect_dam");
        facultati = getFacultatiDinJson(jsonString);
        departamente = getDepartamentDinJson(jsonString);
        facultateDao.insertAll(facultati);
        departamentDao.insertAll(departamente);

        facultati = facultateDao.getFacultati();
        departamente = departamentDao.getDepartamente();

        for (Facultate f : facultati) {
            facultatiSiDepartamente.add(f);
        }
        for (Departament d : departamente){
            facultatiSiDepartamente.add(d);
        }

        lvFacultatiSiDepartamente = findViewById(R.id.lvFacultatiSiDepartamente);
        adapter = new Adapter(MainActivity.this, facultatiSiDepartamente);
        lvFacultatiSiDepartamente.setAdapter(adapter);

        lvFacultatiSiDepartamente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(facultatiSiDepartamente.get(position) instanceof Facultate){
                    Facultate facultate = (Facultate) facultatiSiDepartamente.get(position);
                    Facultate facultateDinBazaDeDate = facultateDao.getFacultateById(facultate.getId());
                    Intent intent = new Intent(MainActivity.this, Selectie.class);
                    intent.putExtra("facultate", facultateDinBazaDeDate);
                    startActivityForResult(intent, REQUEST_CODE);
                }else {
                    Departament departament = (Departament) facultatiSiDepartamente.get(position);
                    Departament departamentDinBazaDeDate = departamentDao.getDepartament(departament.getId());
                    Intent intent = new Intent(MainActivity.this, Selectie.class);
                    intent.putExtra("departament", departamentDinBazaDeDate);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.editeaza_notite){
            Intent intent = new Intent(MainActivity.this, EditeazaNotite.class);
            intent.putExtra("listaFacultatiSiDepartamente", (Serializable) facultatiSiDepartamente);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        } else if (item.getItemId() == R.id.vizualizeaza_notite) {
            Intent intent = new Intent(MainActivity.this, VizualizeazaNotitele.class);
            intent.putExtra("listaFacultatiSiDepartamente", (Serializable) facultatiSiDepartamente);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        else if(item.getItemId() == R.id.despre_aplicatie){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.dialog_titlu).setMessage(R.string.dialog_mesaj);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public static String incarcaDateJsonDinFisier(Context context, String denumireFisier){
        String json = null;
        try{
            InputStream is = context.getResources().openRawResource(context
                    .getResources().getIdentifier(denumireFisier, "raw", context.getPackageName()));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static List<Facultate> getFacultatiDinJson(String jsonString){
        List<Facultate> facultati = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray facultatiArray = jsonObject.getJSONArray("facultati");
            for (int i = 0; i<facultatiArray.length(); i++){
                JSONObject facultateObj = facultatiArray.getJSONObject(i);
                int id = facultateObj.getInt("id");
                String denumire = facultateObj.getString("nume");
                String notite = facultateObj.getString("notite");

                Facultate f = new Facultate(id, denumire, notite);
                facultati.add(f);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return facultati;
    }

    public static List<Departament> getDepartamentDinJson(String jsonString){
        List<Departament> departamente = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray departamenteArray = jsonObject.getJSONArray("departamente");
            for (int i = 0; i<departamenteArray.length(); i++){
                JSONObject departamentObj = departamenteArray.getJSONObject(i);
                int id = departamentObj.getInt("id");
                String denumire = departamentObj.getString("denumire");
                String notite = departamentObj.getString("notite");

                Departament d = new Departament(id, denumire, notite);
                departamente.add(d);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return departamente;
    }
}