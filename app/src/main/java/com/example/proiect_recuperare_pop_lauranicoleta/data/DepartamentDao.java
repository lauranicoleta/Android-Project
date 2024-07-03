package com.example.proiect_recuperare_pop_lauranicoleta.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DepartamentDao {

    @Insert
    void insertAll(List<Departament> departamente);
    @Query("SELECT * FROM departamente")
    List<Departament> getDepartamente();

    @Query("SELECT * FROM departamente WHERE id = :idDep")
    Departament getDepartament(int idDep);

    @Update
    void updateDepartament(Departament departament);


}
