package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIntegranteDao {

    @Query("SELECT * FROM tbl_politicas_pld_integrante")
    List<PoliticasPldIntegrante> getAll();

    @Query("SELECT * FROM tbl_politicas_pld_integrante t0 WHERE t0.id_politica = :id")
    Optional<PoliticasPldIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldIntegrante entity);

    @Update()
    void update(PoliticasPldIntegrante entity);

    @Delete()
    void delete(PoliticasPldIntegrante entity);


}

