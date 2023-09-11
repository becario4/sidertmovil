package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.VerificacionesDomiciliarias;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface VerificacionesDomiciliariasDao {

    @Query("SELECT * FROM tbl_verificaciones_domiciliarias")
    List<VerificacionesDomiciliarias> getAll();

    @Query("SELECT * FROM tbl_verificaciones_domiciliarias t0 WHERE t0._id = :id")
    Optional<VerificacionesDomiciliarias> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VerificacionesDomiciliarias> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(VerificacionesDomiciliarias entity);

    @Update()
    void update(VerificacionesDomiciliarias entity);

    @Delete()
    void delete(VerificacionesDomiciliarias entity);


}

