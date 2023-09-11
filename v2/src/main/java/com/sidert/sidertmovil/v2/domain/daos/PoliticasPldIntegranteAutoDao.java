package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIntegranteAutoDao {

    @Query("SELECT * FROM tbl_politicas_pld_integrante_auto")
    List<PoliticasPldIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_politicas_pld_integrante_auto t0 WHERE t0.id_politica = :id")
    Optional<PoliticasPldIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldIntegranteAuto entity);

    @Update()
    void update(PoliticasPldIntegranteAuto entity);

    @Delete()
    void delete(PoliticasPldIntegranteAuto entity);


}

