package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NegocioIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NegocioIntegranteAutoDao {

    @Query("SELECT * FROM tbl_negocio_integrante_auto")
    List<NegocioIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_negocio_integrante_auto t0 WHERE t0.id_negocio = :id")
    Optional<NegocioIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NegocioIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NegocioIntegranteAuto entity);

    @Update()
    void update(NegocioIntegranteAuto entity);

    @Delete()
    void delete(NegocioIntegranteAuto entity);


}

