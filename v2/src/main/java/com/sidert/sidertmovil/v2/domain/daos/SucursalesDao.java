package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Sucursales;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SucursalesDao {

    @Query("SELECT * FROM tbl_sucursales")
    List<Sucursales> getAll();

    @Query("SELECT * FROM tbl_sucursales t0 WHERE t0._id = :id")
    Optional<Sucursales> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sucursales> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Sucursales entity);

    @Update()
    void update(Sucursales entity);

    @Delete()
    void delete(Sucursales entity);


}

