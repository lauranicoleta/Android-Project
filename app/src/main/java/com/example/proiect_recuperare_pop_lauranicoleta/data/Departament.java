package com.example.proiect_recuperare_pop_lauranicoleta.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "departamente")
public class Departament implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String denumire;
    private String notite;

    public Departament(int id, String denumire, String notite) {
        this.id = id;
        this.denumire = denumire;
        this.notite = notite;
    }

    @Override
    public String toString() {
        return "Departament{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", notite='" + notite + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getNotite() {
        return notite;
    }

    public void setNotite(String notite) {
        this.notite = notite;
    }
}
