package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.SolicitudesAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SolicitudesAutoDao {

    @Query("SELECT * FROM tbl_solicitudes_auto")
    List<SolicitudesAuto> getAll();

    @Query("SELECT * FROM tbl_solicitudes_auto t0 WHERE t0.id_solicitud = :id")
    Optional<SolicitudesAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SolicitudesAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SolicitudesAuto entity);

    @Update()
    void update(SolicitudesAuto entity);

    @Delete()
    void delete(SolicitudesAuto entity);


}

