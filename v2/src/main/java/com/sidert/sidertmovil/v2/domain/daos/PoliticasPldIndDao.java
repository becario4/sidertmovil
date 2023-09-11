package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIndDao {

    @Query("SELECT * FROM politicas_pld_ind")
    List<PoliticasPldInd> getAll();

    @Query("SELECT * FROM politicas_pld_ind t0 WHERE t0.id = :id")
    Optional<PoliticasPldInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldInd entity);

    @Update()
    void update(PoliticasPldInd entity);

    @Delete()
    void delete(PoliticasPldInd entity);


}

