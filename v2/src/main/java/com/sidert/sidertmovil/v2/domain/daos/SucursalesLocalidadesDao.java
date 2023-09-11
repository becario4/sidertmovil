package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.SucursalesLocalidades;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SucursalesLocalidadesDao {

    @Query("SELECT * FROM tbl_sucursales_localidades")
    List<SucursalesLocalidades> getAll();

    @Query("SELECT * FROM tbl_sucursales_localidades t0 WHERE t0.id_sucursales_localidades = :id")
    Optional<SucursalesLocalidades> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SucursalesLocalidades> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SucursalesLocalidades entity);

    @Update()
    void update(SucursalesLocalidades entity);

    @Delete()
    void delete(SucursalesLocalidades entity);


}

