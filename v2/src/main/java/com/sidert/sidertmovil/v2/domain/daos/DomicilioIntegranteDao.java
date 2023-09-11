package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DomicilioIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DomicilioIntegranteDao {

    @Query("SELECT * FROM tbl_domicilio_integrante")
    List<DomicilioIntegrante> getAll();

    @Query("SELECT * FROM tbl_domicilio_integrante t0 WHERE t0.id_domicilio = :id")
    Optional<DomicilioIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DomicilioIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DomicilioIntegrante entity);

    @Update()
    void update(DomicilioIntegrante entity);

    @Delete()
    void delete(DomicilioIntegrante entity);


}

