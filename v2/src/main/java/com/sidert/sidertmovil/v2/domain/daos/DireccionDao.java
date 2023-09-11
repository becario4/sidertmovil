package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Direccion;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DireccionDao {

    @Query("SELECT * FROM tbl_direccion")
    List<Direccion> getAll();

    @Query("SELECT * FROM tbl_direccion t0 WHERE t0.id_direccion = :id")
    Optional<Direccion> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Direccion> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Direccion entity);

    @Update()
    void update(Direccion entity);

    @Delete()
    void delete(Direccion entity);


}

