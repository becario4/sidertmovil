package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Solicitudes;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SolicitudesDao {

    @Query("SELECT * FROM tbl_solicitudes")
    List<Solicitudes> getAll();

    @Query("SELECT * FROM tbl_solicitudes t0 WHERE t0.id_solicitud = :id")
    Optional<Solicitudes> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Solicitudes> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Solicitudes entity);

    @Update()
    void update(Solicitudes entity);

    @Delete()
    void delete(Solicitudes entity);


}
