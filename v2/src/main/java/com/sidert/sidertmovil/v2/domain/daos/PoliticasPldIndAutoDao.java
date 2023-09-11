package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIndAutoDao {

    @Query("SELECT * FROM tbl_politicas_pld_ind_auto")
    List<PoliticasPldIndAuto> getAll();

    @Query("SELECT * FROM tbl_politicas_pld_ind_auto t0 WHERE t0.id = :id")
    Optional<PoliticasPldIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldIndAuto entity);

    @Update()
    void update(PoliticasPldIndAuto entity);

    @Delete()
    void delete(PoliticasPldIndAuto entity);


}

