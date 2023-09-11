package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NegocioIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NegocioIntegranteDao {

    @Query("SELECT * FROM tbl_negocio_integrante")
    List<NegocioIntegrante> getAll();

    @Query("SELECT * FROM tbl_negocio_integrante t0 WHERE t0.id_negocio = :id")
    Optional<NegocioIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NegocioIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NegocioIntegrante entity);

    @Update()
    void update(NegocioIntegrante entity);

    @Delete()
    void delete(NegocioIntegrante entity);


}

