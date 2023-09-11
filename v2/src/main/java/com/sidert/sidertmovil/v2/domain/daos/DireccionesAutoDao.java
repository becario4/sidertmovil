package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DireccionesAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DireccionesAutoDao {

    @Query("SELECT * FROM tbl_direcciones_auto")
    List<DireccionesAuto> getAll();

    @Query("SELECT * FROM tbl_direcciones_auto t0 WHERE t0.id_direccion = :id")
    Optional<DireccionesAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DireccionesAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DireccionesAuto entity);

    @Update()
    void update(DireccionesAuto entity);

    @Delete()
    void delete(DireccionesAuto entity);


}

