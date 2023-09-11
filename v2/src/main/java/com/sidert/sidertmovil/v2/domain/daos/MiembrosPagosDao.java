package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.MiembrosPagos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface MiembrosPagosDao {

    @Query("SELECT * FROM tbl_miembros_pagos_t")
    List<MiembrosPagos> getAll();

    @Query("SELECT * FROM tbl_miembros_pagos_t t0 WHERE t0._id = :id")
    Optional<MiembrosPagos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MiembrosPagos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MiembrosPagos entity);

    @Update()
    void update(MiembrosPagos entity);

    @Delete()
    void delete(MiembrosPagos entity);


}

