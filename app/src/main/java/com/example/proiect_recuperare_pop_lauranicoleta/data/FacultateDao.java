package com.example.proiect_recuperare_pop_lauranicoleta.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FacultateDao {
    @Insert
    void insertAll(List<Facultate> facultati);

    @Query("SELECT * FROM facultati")
    List<Facultate> getFacultati();

    @Query("SELECT * FROM facultati WHERE id = :idFac")
    Facultate getFacultateById(int idFac);

    @Update
    void updateFacultate(Facultate facultate);

}
