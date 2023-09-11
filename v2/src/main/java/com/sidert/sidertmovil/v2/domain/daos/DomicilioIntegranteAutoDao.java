package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DomicilioIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DomicilioIntegranteAutoDao {

    @Query("SELECT * FROM tbl_domicilio_integrante_auto")
    List<DomicilioIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_domicilio_integrante_auto t0 WHERE t0.id_domicilio = :id")
    Optional<DomicilioIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DomicilioIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DomicilioIntegranteAuto entity);

    @Update()
    void update(DomicilioIntegranteAuto entity);

    @Delete()
    void delete(DomicilioIntegranteAuto entity);


}

